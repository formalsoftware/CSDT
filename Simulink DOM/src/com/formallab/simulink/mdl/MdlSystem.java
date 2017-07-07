package com.formallab.simulink.mdl;

import java.util.ArrayList;
import java.util.List;


public class MdlSystem extends MdlTimedSection {

    private final List<MdlInport> inports;

    private final List<MdlOutport> outports;

    private final List<MdlSubSystem>  subSystems;

    private final List<MdlBlock> blocks;

    private final List<MdlSignal> signals;

	public MdlSystem(String name) {
        super("System", name);

        this.inports = new ArrayList<MdlInport>();
        this.outports = new ArrayList<MdlOutport>();
        this.subSystems = new ArrayList<MdlSubSystem>();
        this.blocks = new ArrayList<MdlBlock>();
        this.signals = new ArrayList<MdlSignal>();
    }

    public void addInport(MdlInport inport) {
        this.inports.add(inport);
        inport.setParent(this);
    }

    public List<MdlInport> getInports() {
        return this.inports;
    }

    public void addOutport(MdlOutport outport) {
        this.outports.add(outport);
        outport.setParent(this);
    }

    public List<MdlOutport> getOutports() {
        return this.outports;
    }

    public void addSubSystem(MdlSubSystem subSystem) {
        this.subSystems.add(subSystem);
    }

    public List<MdlSubSystem> getSubSystems() {
        return this.subSystems;
    }

    public void addBlock(MdlBlock block) {
    	if (block instanceof MdlInport) {
    		addInport((MdlInport) block);
    	} else if (block instanceof MdlOutport) {
    		addOutport((MdlOutport) block);
    	} else if (block instanceof MdlSubSystem) {
    		addSubSystem((MdlSubSystem) block);
    	} else {
            this.blocks.add(block);
    	}
        block.setParent(this);
    }

    public List<MdlBlock> getBlocks() {
        return this.blocks;
    }

    public void addSignal(MdlSignal signal) {
        this.signals.add(signal);
        signal.setParent(this);
    }

    public List<MdlSignal> getSignals() {
        return this.signals;
    }

	public MdlBlock getBlock(String name) {
		for (MdlInport i : this.inports) {
			if (i.getName().equals(name)) {
				return i;
			}
		}
		for (MdlOutport o : this.outports) {
			if (o.getName().equals(name)) {
				return o;
			}
		}
		for (MdlBlock b : this.blocks) {
			if (b.getName().equals(name)) {
				return b;
			}
		}
        for (MdlSubSystem b : this.subSystems) {
            if (b.getName().equals(name)) {
                return b;
            }
        }
		return getEntry(name);
	}

}
