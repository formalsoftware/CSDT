package com.formallab.rewriting;

import com.formallab.commons.Function;


public interface FunctionalRule<C,L,R> extends Function<L,R> {

	/**
	 * Set the context in which the rule must be applied.
	 * @param context
	 */
	void setContext(C context);

	/**
	 * Apply this Rule from <b>left</b> hand-site to right hand-side.
	 * 
	 * @param element The <b>left</b> hand-side
	 * @return The right hand-side
	 */
	R applyTo(L element);

}