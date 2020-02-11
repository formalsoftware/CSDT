package com.formallab.simulink.libraries;

import com.formallab.simulink.mdl.MdlBlock;
import com.formallab.simulink.mdl.MdlLibrary;

public class Libraries {

	public static final MdlLibrary SimulinkExtras;
	
	static {
		SimulinkExtras = new MdlLibrary("Simulink Extras");

		MdlLibrary flipFlops = SimulinkExtras.addSubLibrary(new MdlLibrary("Flip Flops"));
		flipFlops.addBlock(new MdlBlock("S-R\\nFlip-Flop", "S-R\\nFlip-Flop"));
	}

}
