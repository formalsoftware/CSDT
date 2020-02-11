package simulinkeditor.config;

//import java.lang.reflect.Array;
//import java.text.MessageFormat;
import java.util.*;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.contentassist.CompletionProposal;
//import org.eclipse.jface.text.contentassist.ContextInformation;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.jface.text.contentassist.IContextInformation;
import org.eclipse.jface.text.contentassist.IContextInformationValidator;

//import com.fware.cspdt.cspm.core.model.CspMModel;
import com.formallab.simulink.mdl.MdlModel;
//import com.fware.cspdt.cspm.core.model.CspMRef;
import simulinkeditor.SimulinkRef;
//falta esse Ref e o model ter um objeto ast
//import com.formallab.simulink.mdl.MdlReferenceBlock;
import simulinkeditor.SimulinkEditor;
import simulinkeditor.link.RefExtractor;

public class ProcessCompletionProcessor implements IContentAssistProcessor {
	private final IContextInformation[] NO_CONTEXTS = {};
	private final char[] PROPOSAL_ACTIVATION_CHARS = { 's', 'f', 'p', 'n', 'm', '.'};
	private final ICompletionProposal[] NO_COMPLETIONS = {};	
	private Keywords[] proposals = Keywords.values();
	private SimulinkEditor editor;
	private MdlModel info;
	
	
	public ProcessCompletionProcessor (SimulinkEditor editor) {
		this.editor = editor;
	}
	
	public ICompletionProposal[] computeCompletionProposals(ITextViewer viewer, int offset) {
		info = editor.parse();
		
		if (info != null) {
			try {
				IDocument document = viewer.getDocument();
				info.ast.apply(new RefExtractor(document, info));
				String prefix = lastWord(document, offset);				
			    List<ICompletionProposal> completionProposals = new ArrayList<ICompletionProposal>();
			    
			    for (SimulinkRef ref : info.tocRefs) {
			    	if (ref.getText().startsWith(prefix)) {
			    		completionProposals.add(new CompletionProposal(ref.getText(), (offset - prefix.length()), prefix.length(), ref.getText().length()));
			    	}
			    }
			    
			    for (Keywords keyword : proposals) {			    	
			        if (keyword.getValue().startsWith(prefix)) {
			        	completionProposals.add(new CompletionProposal(keyword.getValue(), (offset - prefix.length()), prefix.length(), keyword.getValue().length()));
			        }
			    }
			    
			    Collections.sort(completionProposals, new KeywordsComparator());		    
			    return completionProposals.toArray(new ICompletionProposal[completionProposals.size()]);
			    
			} catch (Exception e) {
				// ... log the exception ...
				return NO_COMPLETIONS;
			}
		} else {
			return NO_COMPLETIONS;
		}
	}

	private String lastWord(IDocument doc, int offset) {
		try {
			for (int n = offset - 1; n >= 0; n--) {
				char c = doc.getChar(n);
				if (!Character.isJavaIdentifierPart(c))
					return doc.get(n + 1, offset - n - 1);
			}
		} catch (BadLocationException e) {
			// ... log the exception ...
		}
		return "";
	}

//	private String lastIndent(IDocument doc, int offset) {
//		try {
//			int start = offset - 1;
//			while (start >= 0 && doc.getChar(start) != '\n')
//				start--;
//			int end = start;
//			while (end < offset && Character.isSpaceChar(doc.getChar(end)))
//				end++;
//			return doc.get(start + 1, end - start - 1);
//		} catch (BadLocationException e) {
//			e.printStackTrace();
//		}
//		return "";
//	}

	public IContextInformation[] computeContextInformation(ITextViewer viewer, int offset) {
		return NO_CONTEXTS;
	}

	public char[] getCompletionProposalAutoActivationCharacters() {
		return PROPOSAL_ACTIVATION_CHARS;
	}

	// ... remaining methods are optional ...
	@Override
	public char[] getContextInformationAutoActivationCharacters() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getErrorMessage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContextInformationValidator getContextInformationValidator() {
		// TODO Auto-generated method stub
		return null;
	}
	
	static class KeywordsComparator implements Comparator<ICompletionProposal>
	 {
	     public int compare(ICompletionProposal k1, ICompletionProposal k2)
	     {
	         return k1.getDisplayString().compareTo(k2.getDisplayString());
	     }
	 }
	
}