/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHemeral;
import com.elfeck.ephemeral.glContext.EPHInput;


public class Unicellular {

	private EPHemeral core;

	public Unicellular() {
		core = new EPHemeral(480, 320, "Unicellular");
		core.setResizable(true);
		core.setDebug(2000, 5000, 40, -1);
		core.setSurface(new GameSurface(this));
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

	public EPHInput getInput() {
		return core.getInput();
	}

	public static void main(String[] args) {
		new Unicellular();
	}

}
