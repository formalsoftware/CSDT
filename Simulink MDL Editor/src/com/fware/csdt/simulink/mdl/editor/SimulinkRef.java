package com.fware.csdt.simulink.mdl.editor;

public class SimulinkRef {

	private String text;

	private int line;
	private int pos;

	private SimulinkRef parent;

	public SimulinkRef(String text, int line, int pos, SimulinkRef parent) {
		this.text = text;
		this.line = line;
		this.pos = pos;
		this.parent = parent;
	}

	public String getText() {
		return this.text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public int getPos() {
		return pos;
	}

	public void setPos(int pos) {
		this.pos = pos;
	}

	public SimulinkRef getParent() {
		return parent;
	}

	public void setParent(SimulinkRef parent) {
		this.parent = parent;
	}
	
	
	
}
