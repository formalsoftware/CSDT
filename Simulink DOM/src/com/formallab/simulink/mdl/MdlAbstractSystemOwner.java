package com.formallab.simulink.mdl;

import java.util.Hashtable;
import java.util.Map;

import com.formallab.simulink.mdl.node.MdlNode;

public abstract class MdlAbstractSystemOwner extends MdlTimedSection implements MdlSystemOwner {

    private final Map<String, String> maskVars;

	protected MdlSystem system;

	public MdlAbstractSystemOwner(String nodeName, String name) {
		super(nodeName, name);
		this.maskVars = new Hashtable<String, String>();
	}

	public MdlAbstractSystemOwner(MdlNode parent, String nodeName, String name) {
		super(parent, nodeName, name);
		this.maskVars = new Hashtable<String, String>();
	}

	@Override
	public void addVariable(String var, String val) {
        this.maskVars.put(var, val);
    }

	@Override
    public boolean hasMaskVariables() {
        return this.maskVars.size() > 0;
    }

	@Override
    public Map<String, String> getMaskVariables() {
        return this.maskVars;
    }

	public MdlSystem getSystem() {
	    return this.system;
	}

	public void setSystem(MdlSystem system) {
	    this.system = system;
	}

}