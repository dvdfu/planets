package com.dvdfu.planets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;
import com.dvdfu.lib.Sprite;

public class Planet {
	Sprite sprite;
	Vector2 pos;
	float radius, gravity;
	ModelBuilder modelBuilder;
	Model model;
	ModelInstance shape;
	
	public Planet() {
		sprite = new Sprite(Consts.atlas.findRegion("circle"));
		pos = new Vector2(300, 200);
		gravity = 0.2f;

		modelBuilder = new ModelBuilder();
		setRadius(100);
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
		model = modelBuilder.createSphere(radius * 2, radius * 2, radius * 2, 60, 30,
				new Material(ColorAttribute.createDiffuse(Color.CYAN)),
				Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		
		shape = new ModelInstance(model);
		sprite.setSize(radius * 2, radius * 2);
	}
	
	public void update() {
		shape.transform.setToTranslation(pos.x, pos.y, 0);
	}
	
	public void draw(SpriteBatch batch) {
		sprite.drawCentered(batch, pos.x, pos.y);
	}
	
	public float distance(Player player) {
		return Math.max(player.pos.cpy().sub(pos).len() - radius, 0);
	}
}
