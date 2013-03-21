/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import com.elfeck.ephemeral.drawable.EPHDrawable;
import com.elfeck.ephemeral.drawable.EPHDrawablePolygon;
import com.elfeck.ephemeral.drawable.EPHVertex;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.GameSurface;


public class Background {

	private GameSurface surface;
	private EPHDrawablePolygon shape;
	private EPHVec2f offset;

	protected Background(GameSurface surface) {
		this.surface = surface;
		offset = new EPHVec2f(0, 0);
		initShape();
	}

	static boolean once = true;

	private void initShape() {
		EPHVertex[] vertices = new EPHVertex[4];
		// int[] bounds = surface.getLimitBounds();
		int[] bounds = new int[] { -20, -20, 40, 40 };
		vertices[0] = new EPHVertex(0, new EPHVec4f[] { new EPHVec4f(bounds[0], bounds[1] + bounds[3], 0f, 1f), new EPHVec4f(1f, 0f, 0.25f, 1f) });
		vertices[1] = new EPHVertex(1, new EPHVec4f[] { new EPHVec4f(bounds[0] + bounds[2], bounds[1] + bounds[3], 0f, 1f), new EPHVec4f(1f, 0f, 0.25f, 1f) });
		vertices[2] = new EPHVertex(2, new EPHVec4f[] { new EPHVec4f(bounds[0] + bounds[2], bounds[1], 0f, 1f), new EPHVec4f(1f, 0f, 0.25f, 1f) });
		vertices[3] = new EPHVertex(3, new EPHVec4f[] { new EPHVec4f(bounds[0], bounds[1], 0f, 1f), new EPHVec4f(1f, 0f, 0.25f, 1f) });
		shape = new EPHDrawablePolygon("background", vertices, offset);
		shape.addUniformVecf("camera_offset", surface.getCamera().getCameraOffset());
		shape.addUniformMatf("mvp_matrix", surface.getCamera().getVpMatrix());
	}

	public EPHDrawable getShape() {
		return shape;
	}

}
