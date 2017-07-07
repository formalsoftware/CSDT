package com.formallab.simulink.mdl;

import com.formallab.simulink.mdl.node.MdlField;

public class MdlBlock extends MdlTimedSection {

	private String blockType;

	public MdlBlock(String blockType) {
		super("Block", "Default");
        this.blockType = blockType;
	}

	public MdlBlock(String blockType, String name) {
		super("Block", name);
        this.blockType = blockType;
	}

    public String getBlockType() {
        return this.blockType;
    }

    public void setBlockType(String blockType) {
        this.blockType = blockType;
    }

	public String getFieldValue(String name) {
		MdlField entry = getEntry(name);
		if (entry == null) {
			return null;
		}
		return entry.getValue();
	}

	public void setFieldValue(String name, String value) {
		MdlField entry = getEntry(name);
		if (entry == null) {
			addEntry(new MdlField(name, value));
		} else {
			entry.setValue(value);
		}
	}

    public SimulinkType getOutputDataType(SimulinkType parentDatatype) {
        String datatype = getFieldValue("OutDataType");
		if (datatype == null) {
		    datatype = getFieldValue("OutDataTypeMode");
		}
		if (datatype == null) {
		    datatype = getFieldValue("OutDataTypeStr");
		}

		if (datatype != null) {
			if (datatype.equals("Inherit: Logical (see Configuration Parameters: Optimization)")) {
				return SimulinkType.BOOLEAN;
			} else if (datatype.equals("Logical (see Configuration Parameters: Optimization)")) {
				return SimulinkType.BOOLEAN;
			} else if (datatype.startsWith("Inherit:")) { // auto, ...
				return parentDatatype;
			} else if (datatype.equals("Inherit via internal rule")) {
				// TODO: Inherit via internal rule
				return parentDatatype;
			} else {
				return SimulinkType.getType(datatype);
			}
		} else {
			return SimulinkType.DOUBLE;
		}
	}

    @Override
    public String toString() {
        return this.getName() + " - " + this.getBlockType();
    }

}