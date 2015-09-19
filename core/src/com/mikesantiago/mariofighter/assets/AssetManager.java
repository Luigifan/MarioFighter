package com.mikesantiago.mariofighter.assets;

import java.io.File;
import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class AssetManager 
{
	private HashMap<String, Texture> Textures;
	private BitmapFont font;
	
	public AssetManager()
	{
		System.out.println("Texture manager loaded.");
		Textures = new HashMap<String, Texture>();
	}
	
	public void SetFont(BitmapFont fnt){font = fnt;}
	
	///This will replace already existing textures!
	public void LoadResource(String key, Texture texture)
	{
		if(Textures.get(key) != null)
		{
			System.out.println("Replacing texture with key '" + key + "'.");
			Textures.remove(key);
			Textures.put(key, texture);
		}
		else
		{
			System.out.println("Adding texture under key '" + key + "'.");
			Textures.put(key, texture);			
		}
	}
	
	public BitmapFont GetFont()
	{
		return font;
	}
	
	public Texture GetTexture(String key)
	{
		return Textures.get(key);
	}
	
	public void DisposeTexture(String key)
	{
		Textures.remove(key);
	}

}
