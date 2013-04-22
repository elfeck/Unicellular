/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.unicellular.Util;
import com.elfeck.unicellular.environment.substance.subsfeature.MovementO2Circular;
import com.elfeck.unicellular.feature.MovementFeature;
import com.elfeck.unicellular.feature.MovementLine;


public class O2SubstanceQuadPeriph extends O2SubstanceQuad {

	private enum SpawnPosition {
		NORTH, EAST, SOUTH, WEST;
	}

	private float distanceToSource, speed;
	private SpawnPosition spawnPosition;
	private MovementFeature movementFeature;

	public O2SubstanceQuadPeriph(float x, float y, float size, O2Substance substance) {
		super(x, y, size, substance);
		speed = Util.randomFloatInInterval(0.005f, 0.02f);
	}

	@Override
	public void doLogic(long delta) {
		position.addVec2f(movementFeature.move(delta, position));
		if (movementFeature.isFinished()) initCircularMovement();
		updateDataSet();
	}

	private void initCircularMovement() {
		float w = substance.getSourceQuad().getSize() + 2 * width + 2 * distanceToSource;
		float h = substance.getSourceQuad().getSize() + 2 * height + 2 * distanceToSource;
		EPHVec2f pos = new EPHVec2f();
		EPHVec2f dir = new EPHVec2f();
		switch (spawnPosition) {
			case NORTH:
				pos.addVec2f(substance.getSourceQuad().getPosition().getX() - distanceToSource, position.getY() - h);
				dir.addVec2f(0, 1);
				break;
			case EAST:
				pos.addVec2f(position.getX() - w, substance.getSourceQuad().getPosition().getY() - distanceToSource);
				dir.addVec2f(1, 0);
				break;
			case SOUTH:
				pos.addVec2f(substance.getSourceQuad().getPosition().getX() - distanceToSource, position.getY());
				dir.addVec2f(0, -1);
				break;
			case WEST:
				pos.addVec2f(position.getX(), substance.getSourceQuad().getPosition().getY() - distanceToSource);
				dir.addVec2f(-1, 0);
				break;
		}
		MovementO2Circular moveRect = new MovementO2Circular(speed);
		moveRect.addPoint(pos);
		moveRect.addPoint(pos.copy().addVec2f(0, h));
		moveRect.addPoint(pos.copy().addVec2f(w, h));
		moveRect.addPoint(pos.copy().addVec2f(w, 0));
		moveRect.initLine(getCenterPosition(), dir);
		movementFeature = moveRect;
	}

	protected void setDircetion(int x, int y, int density) {
		distanceToSource = width * Util.randomIntInInterval(5, 20);
		EPHVec2f dest = new EPHVec2f(distanceToSource, distanceToSource);
		int xScal = x == 0 ? -1 : x == density ? 1 : 0;
		int yScal = y == 0 ? -1 : y == density ? 1 : 0;
		if ((xScal == -1 && yScal == -1) || (xScal == 1 && yScal == 1)) yScal = 0;
		if ((xScal == -1 && yScal == 1) || (xScal == 1 && yScal == -1)) xScal = 0;
		spawnPosition = yScal == 1 ? SpawnPosition.NORTH : yScal == -1 ? SpawnPosition.SOUTH : xScal == 1 ? SpawnPosition.EAST : SpawnPosition.WEST;
		movementFeature = new MovementLine(dest.mulVec2f(xScal, yScal).addVec2f(position), speed);
	}

}
