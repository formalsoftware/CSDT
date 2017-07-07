package com.formallab.simulink;

import static com.formallab.util.Numbers.gcd;

import java.util.Set;

import com.formallab.simulink.mdl.MdlModel;
import com.formallab.simulink.mdl.MdlTimedSection;


final class SampleTimeComputation {

	public static final String CONTINUOUS_ST = "inf";

	public static final int INHERITED_ST = -1;

	private static boolean isInherited(String sampleTime) {
		return "-1".equals(sampleTime)
				|| "inherited".equals(sampleTime);
	}

	public static String getSampleTime(MdlModel model) {
		return CONTINUOUS_ST;
	}

	public static String getSampleTime(MdlTimedSection section, String parentSampleTime) {
        String sampleTime = section.getSampleTime();
        if (sampleTime == null || isInherited(sampleTime)) {
        	sampleTime = parentSampleTime;
        }
//        if (CONTINUOUS_ST.equals(sampleTime)) {
//            // Still 'inf'
//        	return "...";
//        }
		return sampleTime;
	}

	/*
	 * {- SAMPLING -}
	 * 
	 * INTERVAL_64Hz = 0.015625 -- seconds (s)
	 * INTERVAL_80Hz = 0.0125 -- seconds (s)
	 * GCD = 0.003125 -- Greatest Common Divisor - seconds (s)
	 * GCD_SAMPLE_STEP_64Hz = 5
	 * GCD_SAMPLE_STEP_80Hz = 4
	 * 
	 * MAX_GCD_SAMPLE_STEP = 5
	 * -- MAX_GCD_SAMPLE_STEP = max(GCD_SAMPLE_STEP_64Hz, GCD_SAMPLE_STEP_80Hz)
	 * 
	 * GCD_Hz = 16 -- Greatest Common Divisor - hertz (Hz)
	 * 
	 * LCM = 0.0625 -- Least Common Multiple - seconds (s)
	 * -- LCM = 1 / GCD_Hz -- Least Common Multiple - seconds (s)
	 * -- LCM = INTERVAL_64Hz * GCD_SAMPLE_STEP_80Hz -- Least Common Multiple - seconds (s)
	 * -- LCM = INTERVAL_80Hz * GCD_SAMPLE_STEP_64Hz -- Least Common Multiple - seconds (s)
	 * LCM_GCD_SAMPLE_STEP = 20
	 * -- LCM_GCD_SAMPLE_STEP = GCD_SAMPLE_STEP_64Hz * GCD_SAMPLE_STEP_80Hz
	 * -- LCM = GCD * LCM_GCD_SAMPLE_STEP -- Least Common Multiple - seconds (s)
	 * 
	 * LCM_Hz = 320 -- Least Common Multiple - hertz (Hz)
	 * -- LCM_Hz = 1 / GCD -- Least Common Multiple - hertz (Hz)
	 * -- LCM_Hz = GCD_Hz * LCM_SAMPLE_STEP -- Least Common Multiple - hertz (Hz)
	 * 
	 * 
	 * SAMPLE_TIME = GCD -- seconds (s)
	 * SAMPLE_FREQUENCE = LCM_Hz -- hertz (Hz)
	 * 
	 * nametype SampleStep = { 1..MAX_GCD_SAMPLE_STEP }
	 */
	public static double[] getSampleTimeValues(Set<String> sampleTimes) {
		int length = sampleTimes.size();
		double[] array = new double[length];
		double minST = Double.MAX_VALUE;

		int i = 0;
		for (String st : sampleTimes) {
			try {
				array[i] = Double.parseDouble(st);
				minST = Math.min(minST, array[i]);
			} catch (NumberFormatException e) {
				String[] temp = st.split("\\/");

				if (temp.length == 1) {
					array[i] = INHERITED_ST;
				} else if (temp.length == 2) {
					if (temp[0].equals("1")) {
						array[i] = 1 / Double.parseDouble(temp[1]);
						minST = Math.min(minST, array[i]);
					} else {
						throw new InternalError("Cannot translate the sample time: " + st);
					}
				} else {
					throw new InternalError("Cannot translate the sample time: " + st);
				}
			}
			i++;
		}
		
		if (minST == Double.MAX_VALUE) {
			minST = 0;
		}
		
		// Fix continuous STs.
		for (i = 0; i < array.length; i++) {
			if (array[i] == INHERITED_ST) {
				array[i] = minST;
			}
		}

		return array;
	}

	public static double getFundamentalSampleTime(double[] sampleTimes) {
		final double fundamentalSampleStep = gcd(sampleTimes);

		final double stepSize = fundamentalSampleStep;
		
		return stepSize;
	}

}
