/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.template.ColoredQuad;


public class SubstanceCluster {

	private float layer;
	private List<SubstanceQuad> quads;
	private GameSurface surface;
	private EPHVaoEntry vaoRef;
	private EPHModel model;
	private EPHUniformVec4f color;

	protected SubstanceCluster(GameSurface surface, EPHModel model, float layer) {
		this.surface = surface;
		this.layer = layer;
		this.model = model;
		quads = new ArrayList<SubstanceQuad>();
		color = new EPHUniformVec4f(0.8f, 0.8f, layer, 1f);
		initQuads();
	}

	private void initQuads() {

		vaoRef = model.addToVao(fetchVertexData(), fetchIndexData(), "substance");
		vaoRef.registerUniformEntry("color", color);
		surface.registerCameraAsUniform(vaoRef);
		surface.registerMvpMatrixAsUniform(vaoRef);
	}
	private List<Float> fetchVertexData() {
		List<Float> vertexValues = new ArrayList<Float>();
		for (SubstanceQuad quad : quads) {
			quad.fetchVertexData(vertexValues);
		}
		return vertexValues;
	}

	private List<Integer> fetchIndexData() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < quads.size(); i++) {
			ColoredQuad.fetchIndices(i * 4, indices);
		}
		return indices;
	}

}
