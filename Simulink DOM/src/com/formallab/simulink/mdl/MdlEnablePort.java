package com.formallab.simulink.mdl;

public class MdlEnablePort extends MdlPort {

    private String statesWhenEnabling;

    private boolean zeroCross;

	public MdlEnablePort(String name, String statesWhenEnabling) {
		super("EnablePort", name, 0, SimulinkType.BOOLEAN);
		this.setStatesWhenEnabling(statesWhenEnabling);
	}

	public String getStatesWhenEnabling() {
		return statesWhenEnabling;
	}

	public void setStatesWhenEnabling(String statesWhenEnabling) {
		this.statesWhenEnabling = statesWhenEnabling;
	}

	public boolean isZeroCross() {
		return zeroCross;
	}

	public void setZeroCross(boolean zeroCross) {
		this.zeroCross = zeroCross;
	}

}