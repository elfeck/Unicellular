/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment.substance;

import java.util.ArrayList;
import java.util.List;

import com.elfeck.ephemeral.EPHEntity;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.EPHVaoEntryDataSet;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;
import com.elfeck.ephemeral.math.EPHVec2f;
import com.elfeck.unicellular.GameSurface;
import com.elfeck.unicellular.GameSurfaceLight;
import com.elfeck.unicellular.TimeUnitMulti;
import com.elfeck.unicellular.template.ColoredQuad;


public class O2SubstanceSourceQuad implements EPHEntity {

	private int size, density;
	private GameSurface surface;
	private O2Substance substance;
	private O2SubstanceQuad sourceQuad;
	private EPHVaoEntry entry;
	private EPHVaoEntryDataSet sourceDataSet;
	private List<O2SubstanceQuad> borderQuads;
	private TimeUnitMulti timeUnit;
	private GameSurfaceLight light;
	private EPHVec2f position;
	private EPHUniformVec4f lightPosition, lightFunction;

	public O2SubstanceSourceQuad(float x, float y, int size, int density, GameSurface surface, O2Substance substance, EPHVaoEntry entry) {
		this.size = size;
		this.density = density;
		this.surface = surface;
		this.substance = substance;
		this.entry = entry;
		borderQuads = new ArrayList<O2SubstanceQuad>();
		timeUnit = new TimeUnitMulti();
		timeUnit.addEntry("reapp", 5000);
		position = new EPHVec2f(x, y);
		light = new GameSurfaceLight(lightPosition = new EPHUniformVec4f(0, 0, 0, 11), lightFunction = new EPHUniformVec4f(40, 0.55f, 0, 0));
		initQuads(size, density);
	}

	@Override
	public void doLogic(long delta) {
		timeUnit.enterDelta(delta);
		if (timeUnit.passedDuration("reapp")) {
			for (O2SubstanceQuad quad : borderQuads) {
				quad.reappear(3000);
			}
		}
	}

	@Override
	public boolean isDead() {
		return false;
	}

	private void initQuads(int size, int density) {
		sourceQuad = new O2SubstanceQuad(position.getX() - size / 2.0f, position.getY() - size / 2.0f,
				size, size, substance.getLayer(), substance.getBaseColor(), substance);
		for (int y = 0; y < density + 1; y++) {
			for (int x = 0; x < density + 1; x++) {
				if (y == 0 || y == density) createQuadAtPosition(x, y);
				if ((x == 0 || x == density) && y != 0 && y != density) createQuadAtPosition(x, y);
			}
		}
		surface.addLightSource(light);
		sourceDataSet = entry.addData(sourceQuad.assembleVertexData(), ColoredQuad.assembleIndices());
	}

	private void createQuadAtPosition(int x, int y) {
		float quadSize = size / (density - 1.0f);
		O2SubstanceQuad quad = new O2SubstanceQuad(position.getX() - size / 2.0f + quadSize * (x - 1), position.getY() - size / 2.0f + quadSize * (y - 1),
				quadSize, quadSize, substance.getLayer(), substance.getBaseColor(), substance);
		surface.addEntity(quad);
		borderQuads.add(quad);
	}
}
