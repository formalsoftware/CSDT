package com.formallab.util;

public interface Collector<S, T> {

	public T colect(S signal);

}