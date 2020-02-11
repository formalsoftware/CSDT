package com.formallab.util;


public class Numbers {

	public static double gcd(double[] array) {
		double gcd = array[0];
		for (int i = 1; i < array.length; i++) {
			gcd = gcd(gcd, array[i]);
		}
		return gcd;
	}
	
	private static double gcd(double a, double b)
	{
		double temp;
		
		while (a > 0.000001) {
			temp = Math.max(a, b);
			b = Math.min(a, b);
			a = temp - b;
		}

		return b;
	}

}
