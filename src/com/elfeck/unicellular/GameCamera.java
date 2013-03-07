/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHEntity;


public class GameCamera implements EPHEntity {

	private float x, y;
	private int[] panelBounds;
	private GameScrollJob scrollJob;
	private GameSurface gameSurface;

	public GameCamera(GameSurface gameSurface, int[] panelBounds) {
		this.gameSurface = gameSurface;
		this.panelBounds = panelBounds;
		x = panelBounds[0];
		y = panelBounds[1];
		scrollJob = null;
	}

	@Override
	public void doLogic(long delta) {
		if (scrollJob != null) {
			scrollJob.scroll(delta);
			if (scrollJob.isFinished()) scrollJob = null;
		}
	}

	@Override
	public boolean isDead() {
		return false;
	}

	public float[] getBounds() {
		return new float[] { x, y, panelBounds[2], panelBounds[3] };
	}

	public void requestScroll(GameScrollJob scrollJob) {
		this.scrollJob = scrollJob;
		scrollJob.setSource(x, y);
	}

}
