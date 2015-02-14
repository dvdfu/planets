package com.dvdfu.planets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Player {
	Sprite sprite;
	float dist;
	Vector2 pos, speed, up;
	boolean grounded;
	Planet planet;
	
	public Player(Planet p) {
		sprite = new Sprite(Consts.atlas.findRegion("circle"));
		sprite.setSize(16, 16);
		pos = new Vector2(400, 400);
		dist = 500;
		planet = p;
		speed = new Vector2();
		up = new Vector2();
		sprite.setColor(1, 0, 0);
	}
	
	public void update() {
		up.set(pos.cpy().sub(planet.pos).nor());
		dist = pos.cpy().sub(planet.pos).len();
		
		if (grounded) {
			pos.set(planet.pos.cpy().add(up.cpy().scl(planet.radius)));
			speed.setZero();
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				speed.set(up.cpy().scl(5));
				grounded = false;
			}
		} else {
			if (pos.cpy().add(speed).sub(planet.pos).len() > planet.radius) {
				speed.sub(up.cpy().scl(0.1f));
				pos.add(speed);
			} else {
				pos.set(planet.pos.cpy().add(up.cpy().scl(planet.radius)));
				speed.setZero();
				grounded = true;
			}
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			pos.add(up.cpy().rotate(-90).scl(3));
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			pos.add(up.cpy().rotate(90).scl(3));
		}
	}
	
	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public void switchPlanet(Planet planet) {
		if (this.planet.equals(planet)) {
			return;
		}
		
		this.planet = planet;
	}
}
