package com.formallab.simulink.mdl;

public class MdlInport extends MdlPort {

	public MdlInport(String name, int portNum, SimulinkType datatype) {
		super("Inport", name, portNum, datatype);
	}

	@Override
	public String toString() {
	    return "> " + this.getName();
	}

}