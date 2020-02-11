package com.formallab.simulink.mdl;

import com.formallab.simulink.mdl.node.MdlNode;
import com.formallab.simulink.mdl.node.MdlSection;

public abstract class MdlNamedSection extends MdlSection {

	protected String name;

	public MdlNamedSection(String nodeName, String name) {
		super(nodeName);
		this.name = name;
	}

	public MdlNamedSection(MdlNode parent, String nodeName, String name) {
		super(parent, nodeName);
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}