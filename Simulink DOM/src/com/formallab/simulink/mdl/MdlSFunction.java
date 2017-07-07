package com.formallab.simulink.mdl;

public class MdlSFunction extends MdlBlock {

	private String tag;

	private String functionName;

	public MdlSFunction(String name, String tag, String functionName) {
		super("S-Function", name);
		this.tag = tag;
		this.functionName = functionName;
	}

	public String getTag() {
		return tag;
	}

	public String getFunctionName() {
		return functionName;
	}

}
