package com.formallab.simulink.stateflow;

public class MachineSection extends StateflowSection {

	public MachineSection(String nodeName) {
		super(nodeName);
	}

	public MachineSection(Machine parent, String nodeName) {
		super(parent, nodeName);
	}

	@Override
	public Machine getParent() {
		return (Machine) super.getParent();
	}

}