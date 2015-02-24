package com.dvdfu.planets;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Planet {
	Vector2 pos;
	Sprite sprite;
	float radius, density, angle;
	
	public Planet() {
		pos = new Vector2();
		sprite = new Sprite(Consts.atlas.findRegion("circle"));
		density = 0.2f;
		setRadius(10);
	}
	
	public void setAngle(float angle) {
		this.angle = angle;
		sprite.setAngle(angle);
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
		sprite.setSize(radius * 2, radius * 2);
		sprite.setOrigin(radius, radius);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public float distance(Player player) {
		return Math.max(player.pos.cpy().sub(pos).len() - radius, 0);
	}
	
	public float[] getPoints(ArrayList<Vector2> vertices) {
		float[] points = new float[vertices.size() * 2];
		for (int i = 0; i < vertices.size(); i++) {
			points[i * 2] = vertices.get(i).x + pos.x;
			points[i * 2 + 1] = vertices.get(i).y + pos.y;
		}
		return points;
	}
	
	public float getMass() {
		return density * MathUtils.PI * radius * radius;
	}
}
