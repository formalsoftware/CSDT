package com.formallab.simulink.mdl;

import java.util.Map;

public interface MdlSystemOwner {

	public abstract void addVariable(String var, String val);

	public abstract boolean hasMaskVariables();

	public abstract Map<String, String> getMaskVariables();

}