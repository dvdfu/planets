uniform sampler2D u_texture;
uniform mat4 u_projTrans;

varying vec4 v_color;
varying vec2 v_texCoords;

#define PI 3.14159265358979323844

void main() {
	vec2 map = v_texCoords * 2.0 - 1.0;
	float radius = length(map);
	
	if (radius > 1.0) {
		gl_FragColor = vec4(0.0);
	} else {
		float angle = atan(map.y, map.x) / 2.0 / PI + 0.5;
		vec2 polar = vec2(mod(angle * 8.0, 1.0), 1.0 - radius);
		gl_FragColor = texture2D(u_texture, polar) * v_color;
	}
}