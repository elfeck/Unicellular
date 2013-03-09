/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.math.EPHVec2f;


public class GameCamera implements EPHEntity {

	private int[] panelBounds;
	private EPHVec2f position, scrollOffset;
	private GameScrollJob scrollJob;

	public GameCamera(int[] panelBounds) {
		this.panelBounds = panelBounds;
		position = new EPHVec2f(panelBounds[0], panelBounds[1]);
		scrollOffset = new EPHVec2f(0, 0);
		scrollJob = null;
	}

	@Override
	public void doLogic(long delta) {
		if (scrollJob != null) {
			scrollOffset = scrollJob.scroll(delta);
			System.out.println(getBounds()[0] + " " + getBounds()[1]);
			if (scrollJob.isFinished()) scrollJob = null;
		}
	}

	@Override
	public boolean isDead() {
		return false;
	}

	public float[] getBounds() {
		return new float[] { position.getX() + scrollOffset.getX(), position.getY() + scrollOffset.getX(), panelBounds[2], panelBounds[3] };
	}

	public void requestScroll(GameScrollJob scrollJob) {
		this.scrollJob = scrollJob;
		scrollJob.setSource(position.getX(), position.getY());
	}

}
