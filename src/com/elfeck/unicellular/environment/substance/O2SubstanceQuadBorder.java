/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.unicellular.TimeUnitSingle;


public class O2SubstanceQuadBorder extends O2SubstanceQuad {

	private boolean reappearing;
	private EPHVec2f gridPosition;
	private TimeUnitSingle timeUnit;

	public O2SubstanceQuadBorder(float x, float y, float size, O2Substance substance) {
		super(x, y, size, substance);
		reappearing = false;
		gridPosition = new EPHVec2f(-1, -1);
		timeUnit = new TimeUnitSingle(1000);
	}

	@Override
	public void doLogic(long delta) {
		if (reappearing) {
			timeUnit.enterDelta(delta);
			if (timeUnit.passedDuration()) {
				reappearing = false;
			}
			color.setN(3, timeUnit.getFraction());
			substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
		}
	}

	protected void disappear() {
		color.setN(3, 0);
		substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
	}

	protected void reappear(int duration) {
		timeUnit.setDuration(duration);
		timeUnit.reset();
		reappearing = true;
	}

	protected EPHVec2f getGridPosition() {
		return gridPosition;
	}

	protected void setGridPosition(int x, int y) {
		gridPosition.setXY(x, y);
	}

}
