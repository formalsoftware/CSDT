package com.formallab.simulink.mdl;

public class MdlTriggerPort extends MdlPort {

    private String statesWhenEnabling;

	private String triggerType;

	public MdlTriggerPort(String name, String statesWhenEnabling) {
		super("TriggerPort", name, -1, SimulinkType.BOOLEAN);
	}

	public String getStatesWhenEnabling() {
		return statesWhenEnabling;
	}

	public void setStatesWhenEnabling(String statesWhenEnabling) {
		this.statesWhenEnabling = statesWhenEnabling;
	}

	public String getTriggerType() {
		return triggerType;
	}

	public void setTriggerType(String triggerType) {
		this.triggerType = triggerType;
	}

}