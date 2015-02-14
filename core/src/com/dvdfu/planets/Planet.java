package com.dvdfu.planets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Planet {
	Sprite sprite;
	Vector2 pos;
	float radius, gravity;
	
	public Planet() {
		sprite = new Sprite(Consts.atlas.findRegion("circle"));
		pos = new Vector2(300, 200);
		setRadius(100);
		gravity = 0.2f;
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
		sprite.setSize(radius * 2, radius * 2);
	}
	
	public void update() {
		
	}
	
	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public float distance(Player player) {
		return player.pos.cpy().sub(pos).len() - radius;
	}
}
