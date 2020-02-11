package com.formallab.rewriting;

public abstract class MappingRule<C, L, R> implements FunctionalRule<C, L, R> {

	@Override
	public boolean isPartial() {
		return false;
	}
	
	@Override
	public boolean isTotal() {
		return true;
	}
	
	@Override
	public boolean isSurjective() {
		return true;
	}

}