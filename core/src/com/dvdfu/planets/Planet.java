package com.dvdfu.planets;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.VertexAttributes.Usage;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.math.Vector2;

public class Planet {
	Vector2 pos;
	float radius, gravity;
	ModelBuilder modelBuilder;
	Model model;
	ModelInstance shape;
	Material material;
	
	public Planet() {
		pos = new Vector2();
		gravity = 0.2f;

		modelBuilder = new ModelBuilder();
		material = new Material(ColorAttribute.createDiffuse(Color.WHITE));
		setRadius(10);
	}
	
	public void setRadius(float radius) {
		this.radius = radius;
		model = modelBuilder.createSphere(radius * 2, radius * 2, radius * 2, 60, 30,
				material, Usage.Position | Usage.Normal | Usage.TextureCoordinates);
		
		shape = new ModelInstance(model);
	}
	
	public void update() {
		shape.transform.setToTranslation(pos.x, pos.y, 0);
	}
	
	public void draw(ModelBatch batch, Environment environment) {
		batch.render(shape, environment);
	}
	
	public float distance(Player player) {
		return Math.max(player.pos.cpy().sub(pos).len() - radius, 0);
	}
}
