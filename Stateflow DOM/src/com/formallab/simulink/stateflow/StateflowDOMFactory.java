package com.formallab.simulink.stateflow;

import java.util.LinkedList;
import java.util.List;

import com.formallab.simulink.MdlDOMExtensionFactory;
import com.formallab.simulink.MdlDOMFactory;
import com.formallab.simulink.MdlDOMSFunctionExtensionFactory;
import com.formallab.simulink.mdl.MdlBlock;
import com.formallab.simulink.mdl.node.MdlElement;
import com.formallab.simulink.mdl.node.MdlField;
import com.formallab.simulink.mdl.node.MdlSection;

public class StateflowDOMFactory implements MdlDOMExtensionFactory, MdlDOMSFunctionExtensionFactory {
	
	private static Stateflow stateflow;

	private static Chart currentChart;

	private static List<Chart> charts;

	private static List<MdlChart> chartBlocks;

	private static StateflowDOMFactory instance;
	
	static {
		instance = new StateflowDOMFactory();
		
		chartBlocks = new LinkedList<MdlChart>();
		charts = new LinkedList<Chart>();
		
		MdlDOMFactory.registerExtension(Stateflow.STATEFLOW, StateflowDOMFactory.instance);
		MdlDOMFactory.registerSfunctionExtension(MdlChart.SF_SFUN, StateflowDOMFactory.instance);
	}

	public static void add(MdlChart mdlChart) {
		if (mdlChart != null) {
			chartBlocks.add(mdlChart);
		}
	}

	private static Machine getMachine(MdlSection section) {
		return stateflow.getMachine(section.cutInt("machine"));
	}

	private static Chart getChart(MdlSection section) {
		return stateflow.getChart(section.cutInt("chart"));
	}

	static Stateflow convert(MdlSection section) {
		stateflow = new Stateflow();
		
		MdlSection subSection;
		String nodeName;
		for (MdlElement mdlNode : section) {
			if (mdlNode instanceof MdlSection) {
				subSection = (MdlSection) mdlNode;

				nodeName = subSection.getNodeName();
				if (nodeName.equals("machine")) {
		            generateMachine(subSection);
		        } else if (nodeName.equals("chart")) {
		            generateChart(subSection);
		        } else if (nodeName.equals("state")) {
		            generateState(subSection);
		        } else if (nodeName.equals("transition")) {
		            generateTransition(subSection);
		        } else if (nodeName.equals("data")) {
		        	generateData(subSection);
		        } else if (nodeName.equals("instance")) {
		        	generateInstance(subSection);
		        } else if (nodeName.equals("target")) {
		        	generateTarget(subSection);
		        }
			}
		}
		
		FirstTransition firstTransition;
		Transition transition;
		FirstData firstData;
		Data data;
		for (Chart chart : stateflow.getCharts()) {
			transition = chart.getFirstTransition();
			if (transition instanceof FirstTransition) {
				firstTransition = (FirstTransition) transition;
				transition = chart.getTransition(firstTransition.getId());
				firstTransition.replaceBy(transition);
			}
			data = chart.getFirstData();
			if (data instanceof FirstData) {
				firstData = (FirstData) data;
				data = chart.getData(firstData.getId());
				firstData.replaceBy(data);
			}
		}
		
		Chart chart;
		for (MdlChart mdlChart : chartBlocks) {
			chart = charts.get(mdlChart.getChartIndex()-1);
	    	mdlChart.setChart(chart);
		}
		chartBlocks.clear();
		
		return stateflow;
	}

	private static void generateMachine(MdlSection section) {
		String name = section.cutFieldValue("name");

		Machine machine = convert(section, new Machine());
		machine.setName(name);

		stateflow.addMachine(machine);
	}

	private static void generateChart(MdlSection section) {
		Machine machine = getMachine(section);

		String name = section.cutFieldValue("name");
		int firstTransitionId = section.cutInt("firstTransition");
		int firstDataId = section.cutInt("firstData");

		currentChart = convert(section, new Chart());

		Transition firstTransition = new FirstTransition(firstTransitionId, currentChart);
		Data firstData = new FirstData(firstDataId, currentChart);

		currentChart.setName(name);
		currentChart.setFirstTransition(firstTransition);
		currentChart.setFirstData(firstData);

		machine.addChart(currentChart);
		
		charts.add(currentChart);
	}

	private static class FirstTransition extends Transition {

		private Chart chart;

		public FirstTransition(int id, Chart chart) {
			this.setId(id);
			this.chart = chart;
		}

		private void replaceBy(Transition transition) {
			chart.setFirstTransition(transition);
		}

	}

	private static class FirstData extends Data {

		private Chart chart;

		public FirstData(int id, Chart chart) {
			super(DataScope.LOCAL);
			this.setId(id);
			this.chart = chart;
		}

		private void replaceBy(Data data) {
			chart.setFirstData(data);
		}

	}

	private static void generateState(MdlSection section) {
		Chart chart = getChart(section);

		String labelString = section.cutFieldValue("labelString");
		int index = labelString.indexOf("\\n");
		String name = labelString.substring(0, index);
		labelString = labelString.substring(index+2);

		State state = convert(section, new State());
		state.setName(name);
		state.setLabelString(labelString);

		chart.addState(state);
	}

	private static void generateTransition(MdlSection section) {
		Chart chart = getChart(section);
		
		MdlSection srcSection = section.removeEntry("src");
		MdlSection dstSection = section.removeEntry("dst");
		int executionOrder = section.cutInt("executionOrder");
		String labelString = section.cutFieldValue("labelString");
		if (labelString != null) {
			labelString = labelString.substring(1, labelString.length()-1);
		}

		Src src = convert(srcSection, new Src());
		Dst dst = convert(dstSection, new Dst());
		
		src.setState(chart.getState(src.getId()));
		dst.setState(chart.getState(dst.getId()));

		Transition transition = convert(section, new Transition());
		transition.setSrc(src);
		transition.setDst(dst);
		transition.setExecutionOrder(executionOrder);
		transition.setLabelString(labelString);

		chart.addTransition(transition);
	}

	private static void generateData(MdlSection section) {
		MdlField scopeField = section.removeEntry("scope");
		String scopeValue = scopeField.getValue();
		DataScope scope = DataScope.LOCAL;
		if (scopeValue.equals("INPUT_DATA")) {
			scope = DataScope.INPUT;
		} else if (scopeValue.equals("OUTPUT_DATA")) {
			scope = DataScope.OUTPUT;
		}
		Data data = convert(section, new Data(scope));

		currentChart.addData(data);
	}

	private static void generateInstance(MdlSection section) {
		Chart chart = getChart(section);
		if (chart != currentChart) {
			throw new InternalError("Chart instance problem.");
		}

		Instance instance = convert(section, new Instance());

		chart.addInstance(instance);
	}

	private static void generateTarget(MdlSection section) {
		Machine parent = getMachine(section);
		Target target = convert(section, new Target());
		parent.setTarget(target);
	}

	private static <T extends StateflowSection> T convert(MdlSection section, T ret) {
		ret.setId(section.cutInt("id"));
		section.copyFieldsTo(ret);
		return ret;
	}

	@Override
	public MdlSection specialise(MdlSection section) {
		return null;
	}

	@Override
	public MdlBlock specialiseSfunction(MdlSection sfunctionSection,
			String name, String tag) {
    	MdlChart mdlChart = new MdlChart(name, tag);
    	StateflowDOMFactory.add(mdlChart);
    	return mdlChart;
	}
}