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
	Planet planet, nearest;
	
	public Player(Planet p) {
		sprite = new Sprite(Consts.atlas.findRegion("player"));
		shadow = new Sprite(Consts.atlas.findRegion("player"));
		shadow.setColor(0, 0, 0);
		shadow.setSize(8, 4);
		shadow.setOrigin(4, 2);
		pos = new Vector2(400, 400);
		planet = p;
		nearest = p;
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
		moveVel.x += -planet.radius * planet.angularSpeed * MathUtils.PI / 180;
		moveVel.rotate(up.angle() - 90);
		pos.add(moveVel);
		vel.add(acc);
		pos.add(vel);
		acc.setZero();
	}
	
	public void playerState() {
		if (grounded) {
			pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
			vel.setZero();
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				moveVel.x -= 2;
			} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				moveVel.x += 2;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				vel.set(up.cpy().setLength(8));
				grounded = false;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				planeted = true;
			}
		} else {
			if (up.cpy().add(vel).len() < planet.radius) {
				pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
				vel.setZero();
				grounded = true;
			}
		}
	}
	
	public void planetState() {
		pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
		pos.add(planet.vel);
		vel.setZero();
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			planet.angularSpeed = MathUtils.lerp(planet.angularSpeed, 3, 0.1f);
		} else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			planet.angularSpeed = MathUtils.lerp(planet.angularSpeed, -3, 0.1f);
		}
		pos.add(moveVel);
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			planet.vel.lerp(up.cpy().setLength(200 / planet.radius), 0.05f);
		}else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
			planet.vel.lerp(up.cpy().setLength(200 / planet.radius).scl(-1), 0.05f);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			planeted = false;
		}
	}
	
	public void draw(SpriteBatch batch) {
		sprite.setAngle(up.angle() - 90);
//		shadow.setAngle(up.angle() - 90);
//		Vector2 shadowPos = planet.pos.cpy().add(up.cpy().setLength(planet.radius - 8));
//		shadow.drawCentered(batch, shadowPos.x, shadowPos.y);
		sprite.drawCentered(batch, pos.x + up.cpy().setLength(8).x, pos.y + up.cpy().setLength(8).y);
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
		nearest = planet;
		if (planetDistance(nearest) < 0) {
			this.planet = nearest;
		}
	}
}
