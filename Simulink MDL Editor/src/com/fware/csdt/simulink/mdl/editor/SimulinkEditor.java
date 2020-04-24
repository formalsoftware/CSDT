package com.fware.csdt.simulink.mdl.editor;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.rules.EndOfLineRule;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.RuleBasedScanner;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.editors.text.TextEditor;
import org.eclipse.ui.texteditor.ITextEditor;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;

//import com.fware.cspdt.cspm.core.model.CspMModel;
import com.formallab.simulink.mdl.MdlModel;
//import com.fware.cspdt.cspm.core.parser.CspMParser;
import com.formallab.simulink.mdl.parser.MdlParser;
//import com.fware.cspdt.cspm.core.parser.CspMParserException;
import com.formallab.simulink.mdl.parser.MdlParserException;
import com.fware.csdt.simulink.mdl.editor.config.ColorConstants;
import com.fware.csdt.simulink.mdl.editor.config.ColorManager;
import com.fware.csdt.simulink.mdl.editor.config.SourceViewerConfiguration;
import com.fware.csdt.simulink.mdl.editor.marker.ErrorHandler;
import com.fware.csdt.simulink.mdl.editor.outline.OutlinePage;
import com.fware.csdt.simulink.mdl.editor.partition.PartitionScanner;
import com.fware.csdt.simulink.mdl.editor.scanner.Scanner;
import com.formallab.simulink.mdl.lexer.MdlLexer;

//falta o Start e o analyser exception e os ajustes para o parser ter metodo getInfo

/**
 * @author Cauanne e Gustavo 
 */
public class SimulinkEditor extends TextEditor implements IEditorPart {
	
	private static IPartitionTokenScanner simulinkPartitionScanner;
	private ColorManager colorManager;
	private ITokenScanner simulinkNotationScanner;
	private RuleBasedScanner simulinkCommentScanner;
	private RuleBasedScanner simulinkMultiLineCommentScanner;
	private OutlinePage fOutlinePage;
	private IEditorInput input;	
	private MdlParser parser;
	private IDocument doc;
	StyleRange underLineRange = null;
	Start startNode = null;

	private ErrorHandler markingErrorHandler;

	/**
	 * Constructor.
	 */
	public SimulinkEditor() {
	}

	/**
	 * @see org.eclipse.ui.editors.text.TextEditor#initializeEditor()
	 */
	protected void initializeEditor() {
		super.initializeEditor();

		setSourceViewerConfiguration(new SourceViewerConfiguration(this, getColorManager()));
	}

	public static IPartitionTokenScanner gePartitionScanner() {
		if (simulinkPartitionScanner == null) {
			simulinkPartitionScanner = new PartitionScanner();
		}
		return simulinkPartitionScanner;
	}

	public ColorManager getColorManager() {
		if (colorManager == null) {
			colorManager = new ColorManager();
		}
		return colorManager;
	}

	public ITokenScanner getCspMScanner() {
		if (simulinkNotationScanner == null) {
			simulinkNotationScanner = new Scanner(getColorManager());
		}
		return simulinkNotationScanner;
	}

	public ITokenScanner getCommentScanner() {
		if (simulinkCommentScanner == null) {
			simulinkCommentScanner = new RuleBasedScanner();

			// Token that defines how to color single line comments.
			IToken cspCommentToken = new Token(new TextAttribute(getColorManager().getColor(ColorConstants.COMMENT.getCor())));

			simulinkCommentScanner.setRules(new IRule[] { new EndOfLineRule("--", cspCommentToken) });
		}
		return simulinkCommentScanner;
	}

	public ITokenScanner getMultiLineCommentScanner() {
		if (simulinkMultiLineCommentScanner == null) {
			simulinkMultiLineCommentScanner = new RuleBasedScanner();

			// Token that defines how to color multi-line comments.
			IToken cspMultiLineCommentToken = new Token(new TextAttribute(getColorManager().getColor(ColorConstants.MULTILINE_COMMENT.getCor())));

			simulinkMultiLineCommentScanner.setRules(new IRule[] { new MultiLineRule("{-", "-}", cspMultiLineCommentToken) });
		}
		return simulinkMultiLineCommentScanner;
	}

	@SuppressWarnings("rawtypes")
	public Object getAdapter(Class required) {
		if (IContentOutlinePage.class.equals(required)) {
			return getOutlinePage();
		}

		return super.getAdapter(required);
	}

	private OutlinePage getOutlinePage() {
		if (fOutlinePage == null) {
			fOutlinePage = new OutlinePage(this);			
		}
		return fOutlinePage;
	}

	public IDocument getDocument() {
		return getSourceViewer().getDocument();
	}

	/**
	 * @see org.eclipse.ui.editors.text.TextEditor#dispose()
	 */
	public void dispose() {
		colorManager.dispose();
		simulinkNotationScanner = null;
		simulinkCommentScanner = null;
		simulinkMultiLineCommentScanner = null;

		super.dispose();
	}

	public ISourceViewer getViewer() {
		return getSourceViewer();
	}

	public StyleRange getUnderLineRange() {
		return underLineRange;
	}

	public void setUnderLineRange(StyleRange underLineRange) {
		this.underLineRange = underLineRange;
	}

	public MdlParser getParser() {
		MdlLexer lexer;
		return new MdlParser(lexer);
	}

	public MdlModel parse() {
		ErrorHandler markingErrorHandler = getErrorHandler();
		try {
			markingErrorHandler.removeExistingMarkers();			
			return this.getParser().getInfo(getInputFile(), markingErrorHandler);
		} catch (MdlParserException e) {
			SimulinkEditorPlugin.log(e.getMessage(), e);
		} catch (CspAnalyserException e) {
			SimulinkEditorPlugin.log(e.getMessage(), e);
		}
		return null;
	}

	public ErrorHandler getErrorHandler() {
		return markingErrorHandler != null ? markingErrorHandler
				: (markingErrorHandler = new ErrorHandler(this));
	}

	public void doRevertToSaved() {
		super.doRevertToSaved();
		updateOutline();
		//parse();
	}

	public void doSave(IProgressMonitor monitor) {
		super.doSave(monitor);
		updateOutline();
		//parse();
	}

	public void doSaveAs() {
		super.doSaveAs();
		updateOutline();
		//parse();
	}

	public void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		this.input = input;
		updateOutline();
		//parse();
	}

	private void updateOutline() {
		getOutlinePage().update();
	}

	public IEditorInput getInput() {
		return input;
	}

	protected IDocument getInputDocument() {
		IDocument document = getDocumentProvider().getDocument(input);
		return document;
	}

	public IFile getInputFile() {
		IFileEditorInput ife = (IFileEditorInput) input;
		IFile file = ife.getFile();
		return file;
	}
	
}