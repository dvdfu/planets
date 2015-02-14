package com.dvdfu.planets;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera cam;
	Planet p;
	Player player;
	
	public void create () {
		new Consts();
		batch = new SpriteBatch();
		p = new Planet();
		player = new Player(p);
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
	}

	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		cam.position.set(player.pos, 0);
		cam.up.set(player.up, 0);
		cam.update();
		
		player.update();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		p.draw(batch);
		player.draw(batch);
		batch.end();
	}
}
