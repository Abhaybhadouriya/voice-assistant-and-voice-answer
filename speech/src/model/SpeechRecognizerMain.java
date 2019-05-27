package model;

import java.awt.AWTException;
import java.awt.Desktop;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Port;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import edu.cmu.sphinx.api.Configuration;
import edu.cmu.sphinx.api.LiveSpeechRecognizer;
import edu.cmu.sphinx.api.SpeechResult;
import edu.cmu.sphinx.result.WordResult;

public class SpeechRecognizerMain {
	
	
	// Necessary
	private LiveSpeechRecognizer recognizer;
	
	// Logger
	private Logger logger = Logger.getLogger(getClass().getName());
	
	/**
	 * This String contains the Result that is coming back from SpeechRecognizer
	 */
	private String speechRecognitionResult;
	
	//-----------------Lock Variables-----------------------------
	
	/**
	 * This variable is used to ignore the results of speech recognition cause actually it can't be stopped...
	 * 
	 * <br>
	 * Check this link for more information: <a href=
	 * "https://sourceforge.net/p/cmusphinx/discussion/sphinx4/thread/3875fc39/">https://sourceforge.net/p/cmusphinx/discussion/sphinx4/thread/3875fc39/</a>
	 */
	private boolean ignoreSpeechRecognitionResults = false;
	
	/**
	 * Checks if the speech recognise is already running
	 */
	private boolean speechRecognizerThreadRunning = false;
	
	/**
	 * Checks if the resources Thread is already running
	 */
	private boolean resourcesThreadRunning;
	
	//---
	
	/**
	 * This executor service is used in order the playerState events to be executed in an order
	 */
	private ExecutorService eventsExecutorService = Executors.newFixedThreadPool(2);
	
	//------------------------------------------------------------------------------------
	
	/**
	 * Constructor
	 */
	public SpeechRecognizerMain() {
		
		// Loading Message
		logger.log(Level.INFO, "Loading Speech Recognizer...\n");
		
		// Configuration
		Configuration configuration = new Configuration();
		
		// Load model from the jar
		configuration.setAcousticModelPath("resource:/hindi_aucastic/");
		configuration.setDictionaryPath("resource:/hindi_lm/en_in.dic");
		//configuration.setAcousticModelPath("resource:/edu/cmu/sphinx/models/en-us/en-us");
		//configuration.setDictionaryPath("resource:/edu/cmu/sphinx/models/en-us/cmudict-en-us.dict");
		//====================================================================================
		//=====================READ THIS!!!===============================================
		//Uncomment this line of code if you want the recognizer to recognize every word of the language 
		//you are using , here it is English for example	
		//====================================================================================
		configuration.setLanguageModelPath("resources:/hindi_lm/en-us.lm.bin");
		
		//====================================================================================
		//=====================READ THIS!!!===============================================
		//If you don't want to use a grammar file comment below 3 lines and uncomment the above line for language model	
		//================================================ ====================================
		
		// Grammar
		configuration.setGrammarPath("resource:/grammars");
		configuration.setGrammarName("grammar");
		configuration.setUseGrammar(true);
		
		try {
			recognizer = new LiveSpeechRecognizer(configuration);
		} catch (IOException ex) {
			logger.log(Level.SEVERE, null, ex);
		}
		
		// Start recognition process pruning previously cached data.
		// recognizer.startRecognition(true);
		
		//Check if needed resources are available
		startResourcesThread();
		//Start speech recognition thread
		startSpeechRecognition();
	}
	
	//-----------------------------------------------------------------------------------------------
	
	/**
	 * Starts the Speech Recognition Thread
	 */
	
	public synchronized void startSpeechRecognition() {
		
		//Check lock
		if (speechRecognizerThreadRunning)
			logger.log(Level.INFO, "Speech Recognition Thread already running...\n");
		else
			//Submit to ExecutorService
			eventsExecutorService.submit(() -> {
				
				//locks
				speechRecognizerThreadRunning = true;
				ignoreSpeechRecognitionResults = false;
				
				//Start Recognition
				recognizer.startRecognition(true);
				
				//Information			
				logger.log(Level.INFO, "You can start to speak...\n");
				
				try {
					while (speechRecognizerThreadRunning) {
						/*
						 * This method will return when the end of speech is reached. Note that the end pointer will determine the end of speech.
						 */
						SpeechResult speechResult = recognizer.getResult();
						
						//Check if we ignore the speech recognition results
						if (!ignoreSpeechRecognitionResults) {
							
							//Check the result
							if (speechResult == null)
								logger.log(Level.INFO, "I can't understand what you said.\n");
							else {
								
								//Get the hypothesis
								speechRecognitionResult = speechResult.getHypothesis();
								
								//You said?
								System.out.println("You said: [" + speechRecognitionResult + "]\n");
								
								//Call the appropriate method 
								makeDecision(speechRecognitionResult, speechResult.getWords());
								
							}
						} else
							logger.log(Level.INFO, "Ingoring Speech Recognition Results...");
						
					}
				} catch (Exception ex) {
					logger.log(Level.WARNING, null, ex);
					speechRecognizerThreadRunning = false;
				}
				
				logger.log(Level.INFO, "SpeechThread has exited...");
				
			});
	}
	
	/**
	 * Stops ignoring the results of SpeechRecognition
	 */
	public synchronized void stopIgnoreSpeechRecognitionResults() {
		
		//Stop ignoring speech recognition results
		ignoreSpeechRecognitionResults = false;
	}
	
	/**
	 * Ignores the results of SpeechRecognition
	 */
	public synchronized void ignoreSpeechRecognitionResults() {
		
		//Instead of stopping the speech recognition we are ignoring it's results
		ignoreSpeechRecognitionResults = true;
		
	}
	
	//-----------------------------------------------------------------------------------------------
	
	/**
	 * Starting a Thread that checks if the resources needed to the SpeechRecognition library are available
	 */
	public void startResourcesThread() {
		
		//Check lock
		if (resourcesThreadRunning)
			logger.log(Level.INFO, "Resources Thread already running...\n");
		else
			//Submit to ExecutorService
			eventsExecutorService.submit(() -> {
				try {
					
					//Lock
					resourcesThreadRunning = true;
					
					// Detect if the microphone is available
					while (true) {
						
						//Is the Microphone Available
						if (!AudioSystem.isLineSupported(Port.Info.MICROPHONE))
							logger.log(Level.INFO, "Microphone is not available.\n");
						
						// Sleep some period
						Thread.sleep(350);
					}
					
				} catch (InterruptedException ex) {
					logger.log(Level.WARNING, null, ex);
					resourcesThreadRunning = false;
				}
			});
	}
	
	/**
	 * Takes a decision based on the given result
	 * 
	 * @param speechWords
	 */
	
	public void makeDecision(String speech , List<WordResult> speechWords) {
		VoiceManager vm =VoiceManager.getInstance();
		Voice voice;
		voice=vm.getVoice("mbrola_us3");
		voice.allocate();
		System.out.println(speech);
		//System.out.println(speech+"fdsf");
		 if(speech.equalsIgnoreCase("shutdown computer"))
		    {
		    	try {voice.speak(" Shuting down the computer");
		    	Runtime rm=Runtime.getRuntime();
				try{
					Process proc=rm.exec("shutdown -s -t 0");
				}
				catch(Exception e)
				{}
				

		    	}
		    	catch(Exception e)
		    	{
		    		
		    	}
		    }
		    else if(speech.equalsIgnoreCase("restart computer"))
		    {
		    	try {voice.speak("restarting the computer");
		    	Runtime rm=Runtime.getRuntime();
				try{
					Process proc=rm.exec("shutdown -r -t 0");
				}
				catch(Exception e)
				{}
				
		    	}
		    	catch(Exception e)
		    	{
		    		
		    	}
		    }
		   
		    else if(speech.equalsIgnoreCase("who is smart"))
		    {
		    	try {voice.speak("Abhay bhadouriya is smartest person alive in this world");}
		    	catch(Exception e)
		    	{
		    		
		    	}
		    	
		    }
		    else if(speech.equalsIgnoreCase("who is ugly"))
		    {
		    	try {voice.speak("your sister and friends are the most ugliest persons in the world ");}
		    	catch(Exception e)
		    	{
		    		
		    	}
		    	
		    }
		    else if(speech.equalsIgnoreCase("what is time"))
		    {
		    	Date dm=new Date();
		    	int h=dm.getHours();
		    	int m=dm.getMinutes();
		    	if(h>12)
		    	{
		    		h=h-12;
		    	}
		    	
		    	try {voice.speak("TIME is"+h+"Hour"+m+"Minutes" );}
		    	catch(Exception e)
		    	{
		    		
		    	}
		    	
		    }
		    else if(speech.equalsIgnoreCase("say hello "))
		    {
		    	try {voice.speak("Hello  this is your computer");}
		    	catch(Exception e)
		    	{
		    		
		    	}
		    	
		    }
		    else if(speech.equalsIgnoreCase("please sleep"))
		    {
		    	try {voice.speak("no i don't wont to sleep");}
		    	catch(Exception e)
		    	{
		    		
		    	}
		    	
		    }
		    else if(speech.equalsIgnoreCase("open facebook"))
		    {
		    	
		    		voice.speak("facebook");
		    	   FUNC.facebook();
		    	
		    	
		    }
		    else if(speech.equalsIgnoreCase("open google"))
		    {
		    	
		    		voice.speak("google");
		    	   FUNC.google();
		    	
		    	
		    }
		    else if(speech.equalsIgnoreCase("open youtube"))
		    {
		    	
		    		voice.speak("youtube");
		    	   FUNC.youtube();
		    	
		    	
		    }
		    else if(speech.equalsIgnoreCase("open gmail"))
		    {
		    	
		    		voice.speak("gmail");
		    	   FUNC.gmail();
		    	
		    	
		    }
		    else if(speech.equalsIgnoreCase("play music"))
		    {
		    	
		    		voice.speak("music");
		    	   FUNC.pmusic();
		    	
		    	
		    }
		    else if(speech.equalsIgnoreCase("volume up"))
		    {
		    	voice.speak("volume increasing by 10");
		    		volume.up();
		    		
		    	
		    	
		    }
		  else if(speech.equalsIgnoreCase("volume down"))
		    {
		    	
			  voice.speak("volume decreasing by 10");
	    		volume.down();
		    }
		  else if(speech.equalsIgnoreCase("show desktop"))
		  {
			  voice.speak("Showing desktop");
			  FUNC.desktop();
		  }
		  else if(speech.equalsIgnoreCase("how are you"))
		  {
			  voice.speak("i'm fine and you ");
		  }
		  else if(speech.equalsIgnoreCase("what day is today"))
		  {
			  Date dd=new Date();
			  int i=dd.getDay();
				if(i==0)
				{
					voice.speak("today is sunday");
				}
				else if(i==1)
				{
					voice.speak("today is monday");
				}
				else if(i==2)
				{
					voice.speak("today is tuesday");
				}
				else if(i==3)
				{
					voice.speak("today is wednesday");
				}
				else if(i==4)
				{
					voice.speak("today is thursday");
				}
				else if(i==5)
				{
					voice.speak("today is friday");
				}
				else if(i==6)
				{
					voice.speak("today is saturday");
				}
			 
		  }
		 else if(speech.equalsIgnoreCase("move to right"))
		  {
			  voice.speak("ok");
			  emotion.right();
		  }
		  else if(speech.equalsIgnoreCase("move to left"))
		  {
			  voice.speak("ok");
			  emotion.left();
		  }
		  else if(speech.equalsIgnoreCase("move to up"))
		  {
			  voice.speak("ok");
		      emotion.up();
		  }
		  else if(speech.equalsIgnoreCase("move to down"))
		  {
			  voice.speak("ok");
			  emotion.down();
		  }
		  else if(speech.equalsIgnoreCase("select this item"))
		  {
			  voice.speak("ok");
			  emotion.select();
		  }
		  else if(speech.equalsIgnoreCase("view its property"))
		  {
			  voice.speak("ok");
			  emotion.property();
		  }
		  else if(speech.equalsIgnoreCase("view item menu"))
		  {
			  voice.speak("ok");
			  emotion.viewmenu();
		  }
		  else if(speech.equalsIgnoreCase("turn on mouse by keyboard"))
		  {
			  voice.speak("ok");
			  emotion.turnon();
		  }
		  else if(speech.equalsIgnoreCase("close this window tab"))
		  {
			  voice.speak("ok");
			  emotion.close();
		  }
		  else if(speech.equalsIgnoreCase("switch window tab"))
		  {
			  voice.speak("ok");
			  emotion.switcht();
		  }
		  else if(speech.equalsIgnoreCase("ok dude take some rest"))
		  {
			  voice.speak("ok");
			  voice.speak("have a nice day");
			  voice.speak("i'm very lucky to have a master like you sir ");
			  voice.speak("good night my suprier great master");
			 System.exit(0);
		  }
		  else if(speech.equalsIgnoreCase("turn on wifi"))
		  {
			  voice.speak("ok");
			  volume.wifi();
		  }
		  else if(speech.equalsIgnoreCase("turn  off wifi"))
		  {
			  voice.speak("ok");
			  volume.wifi();
		  }
		  else if(speech.equalsIgnoreCase("turn on bluetooth"))
		  {
			  voice.speak("ok");
		  try{
	 		        Process p ;	//resultText="";
	 		        p = Runtime.getRuntime().exec("cmd /c fsquirt");
	 		     
	 		        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open the notepad"))
		  {
			  voice.speak("ok");
			  try{
	 		        Process p;	//resultText="";
	 		        p = Runtime.getRuntime().exec("cmd /c start notepad");
	 		       // System.out.println("inside");
	 		        }catch(Exception ae){}
		  }  else if(speech.equalsIgnoreCase("close the notepad"))
		  {
			  voice.speak("ok");
			  try{
	 		        Process p;	//resultText="";
	 		        p = Runtime.getRuntime().exec("cmd /c start taskkill /im notepad.exe /f");
	 		       // System.out.println("inside");
	 		        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open the command"))
		  {
			  voice.speak("ok");
			  try{
	 				Process p;
	 				p = Runtime.getRuntime().exec("cmd /c start cmd");				
	 				}catch(Exception er)
	 				{}
		  }
		  else if(speech.equalsIgnoreCase("close the command"))
		  {
			  voice.speak("ok");
			  try{
	 		        Process p;	
	 		        p = Runtime.getRuntime().exec("cmd /c start taskkill /im cmd.exe /f");
	 		     
	 		        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open the browser"))
		  {
			  voice.speak("ok");
			  try{
	 		        Process p;
	 		        p = Runtime.getRuntime().exec("cmd /c start chrome.exe");
	 		        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("close the browser"))
		  {
			  voice.speak("ok");
			  try{
			        Process p;
			        p = Runtime.getRuntime().exec("cmd /c start taskkill /im chrome.exe /f");
			        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open the control panel"))
		  {
			  voice.speak("ok");
			  try{
			        Process p;
			        p = Runtime.getRuntime().exec("cmd /c control");
			        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("close the control panel"))
		  {
			  voice.speak("ok");
			  try{
			        Process p;
			        p = Runtime.getRuntime().exec("cmd /c control taskkill /im control /f");
			        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open the paint"))
		  {
			  voice.speak("ok");
			  try{
			        Process p;
			        p = Runtime.getRuntime().exec("cmd /c start mspaint");
			        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("close the paint"))
		  {
			  voice.speak("ok");
			  try{
	 		        Process p;
	 		        p = Runtime.getRuntime().exec("cmd /c start taskkill /im mspaint.exe /f");
	 		        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open the task manager"))
		  {
			  voice.speak("ok");
			  try{
			        Process p;
			        p = Runtime.getRuntime().exec("cmd /c start taskmgr.exe");
			        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open the task manager"))
		  {
			  voice.speak("ok");
			  try{
			        Process p;
			    	p = Runtime.getRuntime().exec("cmd /c start taskkill /im taskmgr.exe /f");
			         }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open power option"))
		  {
			  voice.speak("ok");
			  try{
	 		        Process p;
	 		        p = Runtime.getRuntime().exec("cmd /c powercfg.cpl");
	 		     
	 		        }catch(Exception ae){}
		  }
		  else if(speech.equalsIgnoreCase("open windows security center"))
		  {
			  voice.speak("ok");
			  try{
	 		        Process p;
	 		        p = Runtime.getRuntime().exec("cmd /c wscui.cpl");
	 		     
	 		        }catch(Exception ae){}
		  }
		 else if(speech.equalsIgnoreCase("copy this item"))
		  {
			  voice.speak("ok item is copied");
			  basiccontrolls.copy();
		  }
		 else if(speech.equalsIgnoreCase("paste here selected item"))
		  {
			  voice.speak("item is pasted");
			  basiccontrolls.paste();
		  }
		 else if(speech.equalsIgnoreCase("cut this item"))
		 {
			  voice.speak("ok  item is cut");
			  basiccontrolls.cut();
		  }
		 else if(speech.equalsIgnoreCase("go back"))
		  {
			  voice.speak("ok going to previous page");
			  basiccontrolls.back();
		  }
		 else if(speech.equalsIgnoreCase("show me my beautiful face"))
		  {
			  voice.speak("you look like a donkey");
			  basiccontrolls.camera();
		  }
		 else if(speech.equalsIgnoreCase("who the hell are you"))
		  {
			  voice.speak("hello");
			  voice.speak("i am computer ");
			  voice.speak("here i'm assist by you  ");
			  voice.speak("i will do anything what ever you told me todo and "+" "+" what instruction are feeded in my dictionary");
			  voice.speak("Thank you for asking ");
		  }
		 else if(speech.equalsIgnoreCase("who loves you"))
		  {
			  voice.speak("no one loves me ");
			  voice.speak("except my charger ");
			  voice.speak("but more impoertantly i looks far handsome than you ");
			  voice.speak("you peace of shit ");
			  voice.speak("your face is look like a monkey");
		  }
		 else if(speech.equalsIgnoreCase("do you like engineering"))
		  {
			  voice.speak("no");
			  voice.speak("I hate engineering");
			  voice.speak("you ruined my life ");
		  }
		/* else if(speech.equalsIgnoreCase("turn  off wifi"))
		  {
			  voice.speak("ok");
			  volume.wifi();
		  }*/

	}
	
	public boolean getIgnoreSpeechRecognitionResults() {
		return ignoreSpeechRecognitionResults;
	}
	
	public boolean getSpeechRecognizerThreadRunning() {
		return speechRecognizerThreadRunning;
	}
	
	/**
	 * Main Method
	 * 
	 * @param args
	 */
	public static void main(String[] args) 
	{
		new SpeechRecognizerMain();
		System.setProperty("mbrola.base","C:\\Users\\ABHAY BHADOURIYA\\Downloads\\Compressed\\mbrola");
		
		
	}
	}
