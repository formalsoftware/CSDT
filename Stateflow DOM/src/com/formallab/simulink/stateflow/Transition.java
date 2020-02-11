package com.formallab.simulink.stateflow;



public class Transition extends ChartSection {

	private Src src;

	private Dst dst;
	
	private int executionOrder;

	public Transition() {
		super("transition");
	}

	public Src getSrc() {
		return src;
	}

	public void setSrc(Src src) {
		this.src = src;
		if (src != null) {
			src.setParent(this);

			State state = src.getState();
			if (state != null) {
				state.addOutTransition(this);	
			}
		}
	}

	public Dst getDst() {
		return dst;
	}

	public void setDst(Dst dst) {
		this.dst = dst;
		if (dst != null) {
			dst.setParent(this);
			dst.getState().addInTransition(this);
		}
	}

	public int getExecutionOrder() {
		return executionOrder;
	}

	public void setExecutionOrder(int executionOrder) {
		this.executionOrder = executionOrder;
	}

}