package com.dvdfu.lib;

import java.util.Stack;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.dvdfu.planets.Consts;

public class Main extends Game {
	private Stack<AbstractScreen> screens;
	
	private FrameBuffer fb;
	private SpriteBatch fbBatch;

	public void create() {
		new Consts();
		screens = new Stack<AbstractScreen>();
		fb = new FrameBuffer(Format.RGBA8888, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), false);
		fb.getColorBufferTexture().setFilter(TextureFilter.Nearest, TextureFilter.Nearest);
		fbBatch = new SpriteBatch();
		fbBatch.setShader(Consts.passShader);
	}

	public void enterScreen(AbstractScreen screen) {
		if (!screens.isEmpty()) {
			screens.peek().pause();
		}
		screens.push(screen);
		setScreen(screens.peek());
	}

	public void changeScreen(AbstractScreen screen) {
		if (screens.isEmpty()) {
			return;
		}
		screens.pop();
		screens.push(screen);
		setScreen(screens.peek());
	}

	public void exitScreen() {
		if (screens.isEmpty()) {
			Gdx.app.exit();
		}
		screens.pop();
		screens.peek().resume();
		setScreen(screens.peek());
	}

	public void dispose() {
		fb.dispose();
		fbBatch.dispose();
	}

	public void render() {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		if (getScreen() != null) {
			super.render();
		}
	}
	
	public void renderShader() {
		fb.begin();
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		if (getScreen() != null) {
			super.render();
		}
		fb.end();
		fbBatch.begin();
		fbBatch.draw(fb.getColorBufferTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), 0, 0, 1, 1);
		fbBatch.end();
	}

	public void resize(int width, int height) {
		super.resize(width, height);
	}

	public void pause() {}

	public void resume() {}
}
