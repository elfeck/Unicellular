/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.drawable.EPHModel;
import com.elfeck.unicellular.GameSurface;


public class Substance {

	private float layer;
	private EPHModel model;
	private GameSurface surface;
	private List<SubstanceCluster> clusters;

	public Substance(GameSurface surface) {
		this.surface = surface;
		layer = 0.2f;
		model = new EPHModel();
		clusters = new ArrayList<SubstanceCluster>();
		initModel();
		initClusters();
	}

	private void initModel() {
		model.addAttribute(4, "vertex_position");
		model.addAttribute(4, "vertex_color");
		model.create();
		model.setViewPort(surface.getWindowSpacePanelBounds());
		model.addToSurface(surface);
	}

	private void initClusters() {
		clusters.add(new SubstanceCluster(surface, model, layer));
	}

	protected void delegateLogic(long delta) {

	}

}
