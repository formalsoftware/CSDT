package com.fware.csdt.simulink.mdl.editor.config;

import org.eclipse.swt.graphics.RGB;

public enum ColorConstants {

	DEFAULT(new RGB(0, 0, 1)),	
	COMMENT(new RGB(70, 255, 70)),
	MULTILINE_COMMENT(new RGB(110, 122, 110)),
	KEYWORD(new RGB(125, 0, 50)),
	INT(new RGB(255, 0, 0)),
	BOOL(new RGB(0, 220, 0)),
	CHANNEL(new RGB(0, 190, 0)),
	TAG(new RGB(0, 190, 0)),
	FUNCTION(new RGB(50, 0, 100)),
	TYPE(new RGB(0, 0, 100)),
	PROCESS(new RGB(0, 0, 190)),
	CONSTANT(new RGB(180, 130, 130));
	
	private RGB cor;
	
	private ColorConstants(RGB cor) {
		this.cor = cor;
	}

	public RGB getCor() {
		return cor;
	}

}