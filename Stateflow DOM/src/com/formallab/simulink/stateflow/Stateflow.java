package com.formallab.simulink.stateflow;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.formallab.simulink.mdl.node.MdlSection;

public class Stateflow extends MdlSection {

	public static final String STATEFLOW = "Stateflow";

	private Map<Integer, Machine> machines;

	private Map<Integer, Chart> charts;

	public Stateflow() {
		super(STATEFLOW);
		
		this.machines = new HashMap<Integer, Machine>();
		this.charts = new HashMap<Integer, Chart>();
	}

	public Collection<Machine> getMachines() {
		return machines.values();
	}

	public Machine getMachine(Integer machineId) {
		return machines.get(machineId);
	}

	public void addMachine(Machine machine) {
		if (machine != null) {
			machines.put(machine.getId(), machine);
			machine.setParent(this);
		}
	}

	public void addChart(Chart chart) {
		if (chart != null) {
			charts.put(chart.getId(), chart);
		}
	}

	public Collection<Chart> getCharts() {
		return charts.values();
	}

	public Chart getChart(int chartId) {
		return charts.get(chartId);
	}

}