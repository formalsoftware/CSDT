package com.fware.csdt.simulink.mdl.editor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.ui.editors.text.FileDocumentProvider;

import com.fware.csdt.simulink.mdl.editor.partition.Partitioner;

public class SimulinkDocumentProvider extends FileDocumentProvider {

	protected IDocument createDocument(Object element) throws CoreException {
		IDocument document = super.createDocument(element);

		if (document != null) {
			IDocumentPartitioner partitioner = new Partitioner();
			partitioner.connect(document);
			document.setDocumentPartitioner(partitioner);
		}

		return document;
	}
}
