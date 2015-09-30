package com.mikesantiago.mariofighter;

import static com.mikesantiago.mariofighter.GlobalVariables.backgroundcam;
import static com.mikesantiago.mariofighter.GlobalVariables.batch;
import static com.mikesantiago.mariofighter.GlobalVariables.hudcam;
import static com.mikesantiago.mariofighter.GlobalVariables.maincamera;
import static com.mikesantiago.mariofighter.GlobalVariables.manager;
import static com.mikesantiago.mariofighter.GlobalVariables.stateManager;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics.DisplayMode;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector3;
import com.mikesantiago.mariofighter.assets.AssetManager;
import com.mikesantiago.mariofighter.states.StateManager;
public class MainGameClass extends ApplicationAdapter 
{
	private float accumulator = 0;
	
	@Override
	public void create () 
	{
		//System.out.println("Assets location: " + Gdx.files.getLocalStoragePath());
		System.out.println("Mario asset should be in : " + Gdx.files.internal("assets/mariotest.png").path());
		SetupCameras();
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.LoadResource("mario", new Texture(Gdx.files.internal("assets/mariotest.png")));
		manager.LoadResource("cursor", new Texture(Gdx.files.internal("assets/cursor_main.png")));
		
		Pixmap p = new Pixmap(Gdx.files.internal("assets/cursor_main.png"));
		Gdx.input.setCursorImage(p, 0, 0);
		
		stateManager = new StateManager(batch, manager);
		
		manager.LoadResource("logo", new Texture(Gdx.files.internal("assets/logo.png")));
		BitmapFont fnt = new BitmapFont(Gdx.files.internal("assets/ingame-font-small.fnt"), false);
		manager.SetFont(fnt);
		
		Gdx.input.setInputProcessor(new CustomInputProcessor());
		
		for(DisplayMode m : Gdx.graphics.getDisplayModes())
		{
			System.out.println(String.format("%s x %s @ %shz %s BBP", m.width, m.height, m.refreshRate, m.bitsPerPixel));
		}
	}

	private void SetupCameras()
	{
		maincamera = new OrthographicCamera(20, 15);
		maincamera.translate(20, 15);
		maincamera.zoom = 32f;
		maincamera.position.set(new Vector3((float)(10 * 32), (float)(7 * 32 + 16), 0));
		maincamera.update();
		
		hudcam = new OrthographicCamera(20, 15);
		hudcam.translate(20, 15);
		hudcam.zoom = 32f;
		hudcam.position.set(new Vector3(10 * 32, 7 * 32, 0));
		hudcam.update();
		
		backgroundcam = new OrthographicCamera(20, 15);
		backgroundcam.translate(20, 15);
		backgroundcam.zoom = 32f;
		backgroundcam.position.set(new Vector3(10 * 32, 7 * 32, 0));
		backgroundcam.update();
	}
	
	public void update()
	{
		accumulator += Gdx.graphics.getDeltaTime();
		while(accumulator >= GlobalVariables.STEP)
		{
			accumulator -= GlobalVariables.STEP;
			stateManager.update(GlobalVariables.STEP);
			Input.update();
		}
		if(Gdx.input.isKeyJustPressed(Keys.F11))
		{
			Gdx.graphics.getDisplayModes();
			if(!isFullscreen)
			{
				for(DisplayMode m : Gdx.graphics.getDisplayModes())
				{
					if(m.width == 640 && m.height == 480)
					{
						Gdx.graphics.setDisplayMode(640, 480, true);
						isFullscreen = true;
						return;
					}
				}
			}
			else
			{
				Gdx.graphics.setDisplayMode(640, 480, false);
				isFullscreen = false;
			}
		}
	}
	private boolean isFullscreen = false;
	@Override
	public void render () 
	{
		update();
	}
}
