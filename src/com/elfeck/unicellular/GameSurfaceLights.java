/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import java.util.HashMap;
import java.util.Map;

import com.elfeck.ephemeral.drawable.EPHLightSource;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformContent;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformStruct;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformStructArray;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec1f;


public class GameSurfaceLights implements EPHLightSource {

	private EPHUniformVec1f activeLights;
	private EPHUniformStructArray uniformLights;

	protected GameSurfaceLights() {
		activeLights = new EPHUniformVec1f(0);
		uniformLights = new EPHUniformStructArray();
	}

	@Override
	public void register(EPHVaoEntry entry) {
		entry.registerUniformEntry("lights", uniformLights);
		entry.registerUniformEntry("active_lights", activeLights);
	}

	public void addLightSource(GameSurfaceLight light) {
		Map<String, EPHUniformContent> members = new HashMap<String, EPHUniformContent>();
		members.put("position", light.getPositionUniform());
		members.put("function", light.getFunctionUniform());
		EPHUniformStruct struct = new EPHUniformStruct(members);
		uniformLights.addStruct(struct);
		activeLights.addToN(0, 1);
		light.setStruct(struct);
	}

	public void removeLightSource(GameSurfaceLight light) {
		activeLights.addToN(0, -1);
		uniformLights.removeStruct(light.getStruct());
	}

}