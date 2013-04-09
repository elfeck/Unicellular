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
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;


public class GameSurfaceLights implements EPHLightSource {

	private EPHUniformVec1f activeLights;
	private EPHUniformStructArray uniformLights;

	public GameSurfaceLights() {
		activeLights = new EPHUniformVec1f(0);
		uniformLights = new EPHUniformStructArray();
	}

	@Override
	public void register(EPHVaoEntry entry) {
		entry.registerUniformEntry("lights", uniformLights);
		entry.registerUniformEntry("active_lights", activeLights);
	}

	public EPHUniformStruct addLightSource(EPHUniformVec4f position, EPHUniformVec4f function) {
		Map<String, EPHUniformContent> members = new HashMap<String, EPHUniformContent>();
		members.put("position", position);
		members.put("function", function);
		EPHUniformStruct struct = new EPHUniformStruct(members);
		uniformLights.addStruct(struct);
		activeLights.addToN(0, 1);
		return struct;
	}

	public void removeLightSource(EPHUniformStruct struct) {
		activeLights.addToN(0, -1);
		uniformLights.removeStruct(struct);
	}

}