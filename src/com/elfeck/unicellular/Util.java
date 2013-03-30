/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

public class Util {

	private Util() {

	}

	public static int randomIntInInterval(int lower, int upper) {
		return (int) randomFloatInInterval(lower, upper);
	}

	public static float randomFloatInInterval(float lower, float upper) {
		return (float) (Math.random() * (Math.abs(upper - lower)) + lower);
	}

	public static <T> T randomElementFromArray(T[] source) {
		return source[(int) (Math.random() * source.length)];
	}

}
