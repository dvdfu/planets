package com.dvdfu.planets;

import java.util.ArrayList;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.Vector2;

public class MainGame extends ApplicationAdapter {
	SpriteBatch batch;
	OrthographicCamera cam;
	ArrayList<Planet> planets;
	Player player;
	Vector2 lerpPos, lerpAng;
	Environment e;
	ModelBatch models;
	
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
		p.pos.set(300, 100);
		p.setRadius(60);
		planets.add(p);
		
		for (int i = 0; i < 5; i++) {
			p = new Planet();
			p.pos.set(500, 300 - i * 150);
			p.setRadius(30);
			planets.add(p);
		}
		
		player = new Player(planets.get(0));
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		cam.near = -3000f; // defines closest distance to draw
		cam.far = 3000f; // defines farthest distance to draw
		lerpPos = new Vector2(cam.position.x, cam.position.y);
		lerpAng = new Vector2(cam.up.x, cam.up.y);
		
		e = new Environment();
		e.set(new ColorAttribute(ColorAttribute.AmbientLight, 0, 0, 0, 1f));
		e.add(new DirectionalLight().set(0.95f, 0.9f, 0.75f, -1.5f, -1f, -0.5f));
		e.add(new DirectionalLight().set(0.11f, 0.08f, 0.14f, 1.5f, 1.0f, 0.5f)); // direct light
		models = new ModelBatch();
	}

	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
		lerpPos.lerp(player.pos, 0.2f);
		lerpAng.lerp(player.up.cpy().nor(), 0.05f);
		cam.position.set(lerpPos, 0);
		cam.up.set(lerpAng, 0);
		cam.update();

		Planet c = planets.get(0);
		float d = Integer.MAX_VALUE;
		for (Planet p : planets) {
			if (p.distance(player) < d) {
				d = p.distance(player);
				c = p;
			}
		}
		player.switchPlanet(c);
		player.update();
		for (Planet planet : planets) {
			planet.update();
		}

		models.begin(cam);
		for (Planet p : planets) {
			models.render(p.shape, e);
		}
		models.end();
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		player.draw(batch);
		batch.end();
	}
}
