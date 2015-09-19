package com.mikesantiago.mariofighter.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mikesantiago.mariofighter.MainGameClass;

public class DesktopLauncher 
{
	public static void main (String[] arg) 
	{
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		
		config.title = "Mario Fighter";
		config.allowSoftwareMode = true;
		config.vSyncEnabled = true;
		config.width = 640;
		config.height = 480;
		
		System.out.println(
				String.format("===Lwjgl App Configuration===\n   Size: %sx%s"
						+ "\n   Title: %s"
						+ "\n   VSync Enabled: %s", config.width, config.height, config.title, config.vSyncEnabled));
		new LwjglApplication(new MainGameClass(), config);
	}
}
