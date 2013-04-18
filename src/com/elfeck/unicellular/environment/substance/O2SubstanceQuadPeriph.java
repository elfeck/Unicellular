/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.math.EPHVec2f;


public class O2SubstanceQuadPeriph extends O2SubstanceQuad {

	private enum State {
		ROTARY,
		POSITIONING;
	}

	private float speed;
	private EPHVec2f movDirection, spawnPosition;
	private State state;

	public O2SubstanceQuadPeriph(float x, float y, float size, O2Substance substance) {
		super(x, y, size, substance);
		movDirection = new EPHVec2f(0, 0);
		spawnPosition = position.copy();
		state = State.POSITIONING;
	}

	@Override
	public void doLogic(long delta) {
		float distScalar = (float) (speed * (delta / 1e6));
		switch (state) {
			case POSITIONING:
				position.addVec2f(movDirection.mulScalar(distScalar));
				movDirection.mulScalar(1 / distScalar);
				updateDataSet();
				if (position.distance(spawnPosition) > width * 10) state = State.ROTARY;
				break;
			case ROTARY:
				break;
		}
	}

	protected void setGridPosition(int x, int y, int density) {
		float xDir = x == 0 ? -1 : x == density ? 1 : 0;
		float yDir = y == 0 ? -1 : y == density ? 1 : 0;
		if (xDir == 1 && yDir == 1) yDir = 0;
		if (xDir == 1 && yDir == -1) xDir = 0;
		if (xDir == -1 && yDir == -1) yDir = 0;
		if (xDir == -1 && yDir == 1) xDir = 0;
		movDirection = new EPHVec2f(xDir, yDir);
		speed = 0.01f;
	}

}
