/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHemeral;


public class Unicellular {

	private EPHemeral core;
	private GameSurface gameSurface;

	public Unicellular() {
		core = new EPHemeral(480, 320, "Unicellular");
		core.setResizable(true);
		core.setDebug(500, 5000, 40, -1);
		core.setSurface(gameSurface = new GameSurface(this));
	}

	public int getWidth() {
		return core.getWidth();
	}

	public int getHeight() {
		return core.getHeight();
	}

	public boolean wasResized() {
		return core.wasResized();
	}

	public static void main(String[] args) {
		new Unicellular();
	}

}
