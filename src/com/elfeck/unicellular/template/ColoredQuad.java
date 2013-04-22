/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.template;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;


public class ColoredQuad {

	protected float layer;
	protected float width, height;
	protected EPHVec2f position;
	protected EPHVec4f color;

	public ColoredQuad(float x, float y, float width, float height, float layer, EPHVec4f color) {
		this.width = width;
		this.height = height;
		position = new EPHVec2f(x, y);
		this.layer = layer;
		this.color = color;
	}

	protected void fetchAdditionalData(List<Float> vertexValues) {

	}

	public EPHVec2f getPosition() {
		return position;
	}

	public EPHVec2f getCenterPosition() {
		return position.copy().addVec2f(width / 2.0f, height / 2.0f);
	}

	public float getWidth() {
		return width;
	}

	public float getHeight() {
		return height;
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
			vertexValues.add(layer);
			vertexValues.add(1f);
			color.fetchData(vertexValues);
			fetchAdditionalData(vertexValues);
		}
	}

	public List<Float> assembleVertexData() {
		List<Float> vertexValues = new ArrayList<Float>();
		fetchVertexData(vertexValues);
		return vertexValues;
	}

	public static void fetchIndices(int offset, List<Integer> indices) {
		indices.add(0 + offset);
		indices.add(1 + offset);
		indices.add(2 + offset);
		indices.add(2 + offset);
		indices.add(3 + offset);
		indices.add(0 + offset);
	}

	public static List<Integer> assembleIndices() {
		List<Integer> indices = new ArrayList<Integer>();
		fetchIndices(0, indices);
		return indices;
	}

}
