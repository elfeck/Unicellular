/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.drawable.EPHLightSource;
import com.elfeck.ephemeral.glContext.EPHVaoEntry;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformStruct;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformStructArray;


public class GameSurfaceLights implements EPHLightSource {

	private EPHUniformStructArray uniformLights;

	public GameSurfaceLights() {
		uniformLights = new EPHUniformStructArray();
	}

	@Override
	public void register(EPHVaoEntry entry) {
		entry.registerUniformEntry("global_lights", uniformLights);
	}

	public void addLightSource(EPHUniformStruct struct) {
		uniformLights.addStruct(struct);
	}

	public void removeLightSource(EPHUniformStruct struct) {
		uniformLights.removeStruct(struct);
	}

}
