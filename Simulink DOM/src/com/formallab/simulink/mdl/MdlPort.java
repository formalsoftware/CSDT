package com.formallab.simulink.mdl;

public abstract class MdlPort extends MdlBlock implements Comparable<MdlPort> {

	private final int portNum;

	private final SimulinkType datatype;

    public MdlPort(String portType, String name,
    		int portNum, SimulinkType datatype) {
        super(portType, name);
        this.portNum = portNum;
        this.datatype = datatype;
    }

	public int getPortNum() {
		return this.portNum;
	}

    public SimulinkType getDatatype() {
        return this.datatype;
    }

	@Override
	public int compareTo(MdlPort that) {
		return new Integer(this.portNum).compareTo(that.portNum);
	}

}