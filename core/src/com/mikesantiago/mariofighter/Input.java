package com.mikesantiago.mariofighter;

public class Input  
{
	public static boolean[] keys; //current
	public static boolean[] pkeys; //previous
	
	public static final int NUM_KEYS = 8; //make sure this is in line with
	
	public static final int MOVE_UP = 0;  //these
	public static final int MOVE_LEFT = 1;
	public static final int MOVE_RIGHT = 2;
	public static final int MOVE_DOWN = 3;
	
	public static final int ZOOM_IN = 4;
	public static final int ZOOM_OUT = 5;
	
	public static final int RESET_POS = 6;
	public static final int JUMP = 7;
	
	static
	{
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update()
	{
		for(int i = 0; i < NUM_KEYS; i++)
		{
			pkeys[i] = keys[i];
		}
	}
	
	public static boolean isDown(int i)
	{
		return keys[i];
	}
	
	public static boolean isUp(int i)
	{
		if(keys[i] == false && keys[i] == pkeys[i])
			return true;
		else
			return false;
	}
	
	public static boolean isPressed(int i)
	{
		return keys[i] && !pkeys[i];
	}
	
	public static void setKey(int i, boolean b)
	{
		keys[i] = b;
	}
}