package com.formallab.simulink.mdl;

public class MdlOutport extends MdlPort {

	public MdlOutport(String name, int portNum, SimulinkType datatype) {
		super("Outport", name, portNum, datatype);
	}

	@Override
    public String toString() {
        return this.getName() + " >";
    }

}