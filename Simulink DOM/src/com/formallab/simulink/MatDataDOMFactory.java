package com.formallab.simulink;

import com.formallab.simulink.mdl.node.MdlSection;

public class MatDataDOMFactory implements MdlDOMExtensionFactory {

	private static MatDataDOMFactory instance;

	static {
		instance = new MatDataDOMFactory();
		
		MdlDOMFactory.registerExtension("MatData", MatDataDOMFactory.instance);
	}

	@Override
	public MdlSection specialise(MdlSection section) {
		//TODO MatDataDOMFactory convert
		return section;
	}
}