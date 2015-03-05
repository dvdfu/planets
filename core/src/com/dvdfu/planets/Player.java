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
	float groundSpeed;
	Vector2 pos, vel, moveVel, acc;
	boolean grounded;
	boolean planeted;
	CelestialBody ground, nearest;
	
	public Player(CelestialBody p) {
		sprite = new Sprite(Consts.atlas.findRegion("player"));
		shadow = new Sprite(Consts.atlas.findRegion("player"));
		shadow.setColor(0, 0, 0);
		shadow.setSize(8, 4);
		shadow.setOrigin(4, 2);
		pos = new Vector2(400, 400);
		ground = p;
		nearest = p;
		vel = new Vector2();
		moveVel = new Vector2();
		acc = new Vector2();
		landOnPlanet(p);
	}
	
	public void update() {
		moveVel.setZero();
		if (planeted) {
			planetState();
		} else {
			playerState();
		}
		moveVel.rotate(up().angle() - 90);
		pos.add(moveVel);
	}
	
	private void playerState() {
		vel.add(acc);
		acc.setZero();
		pos.add(vel);
		if (grounded) {
			moveVel.x = -ground.radius * ground.angularSpeed * MathUtils.PI / 180;
			landOnPlanet(nearest);
			if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
				moveVel.x -= 2;
			}
			if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
				moveVel.x += 2;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
				vel.set(up().cpy().setLength(4)).add(ground.vel).add(moveVel.rotate(up().angle() - 90));
				grounded = false;
			}
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE) && ground instanceof Planet) {
				planeted = true;
			}
		} else {
			if (planetDistance(nearest) < 0) {
				landOnPlanet(nearest);
			}
		}
	}
	
	private void planetState() {
		moveVel.x = -ground.radius * ground.angularSpeed * MathUtils.PI / 180;
		landOnPlanet(nearest);
		if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			ground.angularSpeed += 0.1f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			ground.angularSpeed -= 0.1f;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
			Vector2 move = new Vector2(1, 0);
			move.setLength(500 / ground.radius).setAngle(up().angle());
			ground.vel.set(move);
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
			planeted = false;
		}
	}
	
	public Vector2 up() {
		return upFrom(ground);
	}
	
	public Vector2 upFrom(CelestialBody body) {
		return pos.cpy().sub(body.pos);
	}
	
	public void landOnPlanet(CelestialBody body) {
		Vector2 p = body.pos.cpy();
		p.add(pos.cpy().sub(p).setLength(body.radius + 8));
		pos.set(p);
		vel.set(body.vel);
		ground = body;
		grounded = true;
	}
	
	public void draw(SpriteBatch batch) {
		sprite.setAngle(up().angle() - 90);
		sprite.drawCentered(batch, pos.x, pos.y);
		// sprite.drawCentered(batch, ground.pos.x, ground.pos.y);
	}
	
	public float planetDistance(CelestialBody planet) {
		return pos.cpy().add(vel).sub(planet.pos).len() - planet.radius - 8;
	}
	
	public void gravitate(CelestialBody body) {
		if (grounded || planeted) return;
		float length = upFrom(body).len2() * upFrom(body).len() / 10;
		Vector2 mass = upFrom(body).setLength(body.getMass()).scl(-1);
		acc.add(mass.x / length, mass.y / length);
	}
	
	public void switchPlanet(CelestialBody planet) {
		if (planeted) return;
		nearest = planet;
	}
}
