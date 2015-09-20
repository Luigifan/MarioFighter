package com.mikesantiago.mariofighter.states;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mikesantiago.mariofighter.GlobalVariables;
import com.mikesantiago.mariofighter.assets.AssetManager;
import com.mikesantiago.mariofighter.states.StateManager.GameState;

public class SplashState 
{
	private AssetManager manager;
	public SplashState(AssetManager tm)
	{
		manager = tm;
		
		if(manager.GetTexture("logo") == null)
			manager.LoadResource("logo", new Texture("assets/logo.png"));
	}
	
	private int framecounter = 0;
	
	public void update(float dt)
	{
		framecounter++;
		if(Math.round((float)framecounter / 60) >= 2) //after two seconds
		{
			GlobalVariables.stateManager.SetState(GameState.TEST);
		}
		
		//if(Gdx.input.isKeyPressed(Keys.SPACE))
		//{
		//	GlobalVariables.stateManager.SetState(GameState.TEST);
		//}
	}
	
	public void render(SpriteBatch sb)
	{
		int x, y;
		x = (GlobalVariables.V_WIDTH / 2) - (manager.GetTexture("logo").getWidth() / 2);
		y = (GlobalVariables.V_HEIGHT / 2) - (manager.GetTexture("logo").getHeight() / 2);
		
		Gdx.gl.glClearColor(1, 1, 1, 1);
		//Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		sb.begin();
		sb.draw(manager.GetTexture("logo"), x, y);
		sb.end();
	}
}
