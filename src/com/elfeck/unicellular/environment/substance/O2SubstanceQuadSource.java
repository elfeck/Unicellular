/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.GameSurfaceLight;
import com.elfeck.unicellular.TimeUnitMulti;
import com.elfeck.unicellular.Util;


public class O2SubstanceQuadSource extends O2SubstanceQuad {

	private int size, density;
	private GameSurface surface;
	private EPHVaoEntry entry;
	private List<O2SubstanceQuadBorder> borderQuads;
	private List<O2SubstanceQuad> substanceQuads;
	private TimeUnitMulti timeUnit;
	private GameSurfaceLight light;
	private EPHUniformVec4f lightPosition, lightFunction;

	public O2SubstanceQuadSource(float x, float y, int size, int density, GameSurface surface, O2Substance substance, EPHVaoEntry entry) {
		super(x, y, size, substance);
		this.size = size;
		this.density = density;
		this.surface = surface;
		this.entry = entry;
		borderQuads = new ArrayList<O2SubstanceQuadBorder>();
		substanceQuads = new ArrayList<O2SubstanceQuad>();
		timeUnit = new TimeUnitMulti();
		light = new GameSurfaceLight(lightPosition = new EPHUniformVec4f(x + size / 2.0f, y + size / 2.0f, 0, 11), lightFunction = new EPHUniformVec4f(40, 0.55f, 0, 0));
		initTimeUnit();
		initQuads(size, density);
	}

	@Override
	public void doLogic(long delta) {

	}

	@Override
	public boolean isDead() {
		return false;
	}

	private void initTimeUnit() {

	}

	private void initQuads(int size, int density) {
		for (int y = 0; y < density + 1; y++) {
			for (int x = 0; x < density + 1; x++) {
				if (y == 0 || y == density) createBorderQuad(x, y);
				if ((x == 0 || x == density) && y != 0 && y != density) createBorderQuad(x, y);
			}
		}
		surface.addLightSource(light);
	}

	private void createBorderQuad(int x, int y) {
		float quadSize = size / (density - 1.0f);
		O2SubstanceQuadBorder quad = new O2SubstanceQuadBorder(position.getX() + quadSize * (x - 1), position.getY() + quadSize * (y - 1), quadSize, substance);
		quad.setGridPosition(x, y);
		surface.addEntity(quad);
		borderQuads.add(quad);
	}

	private void createPeriphQuad(int x, int y) {
		float quadSize = size / (density - 1.0f);
		O2SubstanceQuad quad = new O2SubstanceQuadPeriph(position.getX() + quadSize * (x - 1), position.getY() + quadSize * (y - 1), quadSize, substance);
		surface.addEntity(quad);
		substanceQuads.add(quad);
	}

	private O2SubstanceQuadBorder getBorderQuadAtPposition(int x, int y) {
		for (O2SubstanceQuadBorder quad : borderQuads) {
			if (quad.getGridPosition().getX() == x && quad.getGridPosition().getY() == y) return quad;
		}
		return null;
	}

	private void spwan() {
		int x = Util.randomIntInInterval(0, density + 2);
		int y = Util.randomIntInInterval(0, density + 2);
		O2SubstanceQuadBorder spwanQuad = getBorderQuadAtPposition(x, y);

	}
}
