/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

#version 140

//@insert global_light.struct

const int max_light_count = 64;
const float light_parabolic_pixel = 10;
const float light_gaussian_pixel = 11;
const float light_para_gauss_pixel = 12;

in vec4 frag_colorcode;
in vec4 frag_light_factor;
in vec2 frag_model_position;

uniform vec4 color;
uniform Global_light[max_light_count] lights;
uniform float active_lights;
uniform float light_minimum;

vec4 compute_light();
//@insert global_light.decl
 
void main() {
 	gl_FragColor = (frag_colorcode + color) * clamp((frag_light_factor + compute_light()), 0, 1);
}

vec4 compute_light() {
	vec4 light_factor = vec4(0);
	for(int i = 0; i < active_lights; i++) {
		vec2 dist = frag_model_position - lights[i].position.xy;
		float type = lights[i].position.w;
		if(type == light_gaussian_pixel) light_factor += light_gaussian(dist, lights[i]);
		if(type == light_parabolic_pixel) light_factor += light_parabolic(dist, lights[i]);
		if(type == light_para_gauss_pixel) light_factor += light_para_gauss(dist, lights[i]);
	}
	light_factor.w = 1;
	return light_factor;
}

//@insert global_light.parabolic
//@insert global_light.gaussian
//@insert global_light.para_gauss