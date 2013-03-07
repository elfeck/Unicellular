/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

#version 140

in vec4 vertex_position;
in vec4 vertex_color;

uniform mat4 mvp_matrix;

out vec4 fragment_color;

void main() {
	gl_Position = vertex_position * mvp_matrix;
	fragment_color = vertex_color;
}
