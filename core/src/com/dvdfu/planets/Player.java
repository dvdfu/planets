package com.dvdfu.planets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Player {
	Sprite sprite, shadow;
	float dist;
	Vector2 pos, vel, moveVel, acc, up;
	boolean grounded;
	boolean planeted;
	Planet planet;
	
	public Player(Planet p) {
		sprite = new Sprite(Consts.atlas.findRegion("player"));
		shadow = new Sprite(Consts.atlas.findRegion("player"));
		shadow.setColor(0, 0, 0);
		shadow.setSize(8, 4);
		shadow.setOrigin(4, 2);
		pos = new Vector2(400, 400);
		planet = p;
		vel = new Vector2();
		moveVel = new Vector2();
		up = new Vector2();
		acc = new Vector2();
	}
	
	public void update() {
		
		up.set(pos.cpy().sub(planet.pos));
		moveVel.setZero();
		if (planeted) {
			planetState();
		} else {
			playerState();
		}
		vel.add(acc);
		pos.add(vel);
		acc.setZero();
	}
	
	public void playerState() {
		if (grounded) {
			pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
			vel.setZero();
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				moveVel.x = -2;
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				moveVel.x = 2;
			}
			moveVel.rotate(up.angle() - 90);
			pos.add(moveVel);
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				vel.set(up.cpy().setLength(8));
				grounded = false;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
				planeted = true;
			}
		} else {
			if (up.len() < planet.radius) {
				pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
				vel.setZero();
				grounded = true;
			}
		}
	}
	
	public void planetState() {
		pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
		vel.setZero();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			planet.setAngle(planet.angle + 1);
			moveVel.x = -MathUtils.PI * planet.radius / 180;
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			planet.setAngle(planet.angle - 1);
			moveVel.x = MathUtils.PI * planet.radius / 180;
		}
		moveVel.rotate(up.angle() - 90);
		pos.add(moveVel);
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			planet.pos.add(up.cpy().setLength(6000 / planet.getMass()));
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
			planeted = false;
		}
	}
	
	public void draw(SpriteBatch batch) {
		sprite.setAngle(up.angle() - 90);
		shadow.setAngle(up.angle() - 90);
		Vector2 shadowPos = planet.pos.cpy().add(up.cpy().setLength(planet.radius - 8));
		shadow.drawCentered(batch, shadowPos.x, shadowPos.y);
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public float planetDistance(Planet planet) {
		return pos.cpy().sub(planet.pos).len() - planet.radius;
	}
	
	public void gravitate(Planet planet) {
		if (grounded || planeted) return;
		Vector2 diff = planet.pos.cpy().sub(pos);
		float length = diff.len2();
		diff = diff.setLength(planet.getMass());
		acc.add(diff.x / length, diff.y / length);
	}
	
	public void switchPlanet(Planet planet) {
		if (planeted) return;
		if (this.planet.equals(planet)) {
			return;
		}
		this.planet = planet;
	}
}
