/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.EPHSurface;
import com.elfeck.ephemeral.glContext.EPHVao;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.math.EPHRect2i;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.unicellular.environment.Environment;


public class GameSurface extends EPHSurface {

	private EPHRect2i panelRect, limitRect;
	private GameCamera camera;
	private GameSurfaceLights surfaceLights;
	private Unicellular main;

	public GameSurface(Unicellular main) {
		this.main = main;
		panelRect = new EPHRect2i();
		limitRect = new EPHRect2i(-512, -512, 1024, 1024);
		surfaceLights = new GameSurfaceLights();
		updateBounds();
		addEntity(camera = new GameCamera(panelRect));
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
		panelRect.setX(frame);
		panelRect.setY(frame);
		panelRect.setWidth(width);
		panelRect.setHeight(height);
	}

	private void handleInput() {
		if (panelRect.withinBounds(main.getInput().getMx(), main.getInput().getMy())) {
			if (main.getInput().isMleftReleased()) {
				EPHVec2f pos = toModelSpace(main.getInput().getMx(), main.getInput().getMy()).addVec2f(camera.getCameraPosition());
				camera.requestScroll(new GameScrollJob(pos, 2f));
			}
		}
	}

	private EPHVec2f toModelSpace(int x, int y) {
		return new EPHVec2f((x - panelRect.getX() - panelRect.getWidth() / 2.0f) * (1 / camera.getScale()), (y - panelRect.getY() - panelRect.getHeight() / 2.0f)
				* (1 / camera.getScale()));
	}

	public EPHRect2i getWindowSpacePanelBounds() {
		return panelRect;
	}

	public EPHRect2i getLimitBounds() {
		return limitRect;
	}

	public void setViewport(EPHVao vao) {
		vao.setViewportRect(panelRect.asIntArray());
	}

	public void registerCameraAsUniform(EPHVaoEntry entry) {
		entry.registerUniformEntry("camera_offset", camera.getCameraPosition());
	}

	public void registerMvpMatrixAsUniform(EPHVaoEntry entry) {
		entry.registerUniformEntry("mvp_matrix", camera.getVpMatrix());
	}

	public void registerSurfaceLightsAsUniform(EPHVaoEntry entry) {
		surfaceLights.register(entry);
	}

	public void addLightSource(GameSurfaceLight light) {
		surfaceLights.addLightSource(light);
	}

}
