/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.unicellular.GameSurface;


public class Substance {

	private EPHModel model;
	private GameSurface surface;
	private EPHVaoEntry vaoRef;
	private List<SubstanceCluster> clusters;

	public Substance(GameSurface surface) {
		this.surface = surface;
		model = new EPHModel();
		clusters = new ArrayList<SubstanceCluster>();
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
		clusters.add(new SubstanceCluster(surface));
		vaoRef = model.addToVao(assembleVertexValues(), assembleIndexData(), "substance");
		vaoRef.registerUniformEntry("camera_offset", surface.getCamera().getCameraPosition());
		vaoRef.registerUniformEntry("mvp_matrix", surface.getCamera().getVpMatrix());
	}

	private List<Float> assembleVertexValues() {
		List<Float> vertexValues = new ArrayList<Float>();
		for (SubstanceCluster cluster : clusters) {
			cluster.fetchVertexData(vertexValues);
		}
		return vertexValues;
	}

	private List<Integer> assembleIndexData() {
		List<Integer> indices = new ArrayList<Integer>();
		int offset = 0;
		for (SubstanceCluster cluster : clusters) {
			offset = cluster.fetchIndexData(indices, offset);
		}
		return indices;
	}

	protected void delegateLogic(long delta) {

	}

}
