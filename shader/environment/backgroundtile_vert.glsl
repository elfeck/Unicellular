/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

#version 140

//@insert global_light.struct

const int max_light_count = 64;
const float light_parabolic_block = 0;
const float light_gaussian_block = 1;
const float light_para_gauss_block = 2;

in vec2 vertex_center;
in vec4 vertex_position;
in vec4 vertex_color;

uniform vec2 camera_offset;
uniform mat4 mvp_matrix;
uniform Global_light[max_light_count] lights;
uniform float active_lights;
uniform float light_minimum;

out vec4 frag_colorcode;
out vec4 frag_light_factor;
out vec2 frag_model_position;

vec4 compute_light();
//@insert global_light.decl

void main() {
	vec4 position = vec4(vertex_position.xy - camera_offset.xy, vertex_position.zw) * mvp_matrix;
	gl_Position = position;
	frag_model_position = vertex_position.xy;
	frag_colorcode = vertex_color;
	frag_light_factor = compute_light();
}

vec4 compute_light() {
	vec4 light_factor = vec4(0);
	for(int i = 0; i < active_lights; i++) {
		vec2 dist = vertex_center - lights[i].position.xy;
		float type = lights[i].position.w;
		if(type == light_parabolic_block) light_factor += light_parabolic(dist, lights[i]);
		if(type == light_gaussian_block) light_factor += light_gaussian(dist, lights[i]);
		if(type == light_para_gauss_block) light_factor += light_para_gauss(dist, lights[i]);
	}
	light_factor.w = 1;
	return light_factor;
}

//@insert global_light.parabolic
//@insert global_light.gaussian
//@insert global_light.para_gauss
