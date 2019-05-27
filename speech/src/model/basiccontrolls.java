package model;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class basiccontrolls {
	
	public static void copy()
	{
		try
		{
			Robot tt=new Robot();
			tt.keyPress(KeyEvent.VK_CONTROL);
			tt.keyPress(KeyEvent.VK_C);
			tt.keyRelease(KeyEvent.VK_C);
			tt.keyRelease(KeyEvent.VK_CONTROL);
		}
		catch(Exception e)
		{
			
		}
	}
    public static void paste()
    {
    	try
		{
			Robot tt=new Robot();
			tt.keyPress(KeyEvent.VK_CONTROL);
			tt.keyPress(KeyEvent.VK_V);
			tt.keyRelease(KeyEvent.VK_V);
			tt.keyRelease(KeyEvent.VK_CONTROL);
		}
		catch(Exception e)
		{
			
		}
    }
    public static void cut()
    {
    	try
		{
			Robot tt=new Robot();
			tt.keyPress(KeyEvent.VK_CONTROL);
			tt.keyPress(KeyEvent.VK_X);
			tt.keyRelease(KeyEvent.VK_X);
			tt.keyRelease(KeyEvent.VK_CONTROL);
		}
		catch(Exception e)
		{
			
		}
    	
    }
    public static void back()
    {
    	try
		{
			Robot tt=new Robot();
			tt.keyPress(KeyEvent.VK_BACK_SPACE);
			tt.keyRelease(KeyEvent.VK_BACK_SPACE);
		}
		catch(Exception e)
		{
			
		}
    }
    public static void camera()
    {
    	try
		{
			Robot tt=new Robot();
			tt.keyPress(KeyEvent.VK_WINDOWS);
			tt.keyRelease(KeyEvent.VK_WINDOWS);
			Thread.sleep(100);
			tt.mouseMove(685, 500);
			Thread.sleep(100);
			tt.mousePress(InputEvent.BUTTON1_DOWN_MASK);
			tt.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
		}
		catch(Exception e)
		{
			
		}
    }
}
