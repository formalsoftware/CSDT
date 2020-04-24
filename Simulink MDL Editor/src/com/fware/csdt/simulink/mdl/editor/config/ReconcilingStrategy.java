package com.fware.csdt.simulink.mdl.editor.config;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.reconciler.DirtyRegion;
import org.eclipse.jface.text.reconciler.IReconcilingStrategy;

import com.fware.csdt.simulink.mdl.editor.SimulinkEditor;;

public class ReconcilingStrategy implements IReconcilingStrategy {

	private SimulinkEditor editor;

	private IDocument fDocument;

	/** holds the calculated positions */
	protected final List<Position> fPositions = new ArrayList<Position>();

	/** The offset of the next character to be read */
	protected int fOffset;

	/** The end offset of the range to be scanned */
	protected int fRangeEnd;

	public ReconcilingStrategy(SimulinkEditor editor) {
		this.editor = editor;
	}

	public SimulinkEditor getEditor() {
		return editor;
	}

	public void initialReconcile() {
		fOffset = 0;
		fRangeEnd = fDocument.getLength();
		calculatePositions();
	}

	//@Override
	public void setDocument(IDocument document) {
		this.fDocument = document;
	}

	//@Override
	public void reconcile(DirtyRegion arg0, IRegion arg1) {
		initialReconcile();
	}

	//@Override
	public void reconcile(IRegion arg0) {
		initialReconcile();
	}

	private void calculatePositions() {
		// TODO calculatePositions()
	}
}
