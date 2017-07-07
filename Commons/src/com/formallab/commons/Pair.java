package com.formallab.commons;

public interface Pair<X,Y> extends Tuple {

	X first();
	
	Y second();
}