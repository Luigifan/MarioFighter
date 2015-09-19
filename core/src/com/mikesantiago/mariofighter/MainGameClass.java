package com.mikesantiago.mariofighter;

import static com.mikesantiago.mariofighter.GlobalVariables.batch;
import static com.mikesantiago.mariofighter.GlobalVariables.backgroundcam;
import static com.mikesantiago.mariofighter.GlobalVariables.manager;
import static com.mikesantiago.mariofighter.GlobalVariables.stateManager;
import static com.mikesantiago.mariofighter.GlobalVariables.maincamera;
import static com.mikesantiago.mariofighter.GlobalVariables.hudcam;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mikesantiago.mariofighter.assets.AssetManager;
import com.mikesantiago.mariofighter.states.StateManager;
public class MainGameClass extends ApplicationAdapter 
{
	@Override
	public void create () 
	{
		System.out.println("Assets location: " + Gdx.files.getLocalStoragePath());
		batch = new SpriteBatch();
		manager = new AssetManager();
		stateManager = new StateManager(batch, manager);
		
		manager.LoadResource("logo", new Texture("assets/logo.png"));
		BitmapFont fnt = new BitmapFont(new FileHandle(Gdx.files.getLocalStoragePath() + "/assets/ingame-font-small.fnt"), false);
		manager.SetFont(fnt);
		
		SetupCameras();
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
		stateManager.update();
	}
	
	@Override
	public void render () 
	{
		update();
	}
}
