package com.mikesantiago.mariofighter;

import com.badlogic.gdx.Input.TextInputListener;


public class CustomTextListener implements TextInputListener
{
	private String textInputted;
	
	@Override
	public void input(String text) 
	{
		textInputted = text;
		hasInput = true;
	}

	public boolean hasInput = false;
	
	@Override
	public void canceled() 
	{
		textInputted = null;
	}
	
	public String getText() 
	{
		return textInputted == null ? "" : textInputted;
	}
}
