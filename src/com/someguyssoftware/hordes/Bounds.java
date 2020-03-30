/**
 * 
 */
package com.someguyssoftware.hordes;

/**
 * @author Mark Gottschling on Oct 7, 2014
 *
 */
public class Bounds {
	private float start;
	private float end;
	
	public Bounds() {}
	
	public Bounds(float start, float end) {
		setStart(start);
		setEnd(end);
	}

	public float getStart() {
		return start;
	}

	public void setStart(float start) {
		this.start = start;
	}

	public float getEnd() {
		return end;
	}

	public void setEnd(float end) {
		this.end = end;
	}

	@Override
	public String toString() {
		return "Bounds [start=" + start + ", end=" + end + "]";
	}
}
