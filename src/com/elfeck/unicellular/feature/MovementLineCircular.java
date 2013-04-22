/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.feature;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.math.EPHVec2f;


public class MovementLineCircular implements MovementFeature {

	protected boolean clockwise;
	protected int currentIndex;
	protected float speed;
	protected List<EPHVec2f> points;
	protected MovementLine line;

	public MovementLineCircular(float speed) {
		this.speed = speed;
		clockwise = false;
		currentIndex = -1;
		line = null;
		points = new ArrayList<EPHVec2f>();
	}

	@Override
	public EPHVec2f move(long delta, EPHVec2f currentPosition) {
		if (line == null) line = new MovementLine(points.get(currentIndex), speed);
		if (line.isFinished()) {
			if (clockwise) line.setDestination(points.get(++currentIndex % points.size()));
			if (!clockwise) line.setDestination(points.get((--currentIndex + points.size()) % points.size()));
			if (Math.abs(currentIndex) == points.size()) currentIndex = 0;
		}
		return line.move(delta, currentPosition);
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	public void setStartIndex(int index) {
		currentIndex = index;
	}

	public void setClockwise(boolean clockwise) {
		this.clockwise = clockwise;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void addPoint(EPHVec2f point) {
		points.add(point);
	}

}
