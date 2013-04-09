/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec1f;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.Util;
import com.elfeck.unicellular.template.ColoredQuad;


public class Background {

	private int tileSize, tileFactor;
	private float layer;
	private EPHModel model;
	private EPHVaoEntry vaoRef;
	private GameSurface surface;
	private List<BackgroundQuad> quads;
	private EPHUniformVec4f color;

	protected Background(GameSurface surface) {
		this.surface = surface;
		tileSize = 16;
		tileFactor = 8;
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
		model.addAttribute(2, "vertex_center");
		model.create();
		model.setViewPort(surface.getWindowSpacePanelBounds());
		model.addToSurface(surface);
	}

	private void initQuads() {
		int[] bounds = surface.getLimitBounds();
		float offs;
		for (int y = bounds[1]; y < (bounds[1] + bounds[3]); y += tileSize * tileFactor) {
			for (int x = bounds[0]; x < (bounds[0] + bounds[2]); x += tileSize * tileFactor) {
				offs = Util.randomFloatInInterval(0, 0.015f) + (Util.testOnProbability(0.5f) ? 0.01f : 0);
				// offs = 0;
				EPHVec4f currentColor = new EPHVec4f(offs, offs, offs, 0);
				for (int h = 0; h < tileFactor; h++) {
					for (int w = 0; w < tileFactor; w++) {
						quads.add(new BackgroundQuad(x + w * tileSize, y + h * tileSize, tileSize, tileSize, layer, currentColor));
					}
				}
			}
		}
		System.out.println("Init Quads: [Count = " + quads.size() + "]  [VBO Size: " + quads.size() * 4 * 4 / 1024 + " kB]");
		vaoRef = model.addToVao(assembleVertexValues(), assebleIndices(), "backgroundtile");
		vaoRef.registerUniformEntry("color", color);
		vaoRef.registerUniformEntry("light_minimum", new EPHUniformVec1f(0.3f));
		surface.registerCameraAsUniform(vaoRef);
		surface.registerMvpMatrixAsUniform(vaoRef);
		surface.registerSurfaceLightsAsUniform(vaoRef);
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
			ColoredQuad.fetchIndices(i * 4, indices);
		}
		return indices;
	}

	protected void delegateLogic(long delta) {

	}

}
