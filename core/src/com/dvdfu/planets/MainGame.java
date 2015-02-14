package com.dvdfu.planets;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera cam;
	ArrayList<Planet> planets;
	Player player;
	Vector2 lerpPos, lerpAng;
	
	public void create () {
		new Consts();
		batch = new SpriteBatch();
		planets = new ArrayList<Planet>();
		Planet p = new Planet();
		p.pos.set(300, 200);
		p.setRadius(100);
		planets.add(p);
		p = new Planet();
		p.pos.set(0, 100);
		p.setRadius(60);
		planets.add(p);
		p = new Planet();
		p.pos.set(400, 200);
		p.setRadius(50);
		planets.add(p);
		
		player = new Player(planets.get(0));
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		lerpPos = new Vector2(cam.position.x, cam.position.y);
		lerpAng = new Vector2(cam.up.x, cam.up.y);
	}

	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		lerpPos.lerp(player.pos, 0.1f);
		lerpAng.lerp(player.up, 0.05f);
		cam.position.set(lerpPos, 0);
		cam.up.set(lerpAng, 0);
		cam.update();

		Planet c = planets.get(0);
		float d = c.distance(player);
		Planet p;
		for (int i = 1; i < planets.size(); i++) {
			p = planets.get(i);
			if (p.distance(player) < d) {
				c = p;
			}
		}
		player.switchPlanet(c);
		
		player.update();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		for (Planet planet : planets) {
			planet.draw(batch);
		}
		player.draw(batch);
		batch.end();
	}
}
