package com.dvdfu.planets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Player {
	Sprite sprite, shadow;
	float dist;
	Vector2 pos, speed, up;
	boolean grounded;
	Planet planet;
	
	public Player(Planet p) {
		sprite = new Sprite(Consts.atlas.findRegion("circle"));
		sprite.setSize(16, 16);
		shadow = new Sprite(Consts.atlas.findRegion("circle"));
		shadow.setSize(16, 16);
		shadow.setColor(0, 0, 0);
		pos = new Vector2(400, 400);
		planet = p;
		speed = new Vector2();
		up = new Vector2();
	}
	
	public void update() {
		up.set(pos.cpy().sub(planet.pos));
		dist = pos.cpy().sub(planet.pos).len() - planet.radius;
		
		if (grounded) {
			pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
			speed.setZero();
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				speed.add(up.cpy().rotate(-90).setLength(2 + dist / 50));
			} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				speed.add(up.cpy().rotate(90).setLength(2 + dist / 50));
			}
			pos.add(speed);
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				speed.add(up.cpy().setLength(4));
				grounded = false;
			}
		} else {
			if (pos.cpy().add(speed).sub(planet.pos).len() > planet.radius) {
				speed.sub(up.cpy().setLength(0.1f));
				pos.add(speed);
			} else {
				pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
				speed.setZero();
				grounded = true;
			}
		}
	}
	
	public void draw(SpriteBatch batch) {
		Vector2 shadowPos = planet.pos.cpy().add(up.cpy().setLength(planet.radius));
		shadow.drawCentered(batch, shadowPos.x, shadowPos.y);
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public void switchPlanet(Planet planet) {
		if (this.planet.equals(planet)) {
			return;
		}
		System.out.println(planet.radius);
		this.planet = planet;
	}
}
