package com.plagiarism.response;

public class Highlighter {

	private int start1;
	private int end1;
	private int start2;
	private int end2;
	private float similarity;
	private String color;

	public int getStart1() {
		return start1;
	}

	public void setStart1(int start1) {
		this.start1 = start1;
	}

	public int getEnd1() {
		return end1;
	}

	public void setEnd1(int end1) {
		this.end1 = end1;
	}

	public int getStart2() {
		return start2;
	}

	public void setStart2(int start2) {
		this.start2 = start2;
	}

	public int getEnd2() {
		return end2;
	}

	public void setEnd2(int end2) {
		this.end2 = end2;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public Highlighter(int start1, int end1, int start2, int end2, float similarity) {
		super();
		this.start1 = start1;
		this.end1 = end1;
		this.start2 = start2;
		this.end2 = end2;
		this.similarity = similarity;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Highlighter) {
			Highlighter target = (Highlighter) o;
			if (this.start1 == target.start1 && this.end1 == target.getEnd1()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

}
