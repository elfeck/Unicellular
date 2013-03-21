/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.unicellular.environment.Environment;


public class GameSurface extends EPHSurface {

	private int[] windowSpacePanelBounds; // window space
	private int[] limitBounds; // ingame space
	private GameCamera camera;
	private Unicellular main;

	public GameSurface(Unicellular main) {
		this.main = main;
		windowSpacePanelBounds = new int[4];
		limitBounds = new int[] { -500, -500, 1000, 1000 };
		updateBounds();
		addEntity(camera = new GameCamera(windowSpacePanelBounds));
		new Environment(this);
	}

	@Override
	public void execLogic(long delta) {
		updateBounds();
		super.execLogic(delta);
	}

	private void updateBounds() {
		int frame = (int) (main.getHeight() / 50.0);
		int width = (int) Math.round(main.getWidth() * (2.0 / 3)) - frame * 2;
		int height = main.getHeight() - frame * 2;
		windowSpacePanelBounds[0] = frame;
		windowSpacePanelBounds[1] = frame;
		windowSpacePanelBounds[2] = width;
		windowSpacePanelBounds[3] = height;
	}

	public GameCamera getCamera() {
		return camera;
	}

	public int[] getWindowSpacePanelBounds() {
		return windowSpacePanelBounds;
	}

	public int[] getLimitBounds() {
		return limitBounds;
	}

}
