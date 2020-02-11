package com.formallab.util;

import java.util.ArrayList;
import java.util.List;

public class Lists {
	
	public static <S,T> List<T> collect(List<S> listS, Collector<S,T> collector) {
		List<T> result = new ArrayList<T>(listS.size());
		for (S s : listS) {
			result.add(collector.colect(s));
		}
		return result;
	}
	
}
