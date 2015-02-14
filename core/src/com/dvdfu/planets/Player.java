package com.dvdfu.planets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Player {
	Sprite sprite;
	float angle, dist;
	Vector2 pos, speed, up, a;
	boolean grounded;
	Planet planet;
	
	public Player(Planet p) {
		sprite = new Sprite(Consts.atlas.findRegion("circle"));
		sprite.setSize(16, 16);
		pos = new Vector2();
		angle = 0;
		dist = 100;
		planet = p;
		speed = new Vector2();
		up = new Vector2(1, 1);
		a = new Vector2();
	}
	
	public void update() {
		up.setLength(1);
		up.setAngleRad(angle);
		a.set(up.cpy().setLength(dist));
		pos.set(planet.pos.cpy().add(a));
		
		if (grounded) {
			if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
				speed.y = 5;
				grounded = false;
			}
		} else {
			if (dist + speed.y < planet.radius) {
				dist = planet.radius;
				speed.y = 0;
				grounded = true;
			} else {
				speed.y -= planet.gravity;
				dist += speed.y;
			}
		}
		
		if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
			speed.x = 3;
		} else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
			speed.x = -3;
		} else {
			speed.lerp(new Vector2(0, speed.y), 0.1f);
		}
		
		angle -= speed.x / planet.radius;
	}
	
	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, pos.x, pos.y);
	}
}
