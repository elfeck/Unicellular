/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.unicellular.TimeUnitSingle;


public class O2SubstanceQuadBorder extends O2SubstanceQuad {

	private enum State {
		NORMAL,
		VANISHED,
		REAPPEARING;
	}

	private State state;
	private EPHVec2f gridPosition;
	private TimeUnitSingle timeUnit;

	public O2SubstanceQuadBorder(float x, float y, float size, O2Substance substance) {
		super(x, y, size, substance);
		gridPosition = new EPHVec2f(-1, -1);
		timeUnit = new TimeUnitSingle(1000);
		state = State.NORMAL;
	}

	@Override
	public void doLogic(long delta) {
		timeUnit.enterDelta(delta);
		switch (state) {
			case NORMAL:
				break;
			case VANISHED:
				if (timeUnit.passedDuration()) reappear();
				break;
			case REAPPEARING:
				if (timeUnit.passedDuration()) state = State.NORMAL;
				color.setN(3, timeUnit.getFraction());
				substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
				break;
		}
	}

	protected void vanish() {
		timeUnit.setDuration(1000);
		timeUnit.reset();
		color.setN(3, 0);
		substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
		state = State.VANISHED;
	}

	private void reappear() {
		timeUnit.setDuration(1000);
		timeUnit.reset();
		state = State.REAPPEARING;
	}

	protected EPHVec2f getGridPosition() {
		return gridPosition;
	}

	protected void setGridPosition(int x, int y) {
		gridPosition.setXY(x, y);
	}

}
