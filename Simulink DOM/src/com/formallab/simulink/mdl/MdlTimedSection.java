package com.formallab.simulink.mdl;

import com.formallab.simulink.mdl.node.MdlNode;

public abstract class MdlTimedSection extends MdlNamedSection {

	private String sampleTime;

	public MdlTimedSection(String nodeName, String name) {
		super(nodeName, name);
	}

	public MdlTimedSection(MdlNode parent, String nodeName, String name) {
		super(parent, nodeName, name);
	}

	public String getSampleTime() {
		return sampleTime;
	}

	public void setSampleTime(String sampleTime) {
		this.sampleTime = sampleTime;
	}

}