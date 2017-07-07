package com.formallab.simulink.stateflow;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;


public class Chart extends MachineSection {

	private String name;

	private Map<Integer, State> states;

	private Map<Integer, Transition> transitions;

	private Transition firstTransition;

	private Map<Integer, Data> localdata;

	private Map<Integer, Data> indata;

	private Map<Integer, Data> outdata;

	private Data firstData;

	private Instance instance;

	public Chart() {
		super("chart");
		this.states = new LinkedHashMap<Integer, State>();
		this.transitions = new LinkedHashMap<Integer, Transition>();
		this.localdata = new LinkedHashMap<Integer, Data>();
		this.indata = new LinkedHashMap<Integer, Data>();
		this.outdata = new LinkedHashMap<Integer, Data>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public State getState(Integer id) {
		return states.get(id);
	}

	public Collection<State> getStates() {
		return Collections.unmodifiableCollection(states.values());
	}

	public void addState(State state) {
		this.states.put(state.getId(), state);
	}

	public Collection<Transition> getTransitions() {
		return Collections.unmodifiableCollection(transitions.values());
	}

	public Transition getTransition(Integer id) {
		return this.transitions.get(id);
	}

	public void addTransition(Transition transition) {
		this.transitions.put(transition.getId(), transition);
	}

	public Transition getFirstTransition() {
		return firstTransition;
	}

	public void setFirstTransition(Transition firstTransition) {
		this.firstTransition = firstTransition;
	}

	public Collection<Data> getLocalData() {
		return Collections.unmodifiableCollection(localdata.values());
	}

	public Collection<Data> getInputData() {
		return Collections.unmodifiableCollection(indata.values());
	}

	public Collection<Data> getOutputData() {
		return Collections.unmodifiableCollection(outdata.values());
	}

	public Data getData(Integer id) {
		Data data = localdata.get(id);
		if (data == null) data = indata.get(id);
		if (data == null) data = outdata.get(id);
		return data;
	}

	public void addData(Data data) {
		if (data != null) {
			if (data.getScope() == DataScope.LOCAL) {
				this.localdata.put(data.getId(), data);
			} else if (data.getScope() == DataScope.INPUT) {
				this.indata.put(data.getId(), data);
			} else if (data.getScope() == DataScope.OUTPUT) {
				this.outdata.put(data.getId(), data);
			}
		}
	}

	public Data getFirstData() {
		return firstData;
	}

	public void setFirstData(Data firstData) {
		this.firstData = firstData;
	}

	public Instance getInstance() {
		return instance;
	}

	public void addInstance(Instance instance) {
		this.instance = instance;
		this.instance.setParent(this);
	}

}