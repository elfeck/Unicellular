/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformMat4f;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec2f;
import com.elfeck.ephemeral.math.EPHRect2i;
import com.elfeck.ephemeral.math.EPHVec2f;


public class GameCamera implements EPHEntity {

	private EPHRect2i panelRect; // ingame space
	private float scale;
	private EPHUniformMat4f vpMatrix;
	private EPHUniformVec2f position; // center
	private EPHVec2f scrollOffset;
	private GameScrollJob scrollJob;

	public GameCamera(EPHRect2i panelRect) {
		this.panelRect = panelRect;
		scale = 1f;
		vpMatrix = new EPHUniformMat4f(new float[][] {
														{ scale * 2.0f / panelRect.getWidth(), 0, 0, 0 },
														{ 0, scale * 2.0f / panelRect.getHeight(), 0, 0 },
														{ 0, 0, 1, 0 },
														{ 0, 0, 0, 1 }
		});
		position = new EPHUniformVec2f(0, 0);
		scrollOffset = new EPHVec2f(0, 0);
		scrollJob = null;
	}

	@Override
	public void doLogic(long delta) {
		if (scrollJob != null) {
			position.subVec2f(scrollOffset);
			position.addVec2f(scrollOffset = scrollJob.scroll(delta));
			if (scrollJob.isFinished()) {
				scrollJob = null;
				scrollOffset.setX(0f);
				scrollOffset.setY(0f);
			}
		}
		// 2.0f to the scale is necessary b/c the vpMatrix translates window ->
		// [0; 1] and not window -> [-1; -1] aka half the width and height
		vpMatrix.copyToCL(0, 0, scale * 2.0f / panelRect.getWidth());
		vpMatrix.copyToCL(1, 1, scale * 2.0f / panelRect.getHeight());
	}

	@Override
	public boolean isDead() {
		return false;
	}

	public float[] getWindowSpaceBounds() {
		return new float[] { position.getX() - panelRect.getWidth() / 2.0f, position.getY() + panelRect.getHeight() / 2.0f, panelRect.getWidth(), panelRect.getHeight() };
	}

	public float[] getModelSpaceBounds() {
		return new float[] { position.getX() - panelRect.getWidth() / 2.0f, position.getY() - panelRect.getHeight() / 2.0f, panelRect.getWidth(), panelRect.getHeight() };
	}

	public void requestScroll(GameScrollJob scrollJob) {
		this.scrollJob = scrollJob;
		scrollOffset.setX(0);
		scrollOffset.setY(0);
		scrollJob.setSource(position.getX(), position.getY());
	}

	public EPHUniformMat4f getVpMatrix() {
		return vpMatrix;
	}

	public EPHUniformVec2f getCameraPosition() {
		return position;
	}

	public float getScale() {
		return scale;
	}

}
