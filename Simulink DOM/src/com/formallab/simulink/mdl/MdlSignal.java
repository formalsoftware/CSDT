package com.formallab.simulink.mdl;

import java.util.ArrayList;
import java.util.List;


public class MdlSignal extends MdlNamedSection implements Comparable<MdlSignal> {

	private MdlBlock srcBlock;
	private int srcPort;

	private MdlSignalBranch target;

	private SimulinkType type;

	public MdlSignal() {
		super("Line", "");
	}

	public MdlSignal(String name) {
		super("Line", name);
	}

	public MdlBlock getSrcBlock() {
		return this.srcBlock;
	}

	public void setSrcBlock(MdlBlock srcBlock) {
		this.srcBlock = srcBlock;
	}

	public String getSrcBlockName() {
		return this.srcBlock.getName();
	}

	public int getSrcPort() {
		return this.srcPort;
	}

	public void setSrcPort(int srcPort) {
		this.srcPort = srcPort;
	}

	public SimulinkType getType() {
		return type;
	}

	public void setType(SimulinkType type) {
		this.type = type;
	}

	public MdlSignalBranch getTarget() {
		return target;
	}

	public void setTarget(MdlSignalBranch target) {
		this.target = target;
	}

	public static List<MdlSignal> filterBySource(List<MdlSignal> signals, MdlBlock sourceBlock) {
		List<MdlSignal> result = new ArrayList<MdlSignal>();
		for (MdlSignal mdlSignal : signals) {
			if (mdlSignal.srcBlock == sourceBlock) {
				result.add(mdlSignal);
			}
		}
		return result;
	}

	public static List<MdlSignal> filterByTarget(List<MdlSignal> signals, MdlBlock targetBlock) {
		List<MdlSignal> result = new ArrayList<MdlSignal>();
		for (MdlSignal mdlSignal : signals) {
			if (mdlSignal.target.reaches(targetBlock)) {
				result.add(mdlSignal);
			}
		}
		return result;
	}

	@Override
	public int compareTo(MdlSignal o) {
    	MdlBlock b2 = o.srcBlock;
    	//String s2 = o.srcBlockName;
		int port2 = o.srcPort;

    	if (this.srcBlock != null) {
    		if (this.srcBlock.getBlockType().startsWith("Inport")) {
        		if (b2 != null) {
        			if (b2.getBlockType().startsWith("Inport")) {
        				return new Integer(this.srcPort).compareTo(port2);
        			} else {
        				return -1;
        			}
        		} else {
        			return -1;
        		}
    		} else if (this.srcBlock.getBlockType().startsWith("Outport")) {
    			return +1;
    		} else {
    			return -1;
    		}

    	} else {
    		if (b2 != null) {
    			if (b2.getBlockType().startsWith("Inport")) {
                    return +1;
    			} else if (b2.getBlockType().startsWith("Outport")) {
        			return -1;
        		} else {
    				return 0;
    			}
    		} else {
    			return 0;
    		}
    	}
	}

    @Override
    public String toString() {
        return "Line { " + this.srcBlock.getName() + "[" + this.srcPort + "] }";
    }

}