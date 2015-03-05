package com.dvdfu.planets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public abstract class CelestialBody {
	Vector2 pos, vel;
	Sprite sprite;
	float radius, density;
	float angle, angularSpeed;
	
	public CelestialBody() {
		pos = new Vector2();
		vel = new Vector2();
		sprite = new Sprite(new TextureRegion(new Texture(Gdx.files.internal("img/planet.png"))));
		density = 1;
		setRadius(10);
	}
	
	public void update() {
		angle += angularSpeed;
		sprite.setAngle(angle);
		angularSpeed = MathUtils.lerp(angularSpeed, 0, 0.1f);
		pos.add(vel);
		vel.lerp(Vector2.Zero, 0.01f);
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
		sprite.setSize(radius * 2, radius * 2);
		sprite.setOrigin(radius, radius);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public abstract void drawShape(ShapeRenderer batch);
	
	public float distance(Player player) {
		return Math.max(player.pos.cpy().sub(pos).len() - radius, 0);
	}
	
	public float getMass() {
		return density * MathUtils.PI * radius * radius;
	}
}
