package com.mikesantiago.mariofighter;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mikesantiago.mariofighter.assets.AssetManager;
import com.mikesantiago.mariofighter.states.StateManager;

public class GlobalVariables 
{
	public static SpriteBatch batch;
	public static AssetManager manager;
	public static StateManager stateManager;
	public static final int SCALE = 2;
	public static int V_WIDTH = 640; //640
	public static int V_HEIGHT = 480; //480
	
	///Pixels per meter for Box2D
	public static final float PPM = 100;
	
	
	public static OrthographicCamera maincamera;
	public static OrthographicCamera hudcam;
}
