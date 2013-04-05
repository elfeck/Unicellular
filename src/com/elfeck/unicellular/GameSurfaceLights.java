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
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;


public class GameSurfaceLights implements EPHLightSource {

	private EPHUniformStructArray uniformLights;

	public GameSurfaceLights() {
		uniformLights = new EPHUniformStructArray();
	}

	@Override
	public void register(EPHVaoEntry entry) {
		entry.registerUniformEntry("global_lights", uniformLights);
	}

	public EPHUniformStruct addLightSource(EPHUniformVec4f position, EPHUniformVec4f function) {
		Map<String, EPHUniformContent> members = new HashMap<String, EPHUniformContent>();
		members.put("position", position);
		members.put("function", function);
		EPHUniformStruct struct = new EPHUniformStruct(members);
		uniformLights.addStruct(struct);
		return struct;
	}

	public void removeLightSource(EPHUniformStruct struct) {
		uniformLights.removeStruct(struct);
	}

}
