/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.glContext.EPHVaoEntryDataSet;
import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.TimeUnitSingle;
import com.elfeck.unicellular.template.ColoredQuad;


public class O2SubstanceQuad extends ColoredQuad implements EPHEntity {

	private boolean reappearing;
	private O2Substance substance;
	private EPHVaoEntryDataSet dataSet;
	private TimeUnitSingle timeUnit;

	public O2SubstanceQuad(float x, float y, float width, float height, float layer, EPHVec4f color, O2Substance substance) {
		super(x, y, width, height, layer, color);
		this.substance = substance;
		timeUnit = new TimeUnitSingle(1000);
		dataSet = substance.getVaoEntry().addData(assembleVertexData(), ColoredQuad.assembleIndices());
	}

	@Override
	public void doLogic(long delta) {
		if (reappearing) {
			timeUnit.enterDelta(delta);
			if (timeUnit.passedDuration()) {
				reappearing = false;
				color.setN(3, timeUnit.getFraction());
				substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
			}
		}
	}

	@Override
	public boolean isDead() {
		return false;
	}

	protected void dissolve() {
		color.setN(3, 0);
		substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
	}

	protected void reappear(int duration) {
		timeUnit.setDuration(duration);
		timeUnit.reset();
		reappearing = true;
	}

}
