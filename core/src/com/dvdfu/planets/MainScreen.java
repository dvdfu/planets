package com.dvdfu.planets;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.AbstractScreen;

public class MainScreen extends AbstractScreen {
	SpriteBatch batch;
	OrthographicCamera cam;
	ArrayList<Planet> planets;
	Player player;
	Vector2 lerpPos, lerpAngle;

	public MainScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		planets = new ArrayList<Planet>();
		
		Planet p;
		for (int i = 0; i < 10; i++) {
			p = new Planet();
			p.pos.set(MathUtils.random(-400, 400), MathUtils.random(-400, 400));
			p.setRadius(MathUtils.random(30, 160));
			planets.add(p);
		}
		
		player = new Player(planets.get(0));
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		lerpPos = new Vector2(cam.position.x, cam.position.y);
		lerpAngle = new Vector2(cam.up.x, cam.up.y);
	}
	
	public void update(float delta) {
		lerpPos.lerp(player.planeted ? player.planet.pos : player.pos, 0.2f);
		lerpAngle.lerp(player.up.cpy().nor(), 0.05f);
		cam.position.set(lerpPos, 0);
		cam.up.set(lerpAngle, 0);
		cam.zoom = MathUtils.lerp(cam.zoom, player.planeted ? player.planet.radius / 50 + 1 : 1, 0.1f);
		cam.update();
		
		float d = Integer.MAX_VALUE;
		Planet nearest = player.planet;
		for (Planet p : planets) {
			p.update();
			player.gravitate(p);
			if (player.planetDistance(p) < d) {
				d = player.planetDistance(p);
				nearest = p;
			}
		}
		player.switchPlanet(nearest);
		player.update();
	}

	public void render(float delta) {
		update(delta);
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		batch.setShader(Consts.polarShader);
		for (Planet p : planets) {
			p.draw(batch);
		}
		batch.setShader(Consts.passShader);
		player.draw(batch);
		batch.end();
	}

	public void resize(int width, int height) {}

	public void show() {}

	public void hide() {}

	public void pause() {}

	public void resume() {}

	public void dispose() {}

}
