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
	Vector2 pos, speed, move, up;
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
		move = new Vector2();
	}
	
	public void update() {
		up.set(pos.cpy().sub(planet.pos));
		dist = pos.cpy().sub(planet.pos).len() - planet.radius;
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			move.x = MathUtils.lerp(move.x, 3, 0.1f);
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			move.x = MathUtils.lerp(move.x, -3, 0.1f);
		} else {
			move.x = MathUtils.lerp(move.x, 0, 0.2f);
		}
		
		if (grounded) {
			pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
			move.y = 0;
			
			if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) {
				move.y = 4;
				grounded = false;
			}
		} else {
			if (pos.cpy().add(speed).sub(planet.pos).len() > planet.radius) {
				move.y -= 0.1f;
			} else {
				pos.set(planet.pos.cpy().add(up.cpy().setLength(planet.radius)));
				move.y = 0;
				grounded = true;
			}
		}
		speed.set(move.cpy().rotate(up.angle() - 90));
		pos.add(speed);
	}
	
	public void draw(SpriteBatch batch) {
		Vector2 shadowPos = planet.pos.cpy().add(up.cpy().setLength(planet.radius - 4));
		shadow.drawCentered(batch, shadowPos.x, shadowPos.y);
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public void switchPlanet(Planet planet) {
		if (this.planet.equals(planet)) {
			return;
		}
		this.planet = planet;
//		up.set(pos.cpy().sub(planet.pos));
//		move.set(speed.cpy().rotate(up.angle() + 90));
		move.rotate(up.angle() - pos.cpy().sub(planet.pos).angle());
	}
}
