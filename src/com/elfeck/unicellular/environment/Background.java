/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.glContext.EPHVao;
import com.elfeck.ephemeral.glContext.EPHVaoBuilder;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVaoEntryDataSet;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec1f;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.Util;
import com.elfeck.unicellular.template.ColoredQuad;


public class Background implements EPHEntity {

	private int tileSize, tileFactor;
	private float layer;
	private EPHVao vao;
	private EPHVaoEntry vaoEntry;
	private EPHVaoEntryDataSet tileDataSet;
	private GameSurface surface;
	private List<BackgroundQuad> quads;
	private EPHUniformVec4f color;
	private EPHUniformVec1f lightMinimum;

	protected Background(GameSurface surface) {
		this.surface = surface;
		tileSize = 16;
		tileFactor = 8;
		layer = 0.1f;
		vao = null;
		quads = new ArrayList<BackgroundQuad>();
		color = new EPHUniformVec4f(0.1f, 0.45f, 0.25f, 1.0f);
		lightMinimum = new EPHUniformVec1f(0.175f);
		initModel();
		initQuads();
	}

	@Override
	public void doLogic(long delta) {

	}

	@Override
	public boolean isDead() {
		return false;
	}

	private void initModel() {
		EPHVaoBuilder builder = new EPHVaoBuilder();
		builder.addAttribute(4, "vertex_position");
		builder.addAttribute(4, "vertex_color");
		builder.addAttribute(2, "vertex_center");
		vao = builder.create();
		surface.setViewport(vao);
		surface.addVao(vao);
	}

	private void initQuads() {
		int[] bounds = surface.getLimitBounds().toIntArray();
		float offs;
		for (int y = bounds[1]; y < (bounds[1] + bounds[3]); y += tileSize * tileFactor) {
			for (int x = bounds[0]; x < (bounds[0] + bounds[2]); x += tileSize * tileFactor) {
				offs = Util.randomFloatInInterval(0, 0.015f) + (Util.testOnProbability(0.5f) ? 0.01f : 0);
				EPHVec4f currentColor = new EPHVec4f(offs, offs, offs, 0);
				for (int h = 0; h < tileFactor; h++) {
					for (int w = 0; w < tileFactor; w++) {
						quads.add(new BackgroundQuad(x + w * tileSize, y + h * tileSize, tileSize, tileSize, layer, currentColor));
					}
				}
			}
		}
		vaoEntry = vao.addEntry("backgroundtile");
		vaoEntry.registerUniformEntry("color", color);
		vaoEntry.registerUniformEntry("light_minimum", lightMinimum);
		surface.registerCameraAsUniform(vaoEntry);
		surface.registerMvpMatrixAsUniform(vaoEntry);
		surface.registerSurfaceLightsAsUniform(vaoEntry);
		tileDataSet = vaoEntry.addData(assembleVertexValues(), assebleIndices());
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

}
