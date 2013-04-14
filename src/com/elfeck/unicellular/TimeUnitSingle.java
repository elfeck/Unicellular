/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

public class TimeUnitSingle {

	private int timePassed, duration;

	public TimeUnitSingle(int duration) {
		this.duration = duration;
	}

	public void enterDelta(long delta) {
		if (timePassed >= duration) timePassed = 0;
		timePassed += delta / 1e6;
	}

	public boolean passedDuration() {
		return timePassed >= duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void reset() {
		timePassed = 0;
	}

	public int getTimePassed() {
		return timePassed;
	}

	public int getDuration() {
		return duration;
	}

	public float getFraction() {
		return Math.min(timePassed / (float) duration, 1);
	}

}
