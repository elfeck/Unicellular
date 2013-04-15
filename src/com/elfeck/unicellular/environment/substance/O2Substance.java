/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.glContext.EPHRenderUtils;
import com.elfeck.ephemeral.glContext.EPHVao;
import com.elfeck.ephemeral.glContext.EPHVaoBuilder;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.GameSurface;


public class O2Substance implements EPHEntity {

	private float layer;
	private EPHVao vao;
	private EPHVaoEntry entry;
	private O2SubstanceQuadSource sourceQuad;
	private GameSurface surface;

	private EPHVec4f baseColor;

	public O2Substance(GameSurface surface) {
		this.surface = surface;
		layer = 0.2f;
		vao = null;
		entry = null;
		baseColor = new EPHVec4f(0.3f, 0.7f, 0.6f, 1f);
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
		vao = builder.create(EPHRenderUtils.MODE_TRIANGLES, EPHRenderUtils.USAGE_DYNAMIC_DRAW);
		surface.addVao(vao);
		surface.setViewport(vao);
		entry = vao.addEntry("substance_O2");
		surface.registerCameraAsUniform(entry);
		surface.registerMvpMatrixAsUniform(entry);
	}

	private void initQuads() {
		sourceQuad = new O2SubstanceQuadSource(0, 0, 24, 7, surface, this, entry);
		surface.addEntity(sourceQuad);
	}

	protected float getLayer() {
		return layer;
	}

	protected EPHVec4f getBaseColor() {
		return baseColor;
	}

	protected EPHVaoEntry getVaoEntry() {
		return entry;
	}

}
