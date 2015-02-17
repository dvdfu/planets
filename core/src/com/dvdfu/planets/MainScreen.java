package com.dvdfu.planets;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.AbstractScreen;
import com.dvdfu.lib.Sprite;

public class MainScreen extends AbstractScreen {
	SpriteBatch batch;
	OrthographicCamera cam;
	ArrayList<Planet> planets;
	ArrayList<Star> stars;
	Player player;
	Vector2 lerpPos, lerpAng;
	Environment e;
	ModelBatch models;
	Sprite star;

	public MainScreen(MainGame game) {
		super(game);
		batch = new SpriteBatch();
		planets = new ArrayList<Planet>();
		
		Planet p;
		for (int i = 0; i < 10; i++) {
			p = new Planet();
			p.pos.set(MathUtils.random(-400, 400), MathUtils.random(-400, 400));
			p.setRadius(MathUtils.random(5, 60));
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
		e.add(new DirectionalLight().set(1, 1, 0.9f, -1.5f, -1f, -1f));
		e.add(new DirectionalLight().set(0.11f, 0.08f, 0.14f, 1.5f, 1.0f, 0.5f));
		models = new ModelBatch();
		
		star = new Sprite(Consts.atlas.findRegion("circle"));
		stars = new ArrayList<Star>();
		for (int i = 0; i < 2000; i++) {
			stars.add(new Star());
		}
	}

	public void render(float delta) {
		lerpPos.lerp(player.pos, 0.2f);
		lerpAng.lerp(player.up.cpy().nor(), 0.05f);
		cam.position.set(lerpPos, 0);
		cam.up.set(lerpAng, 0);
		cam.update();

		Planet c = planets.get(0);
		float d = Integer.MAX_VALUE;
		for (Planet p : planets) {
			player.gravitate(p);
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
		for (Star s : stars) {
			s.move(player.speed);
		}
		
		batch.setProjectionMatrix(cam.combined);
		batch.begin();
		for (Star s : stars) {
			s.draw(batch);
		}
		batch.end();

		models.begin(cam);
		for (Planet p : planets) {
			models.render(p.shape, e);
		}
		models.end();
		
		batch.begin();
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
