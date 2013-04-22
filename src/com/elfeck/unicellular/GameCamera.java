/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformMat4f;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec2f;
import com.elfeck.ephemeral.math.geom.EPHRect2i;
import com.elfeck.unicellular.feature.MovementFeature;


public class GameCamera implements EPHEntity {

	private float scale;
	private EPHRect2i panelRect;
	private EPHUniformMat4f vpMatrix;
	private EPHUniformVec2f position;
	private MovementFeature scrollJob;

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
		scrollJob = null;
	}

	@Override
	public void doLogic(long delta) {
		if (scrollJob != null) {
			position.addVec2f(scrollJob.move(delta, position));
			if (scrollJob.isFinished()) scrollJob = null;
		}
		// 2.0f to the scale is necessary b/c the vpMatrix translates window -> [0; 1] and not window -> [-1; -1]
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

	public void requestScroll(MovementFeature scrollJob) {
		this.scrollJob = scrollJob;
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
