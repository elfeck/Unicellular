/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular.environment;

import com.elfeck.ephemeral.drawable.EPHDrawableModel;
import com.elfeck.unicellular.GameSurface;


public class Environment {

	private EPHDrawableModel model;
	private Background background;

	public Environment(GameSurface surface) {
		model = new EPHDrawableModel();
		model.addAttribute(4, "vertex_position");
		model.addAttribute(4, "vertex_color");
		model.create();
		model.setViewPort(surface.getWindowSpacePanelBounds());
		model.addToSurface(surface);
		background = new Background(model, surface);
		new Background(model, surface);
	}

}
