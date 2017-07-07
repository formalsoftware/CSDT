package com.formallab.simulink.mdl.node;


public abstract class MdlNode implements MdlElement {

	private MdlNode parent;

	protected String nodeName;

	protected MdlNode() {
		this(null, null);
	}

	protected MdlNode(MdlNode parent) {
		this(parent, null);
	}

	protected MdlNode(String nodeName) {
		this(null, nodeName);
	}

	protected MdlNode(MdlNode parent, String nodeName) {
		this.parent = parent;
		this.nodeName = nodeName;
	}

	public MdlNode getParent() {
		return parent;
	}

	public void setParent(MdlNode parent) {
		assert !hasParent();
		this.parent = parent;
	}

	public String getNodeName() {
		return nodeName;
	}

	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}

	public boolean hasParent() {
		return getParent() != null;
	}

	public abstract boolean hasChildren();

	public int getLevel() {
		int level = 0;
		for (MdlNode e = this; e.hasParent(); e = e.getParent()) {
			level++;
		}
		return level;
	}

}
