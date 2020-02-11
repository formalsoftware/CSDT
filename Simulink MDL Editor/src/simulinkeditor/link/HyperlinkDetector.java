package simulinkeditor.link;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.hyperlink.IHyperlink;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
//import org.eclipse.ui.IFileEditorInput;

//import com.fware.cspdt.cspm.core.model.CspMModel;
import com.formallab.simulink.mdl.MdlModel;
import simulinkeditor.SimulinkRef;
//falta o ref e o objeto ast em model
import simulinkeditor.SimulinkEditor;

public class HyperlinkDetector implements IHyperlinkDetector {

	private SimulinkEditor editor;
	private MdlModel info;
	public HyperlinkDetector(SimulinkEditor editor) {
		this.editor = editor;
	}

	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		IHyperlink[] arrayLinks;
		canShowMultipleHyperlinks = true;
		IDocument document = textViewer.getDocument();
		
		info = editor.parse();
		if (info != null) {
			info.ast.apply(new RefExtractor(document, info));
			
			IRegion selection = getSelectionRegion(region, document, info.tocRefs);
			IHyperlink hyperLink = createHyperLink(document, selection, info.tocRefs);
			if (hyperLink == null) {
				return null;
			}
			arrayLinks = new IHyperlink[] { hyperLink };
		} else {
			arrayLinks = new IHyperlink[0];
		}

		return arrayLinks;
	}

	private IRegion getSelectionRegion(IRegion region, IDocument document, LinkedList<CspMRef> links) {
		IRegion retRegion = null;

		try {
			int currentOffset = region.getOffset();
			int beginRefOffset = 0;
			IRegion lineRegion = document.getLineInformationOfOffset(currentOffset);
			String candidate = document.get(lineRegion.getOffset(), lineRegion.getLength());
			int indexNovo = 0;
					
			for (SimulinkRef ref : links) {
				indexNovo = indexRefs(candidate, ref.getText(), lineRegion.getOffset(), currentOffset);			
				if (indexNovo != -1) {					
					IRegion targetRegion = new Region(lineRegion.getOffset() + indexNovo, ref.getText().length());
					beginRefOffset = document.getLineOffset(ref.getLine() - 1) + ref.getPos();
					if (targetRegion.getOffset() <= currentOffset && (targetRegion.getOffset() + targetRegion.getLength()) > currentOffset) {
						if (beginRefOffset - 1 >= 0) {
							beginRefOffset--;
						}
						retRegion = targetRegion;
						break;
					}
				}
			}
		} catch (org.eclipse.jface.text.BadLocationException e) {
			e.printStackTrace();
		}
		return retRegion;
	}

	private int indexRefs (String candidate, String ref, int linhaOffset, int currentOffset) {
		int indexNovo = -1;
		int limite = 0;
		List<Integer> listaRefs = new ArrayList<Integer>();
		int index = candidate.indexOf(ref);
			
		while (index >= 0) {
			if (candidate.substring(index, index+ref.length()).equals(ref)) {
				listaRefs.add(index);
			}
			index = candidate.indexOf(ref, index+1);	
		}
		for (int indexArray : listaRefs) {
			limite = linhaOffset + indexArray;
			if (currentOffset >= limite && currentOffset < (limite + ref.length())) {
				indexNovo = indexArray;
				break;
			}
		}
		return indexNovo;
	}
	
	private String getTextFromRegion(IRegion region, IDocument document) {
		if (region == null) {
			return null;
		}

		String text = null;
		try {
			text = document.get(region.getOffset(), region.getLength());
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
		return text;
	}

	// PEGA O CONTEUDO DA REGION E COMPARA COM AS DEFINIÇÕES DO DOCUMENTO
	private IHyperlink createHyperLink(IDocument document, IRegion region, LinkedList<CspMRef> refs) {
		IHyperlink link = null;
		String key = this.getTextFromRegion(region, document);
		if (key != null) {
			key = key.trim();

			for (SimulinkRef ref : refs) {
				if (key.equals(ref.getText())) {
					link = new Hyperlink(ref, this.editor, region);
					break;
				}
			}
		}
		return link;
	}
}