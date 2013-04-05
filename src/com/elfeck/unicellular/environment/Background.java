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
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.Util;


public class Background {

	private int tileSize;
	private float layer;
	private EPHModel model;
	private EPHVaoEntry vaoRef;
	private GameSurface surface;
	private List<BackgroundQuad> quads;
	private EPHUniformVec4f color;

	protected Background(GameSurface surface) {
		this.surface = surface;
		tileSize = 100;
		layer = 0.1f;
		model = new EPHModel();
		quads = new ArrayList<BackgroundQuad>();
		color = new EPHUniformVec4f(0.1f, 0.45f, 0.25f, 1.0f);
		initModel();
		initQuads();
	}

	private void initModel() {
		model.addAttribute(4, "vertex_position");
		model.addAttribute(4, "vertex_color");
		model.create();
		model.setViewPort(surface.getWindowSpacePanelBounds());
		model.addToSurface(surface);
	}

	private void initQuads() {
		int[] bounds = surface.getLimitBounds();
		float offs;
		for (int i = bounds[0]; i < bounds[0] + bounds[2]; i += tileSize) {
			for (int j = bounds[1]; j < bounds[1] + bounds[3]; j += tileSize) {
				offs = Util.randomFloatInInterval(0, 0.02f) + (Util.testOnProbability(0.5f) ? 0.001f : 0);
				EPHVec4f currentColor = new EPHVec4f(offs, offs, offs, 0);
				quads.add(new BackgroundQuad(i, j, tileSize, tileSize, layer, currentColor));
			}
		}
		vaoRef = model.addToVao(assembleVertexValues(), assebleIndices(), "backgroundtile");
		vaoRef.registerUniformEntry("camera_offset", surface.getCamera().getCameraPosition());
		vaoRef.registerUniformEntry("color", color);
		vaoRef.registerUniformEntry("mvp_matrix", surface.getCamera().getVpMatrix());
		surface.getSurfaceLights().register(vaoRef);
	}

	private List<Float> assembleVertexValues() {
		List<Float> vertexValues = new ArrayList<Float>();
		for (BackgroundQuad quad : quads) {
			quad.fetchVertexData(vertexValues);
		}
		return vertexValues;
	}

	private List<Integer> assebleIndices() {
		List<Integer> indices = new ArrayList<Integer>();
		for (int i = 0; i < quads.size(); i++) {
			indices.add(0 + i * 4);
			indices.add(1 + i * 4);
			indices.add(2 + i * 4);
			indices.add(2 + i * 4);
			indices.add(3 + i * 4);
			indices.add(0 + i * 4);
		}
		return indices;
	}

	protected void delegateLogic(long delta) {

	}

}
