/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance.subsfeature;

import com.elfeck.ephemeral.math.EPHMat2f;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.unicellular.feature.MovementLineCircular;


public class MovementO2Circular extends MovementLineCircular {

	public MovementO2Circular(float speed) {
		super(speed);
	}

	public void initLine(EPHVec2f currentPosition, EPHVec2f dir) {
		EPHVec2f first = points.get(0);
		EPHVec2f second = points.get(0);
		for (EPHVec2f point : points) {
			if (point == first) continue;
			if (point.distance(currentPosition) < first.distance(currentPosition)) first = point;
		}
		if (first == second) second = points.get(1);
		for (EPHVec2f point : points) {
			if (point == first || point == second) continue;
			if (point.distance(currentPosition) < second.distance(currentPosition)) second = point;
		}
		dir.mulMat2f(new EPHMat2f(new float[][] {
													{ 0, 1 },
													{ -1, 0 }
		}));
		clockwise = !first.copy().subVec2f(second).normalize().equals(dir);
		currentIndex = points.indexOf(first);
	}

}
