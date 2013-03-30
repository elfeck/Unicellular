/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import java.util.List;

import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class BackgroundQuad {

	private int width, height;
	private float depth;
	private EPHVec2f position;
	private EPHVec4f color;

	public BackgroundQuad(float x, float y, int width, int height, float depth, EPHVec4f color) {
		this.width = width;
		this.height = height;
		position = new EPHVec2f(x, y);
		this.depth = depth;
		this.color = color;
	}

	public void fetchVertexData(List<Float> vertexValues) {
		for (int i = 0; i < 4; i++) {
			if (i == 0) {
				vertexValues.add(position.getX());
				vertexValues.add(height + position.getY());
			}
			if (i == 1) {
				vertexValues.add(width + position.getX());
				vertexValues.add(height + position.getY());
			}
			if (i == 2) {
				vertexValues.add(width + position.getX());
				vertexValues.add(position.getY());
			}
			if (i == 3) {
				vertexValues.add(position.getX());
				vertexValues.add(position.getY());
			}
			vertexValues.add(depth);
			vertexValues.add(1f);
			color.fetchData(vertexValues);
		}
	}
}
