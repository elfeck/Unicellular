/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.glContext.EPHVaoEntryDataSet;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.template.ColoredQuad;


public abstract class O2SubstanceQuad extends ColoredQuad implements EPHEntity {

	protected boolean dead;
	protected O2Substance substance;
	protected GameSurface surface;
	protected EPHVaoEntryDataSet dataSet;

	public O2SubstanceQuad(float x, float y, float size, O2Substance substance) {
		super(x, y, size, size, substance.getLayer(), substance.getBaseColor().copy());
		this.substance = substance;
		dead = false;
		surface = substance.getSurface();
		dataSet = substance.getVaoEntry().addData(assembleVertexData(), ColoredQuad.assembleIndices());
	}

	@Override
	public boolean isDead() {
		return dead;
	}

	protected void updateDataSet() {
		substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
	}

}
