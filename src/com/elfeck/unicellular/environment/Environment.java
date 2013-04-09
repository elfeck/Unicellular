/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.unicellular.GameSurface;


public class Environment implements EPHEntity {

	private Background background;
	private Substance substance;

	public Environment(GameSurface surface) {
		background = new Background(surface);
		substance = new Substance(surface);
	}

	@Override
	public void doLogic(long delta) {
		background.delegateLogic(delta);
		substance.delegateLogic(delta);
	}

	@Override
	public boolean isDead() {
		return false;
	}

}
