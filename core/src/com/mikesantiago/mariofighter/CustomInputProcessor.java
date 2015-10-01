package com.mikesantiago.mariofighter;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;

public class CustomInputProcessor extends InputAdapter 
{
	public boolean keyDown(int k)
	{
		if(k == Keys.UP)
		{
			Input.setKey(Input.MOVE_UP, true);
		}
		if(k == Keys.DOWN)
		{
			Input.setKey(Input.MOVE_DOWN, true);
		}
		if(k == Keys.LEFT)
		{
			Input.setKey(Input.MOVE_LEFT, true);
		}
		if(k == Keys.RIGHT)
		{
			Input.setKey(Input.MOVE_RIGHT, true);
		}
		if(k == Keys.LEFT_BRACKET)
		{
			Input.setKey(Input.ZOOM_OUT, true);
		}
		if(k == Keys.RIGHT_BRACKET)
		{
			Input.setKey(Input.ZOOM_IN, true);
		}
		if(k == Keys.R)
		{
			Input.setKey(Input.RESET_POS, true);
		}
		if(k == Keys.Z)
		{
			Input.setKey(Input.JUMP, true);
		}
		if(k == com.badlogic.gdx.Input.Buttons.LEFT)
		{
			Input.setKey(Input.CLICK, true);
		}
		return true;
	}
	public boolean keyUp(int k)
	{
		if(k == Keys.UP)
		{
			Input.setKey(Input.MOVE_UP, false);
		}
		if(k == Keys.DOWN)
		{
			Input.setKey(Input.MOVE_DOWN, false);
		}
		if(k == Keys.LEFT)
		{
			Input.setKey(Input.MOVE_LEFT, false);
		}
		if(k == Keys.RIGHT)
		{
			Input.setKey(Input.MOVE_RIGHT, false);
		}
		if(k == Keys.LEFT_BRACKET)
		{
			Input.setKey(Input.ZOOM_OUT, false);
		}
		if(k == Keys.RIGHT_BRACKET)
		{
			Input.setKey(Input.ZOOM_OUT, false);
		}
		if(k == Keys.R)
		{
			Input.setKey(Input.RESET_POS, false);
		}
		if(k == Keys.Z)
		{
			Input.setKey(Input.JUMP, false);
		}
		if(k == com.badlogic.gdx.Input.Buttons.LEFT)
		{
			Input.setKey(Input.CLICK, false);
		}
		
		return true;
	}

}
