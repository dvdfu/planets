package com.dvdfu.planets;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.AbstractScreen;

public class MainScreen extends AbstractScreen {
	SpriteBatch batch;
	ShapeRenderer shapes;
	OrthographicCamera cam;
	ArrayList<CelestialBody> planets;
	Player player;
	Vector2 lerpPos, cameraAngle;
	float lerpAngle;

	public MainScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		shapes = new ShapeRenderer();
		planets = new ArrayList<CelestialBody>();
		
		Planet p;
		for (int i = 0; i < 10; i++) {
			p = new Planet();
			p.pos.set(MathUtils.random(-900, 900), MathUtils.random(-900, 900));
			p.setRadius(MathUtils.random(80, 160));
			planets.add(p);
			for (int j = i / 4; j > 0; j--) {
				planets.add(p.addMoon(MathUtils.random(160, 300), MathUtils.random(24, 48), MathUtils.random(20, 60)));
			}
		}

		player = new Player(planets.get(0));
		cam = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		lerpPos = new Vector2(cam.position.x, cam.position.y);
		cameraAngle = new Vector2(cam.up.x, cam.up.y);
	}
	
	public void update(float delta) {
		lerpPos.lerp(player.planeted ? player.ground.pos : player.pos, 0.1f);
		cameraAngle.lerp(player.up().nor(), 0.05f);
		float playerAngle = player.up().angle();
		if (Math.abs(lerpAngle - playerAngle) > 180) {
			if (playerAngle > 180) lerpAngle += 360;
			else lerpAngle -= 360;
		}
		lerpAngle = MathUtils.lerp(lerpAngle, playerAngle, 0.05f);
		cam.position.set(lerpPos, 0);
		cam.up.set(cameraAngle.setAngle(lerpAngle), 0);
		cam.zoom = MathUtils.lerp(cam.zoom, player.planeted ? player.ground.radius / 50 + 1 : 1, 0.1f);
		cam.update();
		
		float d = Integer.MAX_VALUE;
		CelestialBody nearest = player.ground;
		for (CelestialBody p : planets) {
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
		for (CelestialBody p : planets) {
			p.draw(batch);
		}
		batch.setShader(Consts.passShader);
		player.draw(batch);
		batch.end();
		
		shapes.setProjectionMatrix(cam.combined);
		shapes.begin(ShapeType.Line);
		for (CelestialBody p : planets) {
			// p.drawShape(shapes);
		}
		shapes.end();
	}

	public void resize(int width, int height) {}

	public void show() {}

	public void hide() {}

	public void pause() {}

	public void resume() {}

	public void dispose() {}

}
