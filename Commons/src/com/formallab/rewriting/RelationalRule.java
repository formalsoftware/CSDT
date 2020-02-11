package com.formallab.rewriting;

import com.formallab.commons.Relation;


public interface RelationalRule<C,L,R> extends Relation<L,R> {

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
	R applyLhs2Rhs(L element);

	/**
	 * Apply this Rule from <b>right</b> hand-site to left hand-side.
	 * 
	 * @param element The <b>right</b> hand-side
	 * @return The left hand-side
	 */
	L applyRhs2Lhs(R element);

}