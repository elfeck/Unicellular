/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.environment.substance.O2Substance;


public class Environment implements EPHEntity {

	public Environment(GameSurface surface) {
		surface.addEntity(new Background(surface));
		surface.addEntity(new O2Substance(surface));
	}

	@Override
	public void doLogic(long delta) {

	}

	@Override
	public boolean isDead() {
		return false;
	}

}
