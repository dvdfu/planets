package com.dvdfu.lib;

import com.badlogic.gdx.math.MathUtils;

public class Animator {
	float start, end;
	enum Ease {
		LINEAR, QUADRATIC
	}
	Ease easeFunction;
	
	private float interpolate(float alpha) {
		switch (easeFunction) {
		
		}
		return 0;
	}
	
	private float easeLinear(float alpha) {
		return MathUtils.lerp(start, end, alpha);
	}
	
	private float easeQuadratic(float alpha) {
		return start + (end - start) * alpha * alpha;
	}
}
