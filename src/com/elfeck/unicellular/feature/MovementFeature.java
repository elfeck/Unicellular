/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.feature;

import com.elfeck.ephemeral.math.EPHVec2f;


public interface MovementFeature {

	public EPHVec2f move(long delta, EPHVec2f currentPosition);
	public boolean isFinished();

}
