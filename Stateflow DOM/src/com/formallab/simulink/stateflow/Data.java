package com.formallab.simulink.stateflow;


public class Data extends MachineSection {

	private DataScope scope;

	public Data(DataScope scope) {
		super("data");
		this.scope = scope;
	}

	public DataScope getScope() {
		return scope;
	}

	public void setScope(DataScope scope) {
		this.scope = scope;
	}

}