package com.formallab.simulink.stateflow;

import com.formallab.simulink.mdl.node.MdlSection;

public class StateflowSection extends MdlSection {

	private int id;

	public StateflowSection(String nodeName) {
		super(nodeName);
	}

	public StateflowSection(MdlSection parent, String nodeName) {
		super(parent, nodeName);
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}