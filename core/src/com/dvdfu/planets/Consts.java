package com.dvdfu.planets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.dvdfu.lib.Shader;

public class Consts {
	public static TextureAtlas atlas;
	public static Shader passShader;
	public static Shader polarShader;
	
	public Consts() {
		AssetManager manager = new AssetManager();
		atlas = new TextureAtlas();
		
		manager.load("img/images.atlas", TextureAtlas.class);
		manager.finishLoading();
		atlas = manager.get("img/images.atlas", TextureAtlas.class);
		
		passShader = new Shader("shaders/pass.vsh", "shaders/pass.fsh");
		polarShader = new Shader("shaders/pass.vsh", "shaders/polar.fsh");
		polarShader.setUniformf("resolution", 16, 32);
	}
}
