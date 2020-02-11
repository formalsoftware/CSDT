package simulinkeditor;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IStatusLineManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.editors.text.TextEditorActionContributor;
import org.eclipse.ui.ide.IDE;

/**
 * Manages the installation/deinstallation of global actions for CSP editor.
 * Responsible for the redirection of global actions to the active editor.
 */
public class SimulinkFileEditorContributor extends TextEditorActionContributor {

	private Action sampleAction;

	/**
	 * Creates a multi-page contributor.
	 */
	public SimulinkFileEditorContributor() {
		createActions();
	}

	private void createActions() {
		sampleAction = new Action("Sample Action") {
			public void run() {
				MessageDialog.openInformation(null, "CspSuite Plug-in", "Sample Action Executed");
			}
		};
		sampleAction.setToolTipText("Sample Action tool tip");
		sampleAction.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(IDE.SharedImages.IMG_OBJS_TASK_TSK));
	}

	public void init(IActionBars bars) {
		super.init(bars);
	}

	// /**
	// * Returns the action registed with the given text editor.
	// * @return IAction or null if editor is null.
	// */
	// protected IAction getAction(ITextEditor editor, String actionID) {
	// return (editor == null ? null : editor.getAction(actionID));
	// }

	// /* (non-JavaDoc)
	// * Method declared in AbstractMultiPageEditorActionBarContributor.
	// */
	// public void setActivePage(IEditorPart part) {
	// IActionBars actionBars = getActionBars();
	//
	// if (actionBars != null) {
	// ITextEditor editor = (part instanceof ITextEditor) ? (ITextEditor) part :
	// null;
	//
	// actionBars.setGlobalActionHandler(
	// ActionFactory.DELETE.getId(),
	// getAction(editor, ITextEditorActionConstants.DELETE));
	// actionBars.setGlobalActionHandler(
	// ActionFactory.UNDO.getId(),
	// getAction(editor, ITextEditorActionConstants.UNDO));
	// actionBars.setGlobalActionHandler(
	// ActionFactory.REDO.getId(),
	// getAction(editor, ITextEditorActionConstants.REDO));
	// actionBars.setGlobalActionHandler(
	// ActionFactory.CUT.getId(),
	// getAction(editor, ITextEditorActionConstants.CUT));
	// actionBars.setGlobalActionHandler(
	// ActionFactory.COPY.getId(),
	// getAction(editor, ITextEditorActionConstants.COPY));
	// actionBars.setGlobalActionHandler(
	// ActionFactory.PASTE.getId(),
	// getAction(editor, ITextEditorActionConstants.PASTE));
	// actionBars.setGlobalActionHandler(
	// ActionFactory.SELECT_ALL.getId(),
	// getAction(editor, ITextEditorActionConstants.SELECT_ALL));
	// actionBars.setGlobalActionHandler(
	// ActionFactory.FIND.getId(),
	// getAction(editor, ITextEditorActionConstants.FIND));
	// actionBars.setGlobalActionHandler(
	// IDEActionFactory.BOOKMARK.getId(),
	// getAction(editor, IDEActionFactory.BOOKMARK.getId()));
	// actionBars.updateActionBars();
	// }
	// }

	@Override
	public void contributeToMenu(IMenuManager manager) {
		super.contributeToMenu(manager);

		IMenuManager menu = new MenuManager("Editor &Menu");
		manager.prependToGroup(IWorkbenchActionConstants.MB_ADDITIONS, menu);
		menu.add(sampleAction);
	}

	@Override
	public void contributeToToolBar(IToolBarManager manager) {
		super.contributeToToolBar(manager);

		manager.add(new Separator());
		manager.add(sampleAction);
	}

	@Override
	public void contributeToStatusLine(IStatusLineManager statusLineManager) {
		super.contributeToStatusLine(statusLineManager);
	}

}
