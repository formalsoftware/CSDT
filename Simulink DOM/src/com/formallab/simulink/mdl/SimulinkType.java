package com.formallab.simulink.mdl;

import java.security.InvalidParameterException;

import com.formallab.simulink.mdl.node.MdlElement;


public enum SimulinkType implements MdlElement {

	INHERITED("*"),

	BOOLEAN("boolean"),

	/**
	 * Create MATLAB structure describing unsigned generalized fixed-point data type
	 * Syntax: ufix(TotalBits)
	 */
	UFIX("ufix"),

	/**
	 * Create MATLAB structure describing unsigned integer data type.
	 * Syntax: uint(TotalBits)
	 */
	UINT("uint"),

	UINT_8("uint8"),
	UINT_16("uint16"),
	UINT_32("uint32"), 

	/**
	 * Create MATLAB structure describing signed integer data type.
	 * Syntax: sint(TotalBits)
	 */
	SINT("sint"),

	INT_8("int8"),
	INT_16("int16"),
	INT_32("int32"),

	/**
	 * Create MATLAB structure describing signed generalized fixed-point data type.
	 * Syntax: sfix(TotalBits)
	 */
	SFIX("sfix"),
	
	/**
	 * Create Simulink.NumericType object describing fixed-point or floating-point data type.
	 * Syntax: fixdt(Signed, WordLength)
	 *         fixdt(Signed, WordLength, FractionLength)
	 *           returns a Simulink.NumericType object describing a fixed-point data
	 *           type with binary point scaling.
	 *         fixdt(Signed, WordLength, TotalSlope, Bias)
	 *         fixdt(Signed, WordLength, SlopeAdjustmentFactor, FixedExponent, Bias)
	 *         fixdt(DataTypeNameString)
	 */
	FIXDT("fixdt"),

	FIXDT_1_16_0("fixdt(1,16,0)"),
	FIXDT_1_16_2e0_0("fixdt(1,16,2^0,0)"),
	
	/**
	 * Create MATLAB structure describing floating-point data type.
	 * Syntax: float('single')
	 *         float('double')
	 *         float(TotalBits, ExpBits)
	 */
	FLOAT("float"),

	/**
	 * Create MATLAB structure describing signed fractional data type.
	 * Range: from -2^4 = -16 to (1 - 2^(1 - 8)).2^4 = 15.875
	 * Syntax: sfrac(TotalBits)
	 *         sfrac(TotalBits, GuardBits)
	 */
	SFRAC("sfrac"),

	/**
	 * Create MATLAB structure describing unsigned fractional data type.
	 * Range: from 0 to (1 - 2^-8).2^4 = 15.9375
	 * Syntax: ufrac(TotalBits)
	 *         ufrac(TotalBits, GuardBits)
	 */
	UFRAC("ufrac"),

	SINGLE("single"),
	DOUBLE("double");

	private String name;

	private SimulinkType(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	public static SimulinkType getType(String datatype) {
		if (datatype != null) {
			for (SimulinkType type : SimulinkType.values()) {
				if (datatype.equals(type.getName())) {
					return type;
				} else if (datatype.startsWith(type.getName())) {
					return type;
				}
			}
		}
		
		throw new InvalidParameterException("Unknown Simulink datatype: " + datatype);
	}

}