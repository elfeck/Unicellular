/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.feature;

import com.elfeck.ephemeral.math.EPHResettableVec2f;
import com.elfeck.ephemeral.math.EPHVec2f;


public class MovementLine implements MovementFeature {

	private boolean finished;
	private float speed;
	private EPHResettableVec2f destination;

	public MovementLine(EPHVec2f destination, float speed) {
		this.speed = speed;
		this.destination = new EPHResettableVec2f(destination);
	}

	@Override
	public EPHVec2f move(long delta, EPHVec2f currentPosition) {
		destination.reset();
		float length = Math.min(currentPosition.distance(destination), (float) (speed * delta / 1e6));
		destination.subVec2f(currentPosition);
		finished = destination.length() == 0;
		if (finished) return destination.setXY(0, 0);
		return destination.toLength(length);
	}

	@Override
	public boolean isFinished() {
		return finished;
	}

	public void setDestination(EPHVec2f destination) {
		this.destination = new EPHResettableVec2f(destination);
	}

}
