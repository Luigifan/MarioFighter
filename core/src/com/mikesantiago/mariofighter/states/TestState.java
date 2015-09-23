package com.mikesantiago.mariofighter.states;

import static com.mikesantiago.mariofighter.GlobalVariables.PPM;
import static com.mikesantiago.mariofighter.GlobalVariables.backgroundcam;
import static com.mikesantiago.mariofighter.GlobalVariables.maincamera;

import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.mikesantiago.mariofighter.CustomTextListener;
import com.mikesantiago.mariofighter.GlobalVariables;
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
		stage = new Stage("assets/maps/test.tmx", false);
	}
	
	public void update(float dt)
	{
		stage.update(dt);
		if(stage.getSpawnPlayer())
			updateCameraWithPlayer();
		else //assume map preview mode
		{
			if(Gdx.app.getType() == ApplicationType.Desktop)
				updateCameraWithKeys();
			else if(Gdx.app.getType() == ApplicationType.Android)
				updateCameraWithMouse();
		}
	}
	
	private void updateCameraWithKeys()
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
	}
	
	private enum CameraUpdateType
	{
		XONLY, YONLY, XANDY
	}
	private void updateCameraWithMouse()
	{
		CameraUpdateType updateType = CameraUpdateType.XANDY;
		if(Gdx.input.isTouched())
		{
			Vector3 oldCamPos = maincamera.position.cpy();
			Vector3 newCamPos = new Vector3(oldCamPos.x + -Gdx.input.getDeltaX(), 
					oldCamPos.y + Gdx.input.getDeltaY(), maincamera.position.z);
			
			if(newCamPos.y > stage.getMapHeight() - (maincamera.zoom * 7.5f))
			{
				updateType = CameraUpdateType.XONLY;
			}
			else if(newCamPos.y < 0 + (maincamera.zoom * 7.5f))
			{
				updateType = CameraUpdateType.XONLY;
			}
			else if(newCamPos.x < 0 + (maincamera.zoom * 10f))
			{
				updateType = CameraUpdateType.YONLY;
			}
			else if(newCamPos.x > stage.getMapWidth() - (maincamera.zoom * 10f))
			{
				updateType = CameraUpdateType.YONLY;
			}
			switch(updateType)
			{
			case XANDY:
				maincamera.position.set(
						new Vector2(oldCamPos.x + -Gdx.input.getDeltaX(), 
								oldCamPos.y + Gdx.input.getDeltaY()), maincamera.position.z);
			break;
			case XONLY:
				maincamera.position.set(
						new Vector2(oldCamPos.x + -Gdx.input.getDeltaX(), 
								oldCamPos.y), maincamera.position.z);
			break;
			case YONLY:
				maincamera.position.set(
						new Vector2(oldCamPos.x, 
								oldCamPos.y + Gdx.input.getDeltaY()), maincamera.position.z);
			break;
			}
			
			maincamera.update();
		}
	}
	
	private void updateCameraWithPlayer()
	{

		Vector3 oldCameraPosition = maincamera.position.cpy();
		
		maincamera.position.x = Math.min(
				Math.max(stage.getPlayerOne().getPlayerBody().getPosition().x * PPM, GlobalVariables.V_WIDTH / 2), 
				stage.getMapWidth() - (GlobalVariables.V_WIDTH / 2));
		
		maincamera.position.y = Math.min(
				Math.max(stage.getPlayerOne().getPlayerBody().getPosition().y * PPM, GlobalVariables.V_WIDTH / 2), 
				stage.getMapHeight() - (GlobalVariables.V_HEIGHT / 2)) - 32f;
		
		if(oldCameraPosition.x != maincamera.position.x)
		{
			if(stage.getPlayerOne().getCurrentDirection() == Direction.RIGHT)
			{
				Vector3 tempBgOffset = stage.getBackgroundOffset();
				Vector3 newBgPos = maincamera.position;
				tempBgOffset.x -= GlobalVariables.BGOFFSET;
				stage.UpdateBgOffset(tempBgOffset);
				backgroundcam.position.set(newBgPos);
				backgroundcam.update();
			}
			else if(stage.getPlayerOne().getCurrentDirection() == Direction.LEFT)
			{
				Vector3 tempBgOffset = stage.getBackgroundOffset();
				Vector3 newBgPos = maincamera.position;
				tempBgOffset.x += GlobalVariables.BGOFFSET;
				stage.UpdateBgOffset(tempBgOffset);
				backgroundcam.position.set(newBgPos);
				backgroundcam.update();
			}
		}
		else if(oldCameraPosition.y != maincamera.position.y)
		{
			float changeInY = maincamera.position.y - oldCameraPosition.y;
			if(Math.signum(changeInY) == 1.0f) //moving up
			{
				Vector3 tempBgOffset = stage.getBackgroundOffset();
				Vector3 newBgPos = maincamera.position;
				tempBgOffset.y -= GlobalVariables.BGOFFSET;
				stage.UpdateBgOffset(tempBgOffset);
				backgroundcam.position.set(newBgPos);
				backgroundcam.update();
			}
			else if(Math.signum(changeInY) == -1.0f) //moving down
			{
				Vector3 tempBgOffset = stage.getBackgroundOffset();
				Vector3 newBgPos = maincamera.position;
				tempBgOffset.y += GlobalVariables.BGOFFSET;
				stage.UpdateBgOffset(tempBgOffset);
				backgroundcam.position.set(newBgPos);
				backgroundcam.update();
			}
		}
		maincamera.update();
		OrthographicCamera b2dcam = stage.getB2Dcam();
		b2dcam.position.set(maincamera.position.x / PPM, maincamera.position.y / PPM, maincamera.position.z / PPM);
		b2dcam.update();
		
		
		if(Gdx.input.isKeyPressed(Keys.CONTROL_LEFT) || Gdx.input.isKeyPressed(Keys.CONTROL_RIGHT))
		{
			if(Gdx.input.isKeyPressed(Keys.SHIFT_LEFT))
			{
				if(Gdx.input.isKeyJustPressed(Keys.ENTER))
				{
					stage.setAllowUnlimitedHops(!stage.getAllowedUnlimitedHops());
				}
			}
		}
		if(Gdx.input.isKeyJustPressed(Keys.GRAVE))
		{
			stage.setDebugMode(!stage.getDebugMode());
		}
		if(Gdx.input.isKeyJustPressed(Keys.R))
		{
			reset();
		}
	}
	
	private void reset()
	{
		stage.getPlayerOne().getPlayerBodyDef().position.set(new Vector2(32f / PPM, 256f / PPM));
		stage.getPlayerOne().getPlayerBody().setTransform(new Vector2(32f / PPM, 256f / PPM), 0);
		
		maincamera = new OrthographicCamera(20, 15);
		maincamera.translate(20, 15);
		maincamera.zoom = 32f;
		maincamera.position.set(new Vector3((float)(10 * 32), (float)(7 * 32 + 16), 0));
		maincamera.update();
		
		backgroundcam = new OrthographicCamera(20, 15);
		backgroundcam.translate(20, 15);
		backgroundcam.zoom = 32f;
		backgroundcam.position.set(new Vector3((float)(10 * 32), (float)(7 * 32 + 16), 0));
		backgroundcam.update();
		
	}
	
	private boolean cheatJustInput = false;
	private boolean openedPrompt = false;
	private void CheckCheats()
	{
		CustomTextListener tempInputListener = new CustomTextListener();
		Gdx.input.getTextInput(tempInputListener, "Cheats", "Cheat", "Type cheat here");
		openedPrompt = true;
		if(tempInputListener.hasInput)
		{
			String input = tempInputListener.getText().toLowerCase().trim();
			if(input.equals("hippityhop"))
			{
				stage.setAllowUnlimitedHops(true);
				cheatJustInput = false;
				openedPrompt = false;
			}
			else if(input.equals("fairgame"))
			{
				stage.setAllowUnlimitedHops(false);
				cheatJustInput = false;
				openedPrompt = false;
			}
			else
			{
				cheatJustInput = false;
				openedPrompt = false;
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
