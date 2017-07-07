package com.formallab.simulink.stateflow;


public class TransitionSection extends StateflowSection {

	private State state;

	public TransitionSection(String nodeName) {
		super(null, nodeName);
	}

	public TransitionSection(Transition parent, String nodeName) {
		super(parent, nodeName);
	}

	@Override
	public Transition getParent() {
		return (Transition) super.getParent();
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

}