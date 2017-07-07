package com.formallab.simulink.stateflow;

import com.formallab.simulink.mdl.MdlSFunction;

public class MdlChart extends MdlSFunction {

	public static final String SF_SFUN = "sf_sfun";

	private Chart chart;

	private int chartIndex;

	/**
	 * 
	 * @param name
	 * @param tag String "Stateflow S-Function <MachineName> <Chart index>"
	 */
	public MdlChart(String name, String tag) {
		super(name, tag, SF_SFUN);

		if (tag != null) {
			String[] temp = tag.split(" +");
			chartIndex = Integer.parseInt(temp[3]);
		}
	}

	public int getChartIndex() {
		return chartIndex;
	}

	public Chart getChart() {
		return chart;
	}

	public void setChart(Chart chart) {
		this.chart = chart;
	}
}