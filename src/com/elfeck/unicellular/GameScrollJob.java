/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import com.elfeck.ephemeral.math.EPHVec2f;


public class GameScrollJob {

	private boolean finished;
	private int timePassed;
	private float speed, totalTime;
	private EPHVec2f dest, dist;

	public GameScrollJob(EPHVec2f dest, float speed) {
		this.dest = dest;
		this.speed = speed;
		finished = false;
		totalTime = -1;
		dist = null;
		timePassed = 0;
	}

	public GameScrollJob(EPHVec2f dest, int time) {
		this.dest = dest;
		this.totalTime = time;
		finished = false;
		speed = -1;
		dist = null;
		timePassed = 0;
	}

	public GameScrollJob(float dx, float dy, float speed) {
		this.speed = speed;
		dist = new EPHVec2f(dx, dy);
		finished = false;
		totalTime = -1;
		dest = null;
		timePassed = 0;
	}

	public GameScrollJob(float dx, float dy, int time) {
		this.totalTime = time;
		dist = new EPHVec2f(dx, dy);
		finished = false;
		speed = -1;
		dest = null;
		timePassed = 0;
	}

	protected EPHVec2f scroll(long delta) {
		if (!finished) {
			timePassed += delta / 1e6;
			EPHVec2f currentDist = dist.copy().toLength(dist.length() * Math.min(timePassed / totalTime, 1));
			if (timePassed >= totalTime) finished = true;
			return currentDist;
		} else {
			return new EPHVec2f(0, 0);
		}
	}

	protected void setSource(float x, float y) {
		if (dest == null) dest = new EPHVec2f(x, y).addVec2f(dist);
		if (dist == null) dist = new EPHVec2f(x, y).negate().addVec2f(dest);
		if (speed == -1) speed = dist.length() / totalTime;
		if (totalTime == -1) totalTime = dist.length() / speed;
	}

	public boolean isFinished() {
		return finished;
	}

}
