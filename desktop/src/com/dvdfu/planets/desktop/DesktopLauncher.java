package com.dvdfu.planets.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.texturepacker.TexturePacker;
import com.dvdfu.planets.MainGame;

public class DesktopLauncher {
	public static void main (String[] arg) {
//		TexturePacker.process("unpacked/", "/home/david/workspace/planets/planets/core/assets/img", "images");
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		new LwjglApplication(new MainGame(), config);
	}
}
