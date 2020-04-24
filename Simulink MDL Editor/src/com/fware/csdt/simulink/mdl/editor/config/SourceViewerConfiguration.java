package com.fware.csdt.simulink.mdl.editor.config;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.preference.PreferenceConverter;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
//import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContentAssistant;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlinkPresenter;
import org.eclipse.jface.text.hyperlink.URLHyperlinkDetector;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.reconciler.IReconciler;
import org.eclipse.jface.text.reconciler.MonoReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
//import org.eclipse.jface.text.rules.ITokenScanner;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.ui.editors.text.TextSourceViewerConfiguration;

import com.fware.csdt.simulink.mdl.editor.SimulinkEditor;
import com.fware.csdt.simulink.mdl.editor.SimulinkEditorPlugin;
import com.fware.csdt.simulink.mdl.editor.link.HyperlinkDetector;
import com.fware.csdt.simulink.mdl.editor.link.HyperlinkPresenter;
import com.fware.csdt.simulink.mdl.editor.partition.PartitionScanner;
import com.fware.csdt.simulink.mdl.editor.preferences.PreferenceConstants;
import com.fware.csdt.simulink.mdl.editor.scanner.Scanner;

public class SourceViewerConfiguration extends TextSourceViewerConfiguration {

	private SimulinkEditor editor;
	private DoubleClickStrategy doubleClickStrategy;
	private Scanner scanner;
	private ColorManager colorManager;
	private ContentAssistant contentAssistant;
	private NonRuleBasedDamagerRepairer nonRuledDamagerRep;
	private DefaultDamagerRepairer defaultDamagerRep;	
	private IPreferenceStore prefs;
	private PreferenceConstants colorConstant;
	private ProcessCompletionProcessor processCompletion;

	public SourceViewerConfiguration(SimulinkEditor editor, ColorManager colorManager) {
		this.editor = editor;
		this.colorManager = colorManager;
		
		contentAssistant = new ContentAssistant();
	    contentAssistant.setContentAssistProcessor (new ProcessCompletionProcessor(editor), IDocument.DEFAULT_CONTENT_TYPE);
	    contentAssistant.enableAutoActivation(true);
	    contentAssistant.setContextInformationPopupOrientation(IContentAssistant.CONTEXT_INFO_BELOW);
	}

	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return PartitionScanner.PARTITION_TYPES;
	}

	public IReconciler getReconciler(ISourceViewer sourceViewer) {
		ReconcilingStrategy strategy = new ReconcilingStrategy(editor);
		return new MonoReconciler(strategy, false);
	}

	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		prefs = SimulinkEditorPlugin.getDefault().getPreferenceStore();
		PresentationReconciler presentationRec = new PresentationReconciler();		
		
		defaultDamagerRep = new DefaultDamagerRepairer(getScanner());
		presentationRec.setDamager(defaultDamagerRep, PartitionScanner.CSPM_DEFAULT_CONTENT_TYPE);
		presentationRec.setRepairer(defaultDamagerRep, PartitionScanner.CSPM_DEFAULT_CONTENT_TYPE);
			
		nonRuledDamagerRep = new NonRuleBasedDamagerRepairer(new TextAttribute(getPreferedColor(prefs, PreferenceConstants.COLOR_COMMENT)));
		presentationRec.setDamager(nonRuledDamagerRep, PartitionScanner.CSPM_COMMENT_CONTENT_TYPE);
		presentationRec.setRepairer(nonRuledDamagerRep, PartitionScanner.CSPM_COMMENT_CONTENT_TYPE);
		
		nonRuledDamagerRep = new NonRuleBasedDamagerRepairer(new TextAttribute(getPreferedColor(prefs, PreferenceConstants.COLOR_MULTILINE_COMMENT)));
		presentationRec.setDamager(nonRuledDamagerRep, PartitionScanner.CSPM_MULTILINE_COMMENT_CONTENT_TYPE);
		presentationRec.setRepairer(nonRuledDamagerRep, PartitionScanner.CSPM_MULTILINE_COMMENT_CONTENT_TYPE);
		
		return presentationRec;
	}
	
	public Color getPreferedColor(IPreferenceStore prefs, String id) {
		return colorManager.getColor(PreferenceConverter.getColor(prefs, id));
	}

	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null)
			doubleClickStrategy = new DoubleClickStrategy();
		return doubleClickStrategy;
	}

	protected Scanner getScanner() {
		if (scanner == null) {
			scanner = new Scanner(colorManager);

			//scanner.setDefaultReturnToken(new Token(new TextAttribute(getPreferedColor(prefs, CspMEditorPreferenceConstants.COLOR_DEFAULT))));
		}
		return scanner;
	}

	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer sourceViewer, String contentType) {
		IAutoEditStrategy strategy = (IDocument.DEFAULT_CONTENT_TYPE.equals(contentType) ? new AutoIndentStrategy()
				: new DefaultIndentLineAutoEditStrategy());
		return new IAutoEditStrategy[] { strategy };
	}

	public IContentAssistant getContentAssistant(ISourceViewer sourceViewer) {		
		
		contentAssistant.setInformationControlCreator(getInformationControlCreator(sourceViewer));		

		return contentAssistant;
	}
	
	 @Override
	 public HyperlinkDetector[] getHyperlinkDetectors(ISourceViewer sourceViewer) {
		 HyperlinkDetector[] detectors = new HyperlinkDetector[] {
			 new HyperlinkDetector(editor)
			 //new URLHyperlinkDetector()
		 };
		 return detectors;
	 }
	 
	public HyperlinkPresenter getHyperlinkPresenter(ISourceViewer sourceViewer) {
		return new HyperlinkPresenter(new RGB(0, 0, 255), editor);
	}
}