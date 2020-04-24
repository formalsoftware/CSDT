package com.fware.csdt.simulink.mdl.editor.link;

import org.eclipse.jface.text.IDocument;

//import com.fware.cspdt.cspm.core.model.CspMModel;
import com.formallab.simulink.mdl.MdlModel;
import com.fware.cspdt.cspm.core.model.CspMRef;
//falta ref e esses equivalentes
import lmf.formula.csp.analysis.ExtendedDepthFirstAdapter;
import lmf.formula.csp.node.ACspAbstractDefinition;
import lmf.formula.csp.node.ACspChannel;
import lmf.formula.csp.node.ACspConstantDefinition;
import lmf.formula.csp.node.ACspDatatypeDefinition;
import lmf.formula.csp.node.ACspDefinitionParagraph;
import lmf.formula.csp.node.ACspFunctionDefinition;
import lmf.formula.csp.node.ACspNametypeDefinition;
import lmf.formula.csp.node.ACspProcessDefinition;
import lmf.formula.csp.node.ACspSpecification;
import lmf.formula.csp.node.ACspSubtypeDefinition;
import lmf.formula.csp.node.TName;

public class RefExtractor extends ExtendedDepthFirstAdapter {

	private MdlModel info;
	private IDocument document;

	public RefExtractor(IDocument document, MdlModel info) {
		this.document = document;
		this.info = info;
	}

	public void inACspSpecification(ACspSpecification node) {
		// WARNING: Do not remove this method.
	}

	public void outACspSpecification(ACspSpecification node) {
		// WARNING: Do not remove this method.
	}

	public void inACspDefinitionParagraph(ACspDefinitionParagraph node) {
		// WARNING: Do not remove this method.
	}

	public void outACspDefinitionParagraph(ACspDefinitionParagraph node) {
		// WARNING: Do not remove this method.
	}

	@Override
	public void caseACspDefinitionParagraph(ACspDefinitionParagraph node) {
		node.getDefinition().apply(this);
	}

	@Override
	public void caseACspFunctionDefinition(ACspFunctionDefinition node) {
		TName name = node.getName();
		info.functionDefinitions.put(name.getText(), node.toString());
		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
		info.functionRefs.add(cspMRef);
		info.tocRefs.add(cspMRef);
	}

	@Override
	public void caseACspConstantDefinition(ACspConstantDefinition node) {
		TName name = node.getName();
		info.constantDefinitions.put(name.getText(), node.toString());
		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
		info.constantRefs.add(cspMRef);
		info.tocRefs.add(cspMRef);
	}

	@Override
	public void caseACspDatatypeDefinition(ACspDatatypeDefinition node) {
//		TName name = node.getName();
//		info.datatypeDefinitions.put(name.getText(), node.toString());
//		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
//		info.datatypeRefs.add(cspMRef);
//		info.tocRefs.add(cspMRef);
	}

	@Override
	public void caseACspSubtypeDefinition(ACspSubtypeDefinition node) {
//		TName name = node.getName();
//		info.subtypeDefinitions.put(name.getText(), node.toString());
//		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
//		info.subtypeRefs.add(cspMRef);
//		info.tocRefs.add(cspMRef);
	}

	@Override
	public void caseACspNametypeDefinition(ACspNametypeDefinition node) {
//		TName name = node.getName();
//		info.nametypeDefinitions.put(name.getText(), node.toString());
//		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
//		info.nametypeRefs.add(cspMRef);
//		info.tocRefs.add(cspMRef);
	}

	@Override
	public void caseACspProcessDefinition(ACspProcessDefinition node) {
		TName name = node.getName();
		info.processDefinitions.put(name.getText(), node.toString());
		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
		info.processRefs.add(cspMRef);
		info.tocRefs.add(cspMRef);
	}

	@Override
	public void caseACspAbstractDefinition(ACspAbstractDefinition node) {
//		TName name = node.getName();
//		info.abstractDefinitions.put(name.getText(), node.toString());
//		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
//		info.abstractRefs.add(cspMRef);
//		info.tocRefs.add(cspMRef);
	}

	@Override
	public void caseACspChannel(ACspChannel node) {
		TName name = node.getName();
		info.channelDefinitions.put(name.getText(), node.toString());
		CspMRef cspMRef = new CspMRef(name.getText(), name.getLine(), name.getPos(), null);
		info.channelRefs.add(cspMRef);
		info.tocRefs.add(cspMRef);
	}

//	/*
//	 * As we come across non terminals, push them onto the stack
//	 */
//	public void defaultIn(Node node) {
//		String name = node.getClass().getSimpleName();
//		name = name.substring(4); // Remove "ACsp"
//
//		String parentSimpleName = node.parent().getClass().getSimpleName();
//
//		Position position = computePosition(node);
//		if (name.startsWith("Base")) {
//			// ACspBase...
//			if (name.endsWith("List") && !parentSimpleName.endsWith("List")) {
//				name = name.substring(4); // Remove "Base"
//				push(node, position); // name
//			}
//		} else {
//			// Not Base...
//			if (name.startsWith("Linked")) {
//				if (!parentSimpleName.endsWith("List")) {
//					name = name.substring(6); // Remove "Linked"
//					push(node, position); // name
//				}
//			} else {
//				push(node, position); // name
//			}
//		}
//	}
//
//	/*
//	 * As we leave a non terminal, it's parent is the node before it on the
//	 * stack, so we pop it off and add it to that node
//	 */
//	public void defaultOut(Node node) {
//		String name = node.getClass().getSimpleName();
//
//		String parentSimpleName = node.parent().getClass().getSimpleName();
//
//		if (!name.startsWith("ACspBase")) {
//			name = name.substring(4); // Remove "ACsp"
//			if (name.startsWith("Linked")) {
//				if (!parentSimpleName.endsWith("List"))
//					pop();
//			} else {
//				pop();
//			}
//		} else {
//			if (name.endsWith("List") && !parentSimpleName.endsWith("List")) {
//				pop();
//			}
//		}
//	}
//
//	public void inACspConstantCallExpr(ACspConstantCallExpr node) {
//		push(node, new Position(computeOffset(node.getName()))); // node.toString(),
//	}
//
//	private Position computePosition(Node node) {
//		TokenExtractor tokenExtractor = new TokenExtractor();
//		node.apply(tokenExtractor);
//
//		return new Position(computeOffset(tokenExtractor.getFirstToken()));
//	}
//
//	private void genDefinition(Node node, TName tName) {
//		Position position = new Position(computeOffset(tName), tName.getText().length());
//
//		try {
//			document.addPosition(CspMOutlineContentProvider.SEGMENTS, position);
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		} catch (BadPositionCategoryException e) {
//			e.printStackTrace();
//		}
//
//		addToParent(new CspMRef(node, position));
//	}
//
//	private int computeOffset(Token token) {
//		try {
//			return document.getLineOffset(token.getLine() - 1) + token.getPos() - 1;
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//			return 0;
//		}
//	}
}