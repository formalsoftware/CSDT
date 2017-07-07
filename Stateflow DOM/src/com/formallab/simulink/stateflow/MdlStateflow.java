package com.formallab.simulink.stateflow;

import com.formallab.simulink.mdl.MdlSFunction;
import com.formallab.simulink.stateflow.Machine;
import com.formallab.simulink.stateflow.Stateflow;


public class MdlStateflow extends MdlSFunction {

	private Stateflow stateflow;

	private String machineName;
	private int machineId;

	/**
	 * 
	 * @param name
	 * @param tag String "Stateflow S-Function <MachineName> <MachineId>"
	 * @param functionName
	 */
	public MdlStateflow(String name, String tag, String functionName) {
		super(name, tag, functionName);

		if (tag != null) {
			String[] temp = tag.split(" +");
			machineName = temp[2];
			machineId = Integer.parseInt(temp[3]);
		}
	}

	public Stateflow getStateflow() {
		return stateflow;
	}

	public void setStateflow(Stateflow stateflow) {
		this.stateflow = stateflow;
	}

	public String getMachineName() {
		return machineName;
	}

	public Machine getMachine() {
		return stateflow.getMachine(machineId);
	}

}
