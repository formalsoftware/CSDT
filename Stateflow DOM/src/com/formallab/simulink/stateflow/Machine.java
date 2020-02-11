package com.formallab.simulink.stateflow;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;


public class Machine extends StateflowSection {

	private String name;

	private Set<Chart> charts;

	private Target target;

	public Machine() {
		this(null);
	}

	public Machine(Stateflow parent) {
		super(parent, "machine");
		this.charts = new LinkedHashSet<Chart>();
	}

	@Override
	public Stateflow getParent() {
		return (Stateflow) super.getParent();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<Chart> getCharts() {
		return Collections.unmodifiableSet(charts);
	}

	public void addChart(Chart chart) {
		if (chart != null) {
			chart.setParent(this);
			this.charts.add(chart);
			getParent().addChart(chart);
		}
	}

	public Target getTarget() {
		return target;
	}

	public void setTarget(Target target) {
		if (target != null) {
			target.setParent(this);
			this.target = target;
		}
	}

}