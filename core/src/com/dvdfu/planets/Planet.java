package com.dvdfu.planets;

import java.util.ArrayList;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Planet extends CelestialBody {
	ArrayList<Moon> moons;
	
	public Planet() {
		super();
		moons = new ArrayList<Moon>();
	}
	
	public Moon addMoon(float radius, float period, float moonRadius) {
		Moon moon = new Moon(this, radius, period, moonRadius);
		moons.add(moon);
		return moon;
	}

	public void drawShape(ShapeRenderer batch) {
		
	}
}
