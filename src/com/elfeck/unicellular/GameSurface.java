/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.unicellular.environment.Environment;


public class GameSurface extends EPHSurface {

	private int[] panelBounds; // window space
	private int[] limitBounds; // ingame space
	private GameCamera camera;
	private Unicellular main;

	public GameSurface(Unicellular main) {
		this.main = main;
		panelBounds = new int[4];
		limitBounds = new int[] { -2500, -2500, 5000, 5000 };
		updateBounds();
		addEntity(camera = new GameCamera(panelBounds));
		addEntity(new Environment(this));
	}

	@Override
	public void execLogic(long delta) {
		updateBounds();
		handleInput();
		super.execLogic(delta);
	}

	private void updateBounds() {
		int frame = (int) (main.getHeight() / 50.0);
		int width = (int) Math.round(main.getWidth() * (2.0 / 3)) - frame * 2;
		int height = main.getHeight() - frame * 2;
		panelBounds[0] = frame;
		panelBounds[1] = frame;
		panelBounds[2] = width;
		panelBounds[3] = height;
	}

	private void handleInput() {
		if (withinBounds(main.getInput().getMx(), main.getInput().getMy())) {
			if (main.getInput().isMleftReleased()) {
				EPHVec2f pos = toModelSpace(main.getInput().getMx(), main.getInput().getMy()).addVec2f(camera.getCameraPosition());
				camera.requestScroll(new GameScrollJob(pos, 2f));
			}
		}
	}

	private boolean withinBounds(int mx, int my) {
		return mx >= panelBounds[0] && mx < panelBounds[0] + panelBounds[2] && my >= panelBounds[1] && my < panelBounds[1] + panelBounds[3];
	}

	private EPHVec2f toModelSpace(int x, int y) {
		return new EPHVec2f((x - panelBounds[0] - panelBounds[2] / 2.0f) * (1 / camera.getScale()),
				(y - panelBounds[1] - panelBounds[3] / 2.0f) * (1 / camera.getScale()));
	}

	public GameCamera getCamera() {
		return camera;
	}

	public int[] getWindowSpacePanelBounds() {
		return panelBounds;
	}

	public int[] getLimitBounds() {
		return limitBounds;
	}

}
