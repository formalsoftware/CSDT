package com.formallab.simulink.mdl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.formallab.simulink.mdl.node.MdlSection;


public class MdlSignalBranch extends MdlSection {

	private MdlBlock dstBlock;

    private String dstPort;

    private final List<MdlSignalBranch> branches;

    public MdlSignalBranch() {
    	super("Branch");
        this.branches = new ArrayList<MdlSignalBranch>(3);
    }

	public MdlBlock getDstBlock() {
		return this.dstBlock;
	}

	public void setDstBlock(MdlBlock dstBlock) {
		this.dstBlock = dstBlock;
	}

    public String getDstBlockName() {
        return this.dstBlock.getName();
    }

    public String getDstPort() {
        return this.dstPort;
    }

    public void setDstPort(String dstPort) {
        this.dstPort = dstPort;
    }

    public Iterator<MdlSignalBranch> branches() {
        return this.branches.iterator();
    }

    public void addBranch(MdlSignalBranch branch) {
        this.branches.add(branch);
        branch.setParent(this);
    }

    public boolean hasBranches() {
        return this.branches.size() > 0;
    }

    @Override
    public String toString() {
        return super.toString();
    }

	public boolean reaches(MdlBlock targetBlock) {
		if (dstBlock != null && dstBlock == targetBlock)
			return true;
		
		for (MdlSignalBranch mdlSignalBranch : branches) {
			if (mdlSignalBranch.reaches(targetBlock)) {
				return true;
			}
		}
		
		return false;
	}
}