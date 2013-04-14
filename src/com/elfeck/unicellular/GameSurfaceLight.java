/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.glContext.uniform.EPHUniformStruct;
import com.elfeck.ephemeral.glContext.uniform.EPHUniformVec4f;


public class GameSurfaceLight {

	private EPHUniformVec4f position, function;
	private EPHUniformStruct struct;

	public GameSurfaceLight() {
		position = null;
		function = null;
	}

	public GameSurfaceLight(EPHUniformVec4f position, EPHUniformVec4f function) {
		this.position = position;
		this.function = function;
	}

	public void setPositionUniform(EPHUniformVec4f position) {
		this.position = position;
	}

	public void setFunctionUniform(EPHUniformVec4f function) {
		this.function = function;
	}

	public EPHUniformVec4f getPositionUniform() {
		return position;
	}

	public EPHUniformVec4f getFunctionUniform() {
		return function;
	}

	protected EPHUniformStruct getStruct() {
		return struct;
	}

	protected void setStruct(EPHUniformStruct struct) {
		this.struct = struct;

	}

}
