/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.math.EPHVec2f;


public class O2SubstanceQuadPeriph extends O2SubstanceQuad {

	private float speed;
	private EPHVec2f movDirection;

	public O2SubstanceQuadPeriph(float x, float y, float size, O2Substance substance) {
		super(x, y, size, substance);
		movDirection = new EPHVec2f(0, 0);
	}

	@Override
	public void doLogic(long delta) {
		position.addVec2f(movDirection.mulScalar(speed));
		movDirection.mulScalar(1 / speed);
		substance.getVaoEntry().updateVboData(dataSet, assembleVertexData());
	}

	protected void setGridPosition(int x, int y, int density) {
		float xDir = x == 0 ? -1 : x == density ? 1 : 0;
		float yDir = y == 0 ? -1 : y == density ? 1 : 0;
		movDirection = new EPHVec2f(xDir, yDir);
		speed = 0.01f;
	}

}
