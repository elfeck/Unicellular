/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

#version 140

in vec4 vertex_position;
in vec4 vertex_color;

uniform vec2 camera_offset;
uniform mat4 mvp_matrix;

out vec4 frag_color;

void main() {
	gl_Position = vec4(vertex_position.xy - camera_offset.xy, vertex_position.zw) * mvp_matrix;
	frag_color = vertex_color;
}