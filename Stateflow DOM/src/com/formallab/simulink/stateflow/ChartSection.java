package com.formallab.simulink.stateflow;


public class ChartSection extends StateflowSection {

	private String labelString;

	public ChartSection(String nodeName) {
		super(nodeName);
	}

	public ChartSection(Chart parent, String nodeName) {
		super(parent, nodeName);
	}

	@Override
	public Chart getParent() {
		return (Chart) super.getParent();
	}

	public String getLabelString() {
		return labelString;
	}

	public void setLabelString(String labelString) {
		this.labelString = labelString;
	}

}