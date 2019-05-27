package model;

import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class FUNC {
   // driver=new ChromeDriver();
	
	
	public static void  facebook()
	{
		 String ff="https://www.fb.com";
 	    try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(ff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void  youtube()
	{
		 String ff="https://www.youtube.com";
 	    try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(ff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void  gmail()
	{
		 String ff="https://www.gmail.com";
 	    try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(ff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void  google()
	{
		 String ff="https://www.google.com";
 	    try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(ff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void  pmusic()
	{
		 String ff="https://www.youtube.com/watch?v=byitAI7kkOM&list=RDbyitAI7kkOM&start_radio=1";
 	    try {
				java.awt.Desktop.getDesktop().browse(java.net.URI.create(ff));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public static void desktop()
	{
		try
		{
			Robot tt=new Robot();
			tt.keyPress(KeyEvent.VK_WINDOWS);
			tt.keyPress(KeyEvent.VK_D);
			tt.keyRelease(KeyEvent.VK_D);
			tt.keyRelease(KeyEvent.VK_WINDOWS);
		}
		catch(Exception e)
		{
			
		}
	}
}
