/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.GameSurface;


public class SubstanceCluster {

	private List<SubstanceQuad> quads;

	protected SubstanceCluster(GameSurface surface) {
		quads = new ArrayList<SubstanceQuad>();
		quads.add(new SubstanceQuad(-10, -10, 20, 20, 0.2f, new EPHVec4f(1.0f, 0, 0, 1.0f), surface));
	}

	protected void fetchVertexData(List<Float> vertexValues) {
		for (SubstanceQuad quad : quads) {
			quad.fetchVertexData(vertexValues);
		}
	}

	protected int fetchIndexData(List<Integer> indices, int offset) {
		for (int i = 0; i < quads.size(); i++) {
			indices.add(0 + i * 4 + offset);
			indices.add(1 + i * 4 + offset);
			indices.add(2 + i * 4 + offset);

			indices.add(2 + i * 4 + offset);
			indices.add(3 + i * 4 + offset);
			indices.add(0 + i * 4 + offset);
		}
		return offset += quads.size() * 4;
	}

}
