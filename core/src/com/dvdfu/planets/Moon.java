package com.dvdfu.planets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.dvdfu.lib.Sprite;

public class Moon extends CelestialBody {
	Planet parent;
	float orbitRadius, orbitAngle;
	
	public Moon(Planet parent, float distance, float period, float moonRadius) {
		super();
		this.parent = parent;
		sprite = new Sprite(new TextureRegion(new Texture(Gdx.files.internal("img/moon.png"))));
		setRadius(moonRadius);
		orbitRadius = distance;
		angularSpeed = distance * MathUtils.PI2 / 60 / period;
	}
	
	public void update() {
		vel.set(pos);
		orbitAngle += angularSpeed;
		angle += angularSpeed;
		sprite.setAngle(angle);
		pos.setLength(orbitRadius);
		pos.setAngle(orbitAngle);
		pos.add(parent.pos);
		vel.sub(pos).scl(-1);
	}

	public void drawShape(ShapeRenderer batch) {
		batch.setColor(Color.GRAY);
		batch.circle(parent.pos.x, parent.pos.y, orbitRadius);
		batch.setColor(Color.GREEN);
		batch.line(pos, pos.cpy().add(vel.scl(60)));
	}
}
