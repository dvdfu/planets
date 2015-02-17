package com.dvdfu.planets;

import com.dvdfu.lib.Main;

public class MainGame extends Main {
	private final boolean SHADER_USED = false;
	
	public void create() {
		super.create();
		enterScreen(new MainScreen(this));
	}
	
	public void render() {
		if (SHADER_USED) {
			super.renderShader();
		} else {
			super.render();
		}
	}
}
