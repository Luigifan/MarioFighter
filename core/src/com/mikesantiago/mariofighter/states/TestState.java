package com.mikesantiago.mariofighter.states;

import static com.mikesantiago.mariofighter.GlobalVariables.PPM;
import static com.mikesantiago.mariofighter.GlobalVariables.backgroundcam;
import static com.mikesantiago.mariofighter.GlobalVariables.maincamera;

import java.awt.Point;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mikesantiago.mariofighter.GlobalVariables;
import com.mikesantiago.mariofighter.Input;
import com.mikesantiago.mariofighter.PlayerOne.Direction;

public class TestState 
{
	public enum MOVEDIRECTION
	{
		LEFT, RIGHT, UP, DOWN, STOP
	}
	
	private Stage stage;
	private Vector3 OriginalCameraPosition = new Vector3((float)(10 * 32), (float)(7 * 32 + 16), 0);
	
	public TestState()
	{
		stage = new Stage("assets/maps/test.tmx");
		
		cameraBounds = new Point[4];
		cameraBounds[0] = new Point(32,32);
		cameraBounds[1] = new Point(GlobalVariables.V_WIDTH - 32,32);
		cameraBounds[2] = new Point(32,GlobalVariables.V_HEIGHT - 32);
		cameraBounds[3] = new Point(GlobalVariables.V_WIDTH - 32,GlobalVariables.V_HEIGHT - 32);
		
	}
	
	private Point[] cameraBounds = new Point[4];
	
	private void resetCameraBounds()
	{
		cameraBounds = new Point[4];
		cameraBounds[0] = new Point(32,32);
		cameraBounds[1] = new Point(GlobalVariables.V_WIDTH - 32,32);
		cameraBounds[2] = new Point(32,GlobalVariables.V_HEIGHT - 32);
		cameraBounds[3] = new Point(GlobalVariables.V_WIDTH - 32,GlobalVariables.V_HEIGHT - 32);
	}
	
	private boolean keyed = false;
	public void update(float dt)
	{
		stage.update(dt);
		if(stage.getPlayerOne().getMoving())
		{
			if(stage.getPlayerOne().getCurrentDirection() == Direction.RIGHT)
			{
				if(stage.getPlayerOne().getPlayerBody().getPosition().x * PPM > cameraBounds[1].x - 256f)
				{
					cameraBounds[1].x += 2 + Math.ceil(stage.getPlayerOne().getPlayerBody().getLinearVelocity().x);

					Vector3 newPos = maincamera.position;
					Vector3 newBgPos = maincamera.position;
					newPos.x += 3 + Math.ceil(stage.getPlayerOne().getPlayerBody().getLinearVelocity().x);
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
					tempBgOffset.x -= .75f;
					stage.UpdateBgOffset(tempBgOffset);
					backgroundcam.position.set(newBgPos);
					backgroundcam.update();
				}
			}
			if(stage.getPlayerOne().getCurrentDirection() == Direction.LEFT)
			{
				if(stage.getPlayerOne().getPlayerBody().getPosition().x * PPM < cameraBounds[1].x - 256f)
				{
					cameraBounds[1].x -= 2 + -(Math.ceil(stage.getPlayerOne().getPlayerBody().getLinearVelocity().x));
					Vector3 newPos = maincamera.position;
					Vector3 newBgPos = maincamera.position;
					newPos.x -= 3 + -(Math.ceil(stage.getPlayerOne().getPlayerBody().getLinearVelocity().x));
					if(newPos.x < 0 + (maincamera.zoom * 10f))
					{
						newPos.x = (maincamera.zoom * 10f);
						return;
					}
					maincamera.position.set(newPos);
					maincamera.update();

					OrthographicCamera b2dcam = stage.getB2Dcam();
					b2dcam.position.set(maincamera.position.x / PPM, maincamera.position.y / PPM, maincamera.position.z / PPM);
					b2dcam.update();

					Vector3 tempBgOffset = stage.getBackgroundOffset();
					tempBgOffset.x += .75f;
					stage.UpdateBgOffset(tempBgOffset);
					backgroundcam.position.set(newBgPos);
					backgroundcam.update();
				}
			}
		}
	}
	
	public void render(SpriteBatch sb)
	{
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		stage.render(sb);
		sb.begin();
		sb.setProjectionMatrix(GlobalVariables.hudcam.combined);
		GlobalVariables.manager.GetFont().draw(sb, Gdx.graphics.getFramesPerSecond() + " FPS", 0, GlobalVariables.V_HEIGHT);
		sb.setProjectionMatrix(maincamera.combined);
		
		sb.end();
	}

}
