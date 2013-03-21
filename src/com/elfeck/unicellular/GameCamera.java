/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.math.EPHMat4f;
import com.elfeck.ephemeral.math.EPHVec2f;


public class GameCamera implements EPHEntity {

	private int[] panelBounds; // ingame space
	private float scale;
	private EPHMat4f vpMatrix;
	private EPHVec2f position; // center
	private EPHVec2f scrollOffset;
	private GameScrollJob scrollJob;

	public GameCamera(int[] panelBounds) {
		this.panelBounds = panelBounds;
		scale = 1.0f;
		vpMatrix = new EPHMat4f(new float[][] {
												{ scale / panelBounds[2], 0, 0, 0 },
												{ 0, scale / panelBounds[3], 0, 0 },
												{ 0, 0, 1, 0 },
												{ 0, 0, 0, 1 }
		});
		position = new EPHVec2f(0, 0);
		scrollOffset = new EPHVec2f(0, 0);
		scrollJob = null;
	}

	@Override
	public void doLogic(long delta) {
		if (scrollJob != null) {
			position.subVec2f(scrollOffset);
			position.addVec2f(scrollOffset = scrollJob.scroll(delta));

			if (scrollJob.isFinished()) scrollJob = null;
		}
		vpMatrix.copyToCL(0, 0, scale / panelBounds[2]);
		vpMatrix.copyToCL(1, 1, scale / panelBounds[3]);
	}

	@Override
	public boolean isDead() {
		return false;
	}

	public float[] getWindowSpaceBounds() {
		return new float[] { position.getX() - panelBounds[2] / 2.0f,
							position.getY() + panelBounds[3] / 2.0f,
							panelBounds[2], panelBounds[3]
		};

	}

	public float[] getModelSpaceBounds() {
		return new float[] { position.getX() - panelBounds[2] / 2.0f,
							position.getY() - panelBounds[3] / 2.0f,
							panelBounds[2], panelBounds[3]
		};
	}

	public void requestScroll(GameScrollJob scrollJob) {
		this.scrollJob = scrollJob;
		scrollJob.setSource(position.getX(), position.getY());
	}

	public EPHMat4f getVpMatrix() {
		return vpMatrix;
	}

	public EPHVec2f getCameraOffset() {
		return position;
	}

}
