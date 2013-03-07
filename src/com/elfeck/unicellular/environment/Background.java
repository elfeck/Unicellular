/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import com.elfeck.ephemeral.drawable.EPHDrawable;
import com.elfeck.ephemeral.drawable.EPHDrawablePolygon;
import com.elfeck.ephemeral.drawable.EPHVertex;
import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec4f;

public class Background {

	private EPHDrawablePolygon shape;
	private EPHMat4f mvpMatrix;

	protected Background() {
		mvpMatrix = new EPHMat4f(new float[][] {
												{ 1, 0, 0, 0 },
												{ 0, 1, 0, 0 },
												{ 0, 0, 1, 0 },
												{ 0, 0, 0, 1 }
		});
		initShape();
	}

	private void initShape() {
		EPHVertex[] vertices = new EPHVertex[4];
		vertices[0] = new EPHVertex(0, new EPHVec4f[] { new EPHVec4f(-1f, 1f, 0f, 1f), new EPHVec4f(0f, 0f, 0.25f, 1f) });
		vertices[1] = new EPHVertex(1, new EPHVec4f[] { new EPHVec4f(1f, 1f, 0f, 1f), new EPHVec4f(0f, 0f, 0.25f, 1f) });
		vertices[2] = new EPHVertex(2, new EPHVec4f[] { new EPHVec4f(1f, -1f, 0f, 1f), new EPHVec4f(0f, 0f, 0.25f, 1f) });
		vertices[3] = new EPHVertex(3, new EPHVec4f[] { new EPHVec4f(-1f, -1f, 0f, 1f), new EPHVec4f(0f, 0f, 0.25f, 1f) });
		shape = new EPHDrawablePolygon("background", vertices);
		shape.addUniformMatf("mvp_matrix", mvpMatrix);
	}

	public EPHDrawable getShape() {
		return shape;
	}

}
