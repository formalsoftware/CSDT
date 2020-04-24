package com.fware.csdt.simulink.mdl.editor.outline;

//import org.eclipse.core.resources.IFile;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.views.contentoutline.ContentOutlinePage;

import com.fware.csdt.simulink.mdl.editor.SimulinkEditor;

public class OutlinePage extends ContentOutlinePage {

	private SimulinkEditor editor;	
	public OutlinePage(SimulinkEditor editor) {
		this.editor = editor;
	}

	public void createControl(Composite parent) {
		super.createControl(parent);

		// PlatformUI.getWorkbench().getHelpSystem().setHelp(getControl(),
		// ICspMConstants.CONTENT_OUTLINE_PAGE_CONTEXT);

		TreeViewer viewer = getTreeViewer();
		viewer.setContentProvider(new OutlineContentProvider(editor));
		viewer.setLabelProvider(new OutlineTreeLabelProvider());
		viewer.addSelectionChangedListener(this);
		//viewer.setInput();

		// TODO createContextMenu(viewer);

		// TODO addActions();

		// initDragAndDrop();

		update();
	}

	// private void initDragAndDrop() {
	// //TODO initDragAndDrop
	//
	// int ops = DND.DROP_COPY | DND.DROP_MOVE;
	// Transfer[] transfers = new Transfer[] { TextTransfer.getInstance(),
	// PluginTransfer.getInstance() };
	// getTreeViewer().addDragSupport(ops, transfers,
	// new ReadmeContentOutlineDragListener(this));
	//
	// }

	public void update() {
		IEditorInput editorInput = editor.getEditorInput();
		TreeViewer viewer = getTreeViewer();
		
		if (viewer != null) {			
			Control control = viewer.getControl();
			if (control != null && !control.isDisposed()) {
				control.setRedraw(false);
				viewer.setInput(editorInput);
				viewer.expandAll();
				control.setRedraw(true);
			}
		}
	}

	public void selectionChanged(SelectionChangedEvent event) {
		super.selectionChanged(event);

		ISelection selection = event.getSelection();
		if (selection.isEmpty()) {
			editor.resetHighlightRange();
		} else {
			OutlineTreeNode treeNode = (OutlineTreeNode) ((IStructuredSelection) selection).getFirstElement();
			Position position = treeNode.position;
			try {
				editor.setHighlightRange(position.offset, position.length, true);
			} catch (IllegalArgumentException x) {
				editor.resetHighlightRange();
			}
		}
	}
}