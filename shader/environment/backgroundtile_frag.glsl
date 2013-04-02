/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

 #version 140
 
 in vec4 frag_position;
 in vec4 frag_color;
 in vec4 frag_colorcode; 
 
 void main() {
 	gl_FragColor = (frag_color + frag_colorcode);
}