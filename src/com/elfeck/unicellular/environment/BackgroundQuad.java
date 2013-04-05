/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import com.elfeck.ephemeral.math.EPHVec4f;
import com.elfeck.unicellular.template.ColoredQuad;


public class BackgroundQuad extends ColoredQuad {

	public BackgroundQuad(float x, float y, int width, int height, float layer, EPHVec4f color) {
		super(x, y, width, height, layer, color);
	}

}
