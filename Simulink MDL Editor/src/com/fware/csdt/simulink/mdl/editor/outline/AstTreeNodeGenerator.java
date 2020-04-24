package com.fware.csdt.simulink.mdl.editor.outline;

import java.util.Stack;

import javax.swing.tree.DefaultMutableTreeNode;

//falta esses equivalentes
import lmf.formula.csp.analysis.ExtendedDepthFirstAdapter;
import lmf.formula.csp.node.ACspAbstractDefinition;
import lmf.formula.csp.node.ACspChannel;
import lmf.formula.csp.node.ACspCommentParagraph;
import lmf.formula.csp.node.ACspConstantCallExpr;
import lmf.formula.csp.node.ACspConstantDefinition;
import lmf.formula.csp.node.ACspDatatypeDefinition;
import lmf.formula.csp.node.ACspDefinitionParagraph;
import lmf.formula.csp.node.ACspFunctionDefinition;
import lmf.formula.csp.node.ACspNametypeDefinition;
import lmf.formula.csp.node.ACspProcessDefinition;
import lmf.formula.csp.node.ACspSpecification;
import lmf.formula.csp.node.ACspSubtypeDefinition;
import lmf.formula.csp.node.EOF;
import lmf.formula.csp.node.Node;
import lmf.formula.csp.node.Start;
import lmf.formula.csp.node.TName;
import lmf.formula.csp.node.Token;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPositionCategoryException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;

//falta token extractor
import com.fware.cspdt.cspm.core.model.TokenExtractor;

/**
 * @author Cauanne e Gustavo
 */
public class AstTreeNodeGenerator extends ExtendedDepthFirstAdapter {

	private IDocument document;

	private OutlineTreeNode rootNode;

	private Stack<OutlineTreeNode> parents;

	private boolean detailed;

	public AstTreeNodeGenerator(IDocument document, boolean detailed) {
		this.document = document;
		this.detailed = detailed;
	}

	public OutlineTreeNode getRootNode() {
		return this.rootNode;
	}

	public void inStart(Start node) {
		this.parents = new Stack<OutlineTreeNode>();

		Position position = new Position(0, 0);
		try {
			this.document.addPosition(OutlineContentProvider.SEGMENTS, position);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (BadPositionCategoryException e) {
			e.printStackTrace();
		}

		this.rootNode = push(node, position); // Specification
	}

	public void outStart(Start node) {
		OutlineTreeNode rootNode = this.parents.pop();
		rootNode.updateChildren();

		if (rootNode != this.rootNode)
			throw new IllegalStateException();
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
	
	public void caseACspCommentParagraph(ACspCommentParagraph node) {
		// WARNING: Do not remove this method.
	}
	
	@Override
	public void caseACspDefinitionParagraph(ACspDefinitionParagraph node) {
		node.getDefinition().apply(this);
	}

	@Override
	public void caseACspFunctionDefinition(ACspFunctionDefinition node) {
		if (detailed)
			super.caseACspFunctionDefinition(node);
		else
			genDefinition(node, node.getName());
	}

	@Override
	public void caseACspConstantDefinition(ACspConstantDefinition node) {
		if (detailed)
			super.caseACspConstantDefinition(node);
		else
			genDefinition(node, node.getName());
	}

	@Override
	public void caseACspDatatypeDefinition(ACspDatatypeDefinition node) {
		if (detailed)
			super.caseACspDatatypeDefinition(node);
		else
			genDefinition(node, node.getName());
	}

	@Override
	public void caseACspSubtypeDefinition(ACspSubtypeDefinition node) {
		if (detailed)
			super.caseACspSubtypeDefinition(node);
		else
			genDefinition(node, node.getName());
	}

	@Override
	public void caseACspNametypeDefinition(ACspNametypeDefinition node) {
		if (detailed)
			super.caseACspNametypeDefinition(node);
		else
			genDefinition(node, node.getName());
	}

	@Override
	public void caseACspProcessDefinition(ACspProcessDefinition node) {
		if (detailed)
			super.caseACspProcessDefinition(node);
		else
			genDefinition(node, node.getName());
	}

	@Override
	public void caseACspAbstractDefinition(ACspAbstractDefinition node) {
		if (detailed)
			super.caseACspAbstractDefinition(node);
		else
			genDefinition(node, node.getName());
	}

	@Override
	public void caseACspChannel(ACspChannel node) {
		if (detailed)
			super.caseACspChannel(node);
		else
			genDefinition(node, node.getName());
	}
	
	public void inACspConstantCallExpr(ACspConstantCallExpr node) {
//		DefaultMutableTreeNode thisNode = new DefaultMutableTreeNode(node);
//		parents.push(thisNode);
		push(node, new Position(computeOffset(node.getName()))); // node.toString(),
	}
	
	
	/*
	 * As we come across non terminals, push them onto the stack
	 */
	public void defaultIn(Node node) {
		String name = node.getClass().getSimpleName();
		name = name.substring(4); // Remove "ACsp"

		String parentSimpleName = node.parent().getClass().getSimpleName();

		Position position = computePosition(node);
		if (name.startsWith("Base")) {
			// ACspBase...
			if (name.endsWith("List") && !parentSimpleName.endsWith("List")) {
				name = name.substring(4); // Remove "Base"
				push(node, position); // name
			}
		} else {
			// Not Base...
			if (name.startsWith("Linked")) {
				if (!parentSimpleName.endsWith("List")) {
					name = name.substring(6); // Remove "Linked"
					push(node, position); // name
				}
			} else {
				push(node, position); // name
			}
		}
	}

	/*
	 * As we leave a non terminal, it's parent is the node before it on the
	 * stack, so we pop it off and add it to that node
	 */
	public void defaultOut(Node node) {
		String name = node.getClass().getSimpleName();

		String parentSimpleName = node.parent().getClass().getSimpleName();

		if (!name.startsWith("ACspBase")) {
			name = name.substring(4); // Remove "ACsp"
			if (name.startsWith("Linked")) {
				if (!parentSimpleName.endsWith("List"))
					pop();
			} else {
				pop();
			}
		} else {
			if (name.endsWith("List") && !parentSimpleName.endsWith("List")) {
				pop();
			}
		}
	}	

	/*
	 * Terminals - our parent is always on the top of the stack, so we add
	 * ourselves to it
	 */
	public void defaultCase(Node node) {
//		DefaultMutableTreeNode thisNode = new DefaultMutableTreeNode(node);
//		parents.peek().add(thisNode);
		addToParent(new OutlineTreeNode(node, computePosition(node)));
	}

	public void caseEOF(EOF node) {
	}

	private OutlineTreeNode push(Node node, Position position) {
		OutlineTreeNode treeNode = new OutlineTreeNode(node, position);
		this.parents.push(treeNode);
		return treeNode;
	}

	private void pop() {
		OutlineTreeNode treeNode = this.parents.pop();
		treeNode.updateChildren();
		addToParent(treeNode);
	}

	private void addToParent(OutlineTreeNode treeNode) {
		OutlineTreeNode parentTreeNode = this.parents.peek();
		parentTreeNode.addChild(treeNode);
	}

	private Position computePosition(Node node) {
		TokenExtractor tokenExtractor = new TokenExtractor();
		node.apply(tokenExtractor);

		return new Position(computeOffset(tokenExtractor.getFirstToken()));
	}

	private void genDefinition(Node node, TName tName) {
		Position position = new Position(computeOffset(tName), tName.getText().length());

		try {
			document.addPosition(OutlineContentProvider.SEGMENTS, position);
		} catch (BadLocationException e) {
			e.printStackTrace();
		} catch (BadPositionCategoryException e) {
			e.printStackTrace();
		}

		addToParent(new OutlineTreeNode(node, position));
	}

	private int computeOffset(Token token) {
		try {
			return document.getLineOffset(token.getLine() - 1) + token.getPos() - 1;
		} catch (BadLocationException e) {
			e.printStackTrace();
			return 0;
		}
	}
}