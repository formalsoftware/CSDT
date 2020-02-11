package com.formallab.simulink;

import java.util.List;
import java.util.Set;

import com.formallab.simulink.mdl.MdlBlock;
import com.formallab.simulink.mdl.MdlModel;
import com.formallab.simulink.mdl.MdlSignal;
import com.formallab.simulink.mdl.MdlSubSystem;
import com.formallab.simulink.mdl.MdlSystem;
import com.formallab.simulink.mdl.MdlTimedSection;


public class SimulinkContext {

	public static SimulinkContext load(MdlModel model) {
		return new SimulinkContext(model);
	}

	public SimulinkContext load(MdlSubSystem subsystem) {
		return new SimulinkContext(this, subsystem);
	}
	
	public static final String CONTINUOUS_ST = "inf";
	
	public final SimulinkContext parent;

	public final String sampleTime;

	private final MdlSystem system;
	
	public boolean hasEnabledSubsystem;
	
	public boolean hasEnablePort;

	private SimulinkContext(MdlModel model) {
		this.parent = null;
		this.sampleTime = SampleTimeComputation.getSampleTime(model);
		
		this.system = model.getSystem();
	}

	private SimulinkContext(SimulinkContext parent, MdlSubSystem section) {
		assert parent != null;
		this.parent = parent;
        this.sampleTime = SampleTimeComputation.getSampleTime(section, parent.sampleTime);
		
		this.system = section.getSystem();
	}

	public String getSampleTime(MdlTimedSection section) {
		return SampleTimeComputation.getSampleTime(section, this.sampleTime);
	}

	public static double[] getSampleTimeValues(Set<String> sampleTimes) {
		return SampleTimeComputation.getSampleTimeValues(sampleTimes);
	}

	public static double getFundamentalSampleTime(double[] sampleTimes) {
		return SampleTimeComputation.getFundamentalSampleTime(sampleTimes);
	}

	public List<MdlSignal> getBlockInputSignals(MdlBlock block) {
		return MdlSignal.filterByTarget(system.getSignals(), block);
	}

	public List<MdlSignal> getBlockOutputSignals(MdlBlock block) {
		return MdlSignal.filterBySource(system.getSignals(), block);
	}

}