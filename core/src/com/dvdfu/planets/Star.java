package com.dvdfu.planets;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Star {
	Sprite sprite;
	float x, y, size, depth;
	
	public Star() {
		x = MathUtils.random(-1000, 1000);
		y = MathUtils.random(-1000, 1000);
		depth = MathUtils.random(0.2f, 1f);
		size = depth * 5;
		sprite = new Sprite(Consts.atlas.findRegion("circle"));
		sprite.setSize(size, size);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, x, y);
	}
	
	public void move(Vector2 d) {
		x += d.x / depth;
		y += d.y / depth;
	}
}
