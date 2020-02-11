package com.formallab.simulink.mdl;

import java.util.Hashtable;
import java.util.Map;


public class MdlSubSystem extends MdlBlock implements MdlSystemOwner {

    private final Map<String, String> maskVars;

    private MdlSystem system;

    public MdlSubSystem(MdlSystem system) {
        super("SubSystem", null);
        this.system = system;
		this.maskVars = new Hashtable<String, String>();
    }

    public MdlSubSystem(String name, MdlSystem system) {
        super("SubSystem", name);
        this.system = system;
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

	public void copyMaskVariablesTo(MdlSubSystem mdlSubSystem) {
		for (Map.Entry<String, String> var : maskVars.entrySet()) {
			mdlSubSystem.maskVars.put(var.getKey(), var.getValue());
		}
	}

    public MdlSystem getSystem() {
        return this.system;
    }

    public void setSystem(MdlSystem system) {
        this.system = system;
    }

}