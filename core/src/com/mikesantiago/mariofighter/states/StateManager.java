package com.mikesantiago.mariofighter.states;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mikesantiago.mariofighter.assets.AssetManager;

public class StateManager 
{
	private GameState currentGameState = GameState.SPLASH;
	private SpriteBatch batch;
	private SplashState Splash;
	private TestState Test;
	private TestStateAndroid Test_Android;
	private AssetManager manager;
	
	public enum GameState
	{
		SPLASH, MENU, FIGHTERSELECT, STAGESELECT, GAME, TEST, TEST_ANDROID
	}
	
	public StateManager(SpriteBatch sb, AssetManager tm)
	{
		batch = sb;
		manager = tm;
		currentGameState = GameState.SPLASH;
		
		Splash = new SplashState(tm);
		Test = new TestState();
		Test_Android = new TestStateAndroid();
	}
	
	public void update(float dt)
	{
		switch(currentGameState)
		{
		case SPLASH:
			if(Splash == null)
				Splash = new SplashState(manager);
			Splash.update(dt);
			Splash.render(batch);
			break;
		case TEST:
			if(Test == null)
				Test = new TestState();
			Test.update(dt);
			Test.render(batch);
			break;
		case TEST_ANDROID:
			if(Test_Android == null)
				Test_Android = new TestStateAndroid();
			
			break;
		}
	}
	
	public void SetState(GameState state)
	{
		currentGameState = state;
	}

}
