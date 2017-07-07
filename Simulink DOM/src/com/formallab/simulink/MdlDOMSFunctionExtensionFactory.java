package com.formallab.simulink;

import com.formallab.simulink.mdl.MdlBlock;
import com.formallab.simulink.mdl.node.MdlSection;

public interface MdlDOMSFunctionExtensionFactory {

	MdlBlock specialiseSfunction(MdlSection sfunctionSection, String name, String tag);
}