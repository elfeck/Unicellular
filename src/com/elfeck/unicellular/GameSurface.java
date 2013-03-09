/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.unicellular.environment.Environment;


public class GameSurface extends EPHSurface {

	private int[] panelBounds;
	private GameCamera camera;
	private Unicellular main;

	public GameSurface(Unicellular main) {
		this.main = main;
		panelBounds = new int[4];
		updateBounds();
		addEntity(camera = new GameCamera(panelBounds));
		new Environment(this, panelBounds);
	}

	int timePassed = 0;
	boolean once = true;
	@Override
	public void execLogic(long delta) {
		super.execLogic(delta);
		updateBounds();
		if (timePassed > 1000 && once) {
			timePassed = 0;
			camera.requestScroll(new GameScrollJob(14, -14, 1000));
			once = false;
			timePassed += delta / 1e6;
		} else {
			timePassed += delta / 1e6;
		}
	}

	private void updateBounds() {
		int frame = (int) (main.getHeight() / 50.0);
		panelBounds[0] = frame;
		panelBounds[1] = frame;
		panelBounds[2] = (int) Math.round(main.getWidth() * (2.0 / 3)) - frame * 2;
		panelBounds[3] = main.getHeight() - frame * 2;
	}

	public void requestScroll(GameScrollJob scrollJob) {
		camera.requestScroll(scrollJob);
	}

}
