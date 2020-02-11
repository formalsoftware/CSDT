package com.formallab.simulink.stateflow;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


public class State extends ChartSection {

	private String name;

	private List<Transition> inTransitions;

	private List<Transition> outTransitions;

	public State() {
		super("state");
		inTransitions = new LinkedList<Transition>();
		outTransitions = new LinkedList<Transition>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void addInTransition(Transition transition) {
		inTransitions.add(transition);
	}

	public void addOutTransition(Transition transition) {
		outTransitions.add(transition);
	}

	public List<Transition> listOutTransitions() {
		return Collections.unmodifiableList(outTransitions);
	}

}