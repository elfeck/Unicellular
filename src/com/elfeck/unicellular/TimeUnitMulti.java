/*
 * Copyright 2013, Sebastian Kreisel. All rights reserved.
 * If you intend to use, modify or redistribute this file contact kreisel.sebastian@gmail.com
 */

package com.elfeck.unicellular;

import java.util.HashMap;
import java.util.Map;


public class TimeUnitMulti {

	private Map<String, TimeUnitSingle> timeUnits;

	public TimeUnitMulti() {
		timeUnits = new HashMap<String, TimeUnitSingle>();
	}

	public void addEntry(String name, int duration) {
		timeUnits.put(name, new TimeUnitSingle(duration));
	}

	public void removeEntry(String name) {
		timeUnits.remove(name);
	}

	public void enterDelta(long delta) {
		for (String s : timeUnits.keySet()) {
			timeUnits.get(s).enterDelta(delta);
		}
	}

	public boolean passedDuration(String name) {
		return timeUnits.get(name).passedDuration();
	}

	public void setDuration(String name, int duration) {
		timeUnits.get(name).setDuration(duration);
	}

	public void reset(String name) {
		timeUnits.get(name).reset();
	}

	public int getTimePassed(String name) {
		return timeUnits.get(name).getTimePassed();
	}

	public int getDuration(String name) {
		return timeUnits.get(name).getDuration();
	}

	public float getFraction(String name) {
		return timeUnits.get(name).getFraction();
	}
}
