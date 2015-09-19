package com.mikesantiago.mariofighter.states;

import static com.mikesantiago.mariofighter.GlobalVariables.PPM;
import static com.mikesantiago.mariofighter.GlobalVariables.backgroundcam;
import static com.mikesantiago.mariofighter.GlobalVariables.maincamera;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.mikesantiago.mariofighter.GlobalVariables;
import com.mikesantiago.mariofighter.assets.AssetManager;

public class TestState 
{
	private Stage stage;
	private Vector3 OriginalCameraPosition = new Vector3((float)(10 * 32), (float)(7 * 32 + 16), 0);
	
	public TestState()
	{
		stage = new Stage("assets/maps/test.tmx");
	}
	
	public void update()
	{
		if(Gdx.input.isKeyPressed(Keys.RIGHT))
		{
			Vector3 newPos = maincamera.position;
			Vector3 newBgPos = maincamera.position;
			newPos.x += 8;
			if(newPos.x > stage.getMapWidth() - (maincamera.zoom * 10f))
			{
				newPos.x = stage.getMapWidth() - (maincamera.zoom * 10f);
				return;
			}
			maincamera.position.set(newPos);
			maincamera.update();
			
			OrthographicCamera b2dcam = stage.getB2Dcam();
			b2dcam.position.set(maincamera.position.x / PPM, maincamera.position.y / PPM, maincamera.position.z / PPM);
			b2dcam.update();
			
			Vector3 tempBgOffset = stage.getBackgroundOffset();
			tempBgOffset.x -= 2;
			stage.UpdateBgOffset(tempBgOffset);
			backgroundcam.position.set(newBgPos);
			backgroundcam.update();
		}
		else if(Gdx.input.isKeyPressed(Keys.LEFT))
		{
			Vector3 newPos = maincamera.position;
			Vector3 newBgPos = maincamera.position;
			newPos.x -= 8;
			if(newPos.x < 0 + maincamera.zoom * 10f)
			{
				newPos.x = maincamera.zoom * 10f;
				return;
			}
			
			maincamera.position.set(newPos);
			maincamera.update();
			
			OrthographicCamera b2dcam = stage.getB2Dcam();
			b2dcam.position.set(maincamera.position.x / PPM, maincamera.position.y / PPM, maincamera.position.z / PPM);
			b2dcam.update();
			
			Vector3 tempBgOffset = stage.getBackgroundOffset();
			tempBgOffset.x += 2;
			stage.UpdateBgOffset(tempBgOffset);
			backgroundcam.position.set(newBgPos);
			backgroundcam.update();
		}
		else if(Gdx.input.isKeyPressed(Keys.UP))
		{
			Vector3 newPos = maincamera.position;
			Vector3 newBgPos = maincamera.position;
			newPos.y += 8;
			if(newPos.y > (stage.getMapHeight() - (maincamera.zoom * 7.5f)))
			{
				newPos.y = stage.getMapHeight() - (maincamera.zoom * 7.5f);
				return;
			}
			maincamera.position.set(newPos);
			maincamera.update();
			
			OrthographicCamera b2dcam = stage.getB2Dcam();
			b2dcam.position.set(maincamera.position.x / PPM, maincamera.position.y / PPM, maincamera.position.z / PPM);
			b2dcam.update();
			
			Vector3 tempBgOffset = stage.getBackgroundOffset();
			tempBgOffset.y -= 2;
			stage.UpdateBgOffset(tempBgOffset);
			backgroundcam.position.set(newBgPos);
			backgroundcam.update();
		}
		else if(Gdx.input.isKeyPressed(Keys.DOWN))
		{
			Vector3 newPos = maincamera.position;
			Vector3 newBgPos = maincamera.position;
			newPos.y -= 8;
			if(newPos.y < 0 + maincamera.zoom * 7.5f)
			{
				newPos.y = 0 + maincamera.zoom * 7.5f;
				return;
			}
			maincamera.position.set(newPos);
			maincamera.update();
			
			OrthographicCamera b2dcam = stage.getB2Dcam();
			b2dcam.position.set(maincamera.position.x / PPM, maincamera.position.y / PPM, maincamera.position.z / PPM);
			b2dcam.update();
			
			Vector3 tempBgOffset = stage.getBackgroundOffset();
			tempBgOffset.y += 2;
			stage.UpdateBgOffset(tempBgOffset);
			backgroundcam.position.set(newBgPos);
			backgroundcam.update();
		}
		else if(Gdx.input.isKeyJustPressed(Keys.LEFT_BRACKET))
		{
			Vector3 tempBgOffset = stage.getBackgroundOffset();
			tempBgOffset.z -= .1f;
			if(tempBgOffset.z < .5f)
				tempBgOffset.z = .5f;
			stage.UpdateBgOffset(tempBgOffset);
		}
		else if(Gdx.input.isKeyJustPressed(Keys.RIGHT_BRACKET))
		{
			Vector3 tempBgOffset = stage.getBackgroundOffset();
			tempBgOffset.z += .1f;
			if(tempBgOffset.z < 2f)
				tempBgOffset.z = 2f;
			stage.UpdateBgOffset(tempBgOffset);
		}
		else if(Gdx.input.isKeyJustPressed(Keys.NUM_1))
		{
			stage = new Stage("assets/maps/test.tmx");
			maincamera.position.set(OriginalCameraPosition);
			maincamera.update();
		}
		else if(Gdx.input.isKeyJustPressed(Keys.NUM_2))
		{
			stage = new Stage("assets/maps/test2.tmx");
			maincamera.position.set(OriginalCameraPosition);
			maincamera.update();
		}
		stage.update();
	}
	
	
	public void render(SpriteBatch sb)
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.render(sb);
	}

}
