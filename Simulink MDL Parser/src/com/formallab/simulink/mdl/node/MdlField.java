package com.formallab.simulink.mdl.node;

public class MdlField extends MdlNode implements Cloneable {

	private String value;

	public MdlField(String name, String value) {
		super(null, name);
		setValue(value);
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public boolean hasChildren() {
		return false;
	}

	public Object clone() throws CloneNotSupportedException {
		MdlField clone = (MdlField) super.clone();
		clone.setParent(null);
		return clone;
	}

	@Override
	public String toString() {
		return nodeName + "\t" + value;
	}

}
