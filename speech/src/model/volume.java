package model;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class volume {
public static void up()
{
	try {
	Robot rm= new Robot();
	
	/*rm.mouseMove(1234, 742);
		Thread.sleep(1000);
		rm.keyPress(KeyEvent.VK_RIGHT);
		rm.keyRelease(KeyEvent.VK_RIGHT);*/
        
		
		
		rm.keyPress(KeyEvent.VK_WINDOWS);
		rm.keyPress(KeyEvent.VK_T);
		rm.keyRelease(KeyEvent.VK_T);
		rm.keyRelease(KeyEvent.VK_WINDOWS);
		Thread.sleep(100);
	
     
    	rm.keyPress(KeyEvent.VK_TAB);
		rm.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(100);
		rm.keyPress(KeyEvent.VK_TAB);
		rm.keyRelease(KeyEvent.VK_TAB);
		Thread.sleep(100);
		rm.keyPress(KeyEvent.VK_RIGHT);
		rm.keyRelease(KeyEvent.VK_RIGHT);
		Thread.sleep(100);
		rm.keyPress(KeyEvent.VK_RIGHT);
		rm.keyRelease(KeyEvent.VK_RIGHT);
		Thread.sleep(100);
		rm.keyPress(KeyEvent.VK_RIGHT);
		rm.keyRelease(KeyEvent.VK_RIGHT);
		Thread.sleep(100);
		rm.keyPress(KeyEvent.VK_ENTER);
		rm.keyRelease(KeyEvent.VK_ENTER);
		for(int ij=0;ij<10;ij++)
		{
		Thread.sleep(100);
		rm.keyPress(KeyEvent.VK_RIGHT);
		rm.keyRelease(KeyEvent.VK_RIGHT);
		}
		
		
	}
catch (AWTException | InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
}

public static void down()
{
	try {
		Robot rm= new Robot();
		
		/*rm.mouseMove(1234, 742);
			Thread.sleep(1000);
			rm.keyPress(KeyEvent.VK_RIGHT);
			rm.keyRelease(KeyEvent.VK_RIGHT);*/
	        
			
			
			rm.keyPress(KeyEvent.VK_WINDOWS);
			rm.keyPress(KeyEvent.VK_T);
			rm.keyRelease(KeyEvent.VK_T);
			rm.keyRelease(KeyEvent.VK_WINDOWS);
			Thread.sleep(100);
		
	     
	    	rm.keyPress(KeyEvent.VK_TAB);
			rm.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_TAB);
			rm.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_RIGHT);
			rm.keyRelease(KeyEvent.VK_RIGHT);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_RIGHT);
			rm.keyRelease(KeyEvent.VK_RIGHT);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_RIGHT);
			rm.keyRelease(KeyEvent.VK_RIGHT);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_ENTER);
			rm.keyRelease(KeyEvent.VK_ENTER);
			for(int ij=0;ij<10;ij++)
			{
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_LEFT);
			rm.keyRelease(KeyEvent.VK_LEFT);
			}
			
			
			
		}
	catch (AWTException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
public static void wifi()
{
	try {
		Robot rm= new Robot();
		
		/*rm.mouseMove(1234, 742);
			Thread.sleep(1000);
			rm.keyPress(KeyEvent.VK_RIGHT);
			rm.keyRelease(KeyEvent.VK_RIGHT);*/
	        
			
			
			rm.keyPress(KeyEvent.VK_WINDOWS);
			rm.keyPress(KeyEvent.VK_T);
			rm.keyRelease(KeyEvent.VK_T);
			rm.keyRelease(KeyEvent.VK_WINDOWS);
			Thread.sleep(100);
		
	     
	    	rm.keyPress(KeyEvent.VK_TAB);
			rm.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_TAB);
			rm.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_RIGHT);
			rm.keyRelease(KeyEvent.VK_RIGHT);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_RIGHT);
			rm.keyRelease(KeyEvent.VK_RIGHT);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_ENTER);
			rm.keyRelease(KeyEvent.VK_ENTER);
			for(int ij=0;ij<10;ij++)
			{
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_LEFT);
			rm.keyRelease(KeyEvent.VK_LEFT);
			}
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_TAB);
			rm.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_TAB);
			rm.keyRelease(KeyEvent.VK_TAB);
			Thread.sleep(100);
			rm.keyPress(KeyEvent.VK_ENTER);
			rm.keyRelease(KeyEvent.VK_ENTER);
			
			
		}
	catch (AWTException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
}
}
