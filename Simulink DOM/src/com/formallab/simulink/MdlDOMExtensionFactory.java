package com.formallab.simulink;

import com.formallab.simulink.mdl.node.MdlSection;

public interface MdlDOMExtensionFactory {

	MdlSection specialise(MdlSection section);
}