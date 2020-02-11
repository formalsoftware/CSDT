package com.formallab.commons;

public interface Function<X,Y> extends Relation<X,Y> {

	boolean isPartial();

	boolean isTotal();

	boolean isSurjective();

}