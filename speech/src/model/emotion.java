package model;

import java.awt.Robot;
import java.awt.event.KeyEvent;

public class emotion {
public static void up()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_UP);
    	tt.keyRelease(KeyEvent.VK_UP);
	}
	catch(Exception e)
	{
		
	}
	
}
public static void down()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_DOWN);
    	tt.keyRelease(KeyEvent.VK_DOWN);
	}
	catch(Exception e)
	{
		
	}
}
public static void right()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_RIGHT);
    	tt.keyRelease(KeyEvent.VK_RIGHT);
	}
	catch(Exception e)
	{
		
	}
}
public static void left()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_LEFT);
    	tt.keyRelease(KeyEvent.VK_LEFT);
	}
	catch(Exception e)
	{
		
	}
}
public static void select()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_ENTER);
    	tt.keyRelease(KeyEvent.VK_ENTER);
	}
	catch(Exception e)
	{
		
	}
}
public static void viewmenu()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_MINUS);
		tt.keyPress(KeyEvent.VK_NUMPAD5);
		tt.keyRelease(KeyEvent.VK_NUMPAD5);
		tt.keyRelease(KeyEvent.VK_MINUS);
	}
	catch(Exception e)
	{
		
	}
}
public static void property()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_ALT);
		tt.keyPress(KeyEvent.VK_ENTER);
		tt.keyRelease(KeyEvent.VK_ENTER);
		tt.keyRelease(KeyEvent.VK_ALT);
	}
	catch(Exception e)
	{
		
	}
}
public static void turnon()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_ALT);
		tt.keyPress(KeyEvent.VK_SHIFT);
		tt.keyPress(KeyEvent.VK_NUM_LOCK);
		tt.keyRelease(KeyEvent.VK_NUM_LOCK);
		tt.keyRelease(KeyEvent.VK_SHIFT);
		tt.keyRelease(KeyEvent.VK_ALT);
	}
	catch(Exception e)
	{
		
	}
}
public static void close()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_ALT);
		tt.keyPress(KeyEvent.VK_F4);
		tt.keyRelease(KeyEvent.VK_F4);
		tt.keyRelease(KeyEvent.VK_ALT);
	}
	catch(Exception e)
	{
		
	}
}
public static void switcht()
{
	try
	{
		Robot tt=new Robot();
		tt.keyPress(KeyEvent.VK_WINDOWS);
		tt.keyPress(KeyEvent.VK_TAB);
		tt.keyRelease(KeyEvent.VK_TAB);
		tt.keyRelease(KeyEvent.VK_WINDOWS);
	}
	catch(Exception e)
	{
		
	}
}

}
