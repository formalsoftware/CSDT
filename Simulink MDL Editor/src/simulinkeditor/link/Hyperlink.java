package simulinkeditor.link;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

//falta o ref
import simulinkeditor.SimulinkRef;
import simulinkeditor.SimulinkEditor;

public class Hyperlink implements IHyperlink {

	private SimulinkRef definitionRef;
	private SimulinkEditor editor;
	private IRegion region;

	public Hyperlink(SimulinkRef definitionRef, SimulinkEditor editor, IRegion region) {
		this.definitionRef = definitionRef;
		this.editor = editor;
		this.region = region;
	}	
	
	public Hyperlink(IRegion region) {
		this.region = region;
	}	

	public void open() {
		IDocument document = this.editor.getDocumentProvider().getDocument(this.editor.getEditorInput());

		if (this.definitionRef != null) {
			try {
				int beginElementOffset = document.getLineOffset(definitionRef.getLine() - 1) + definitionRef.getPos();
				if (beginElementOffset - 1 >= 0) {
					beginElementOffset--;
				}
				this.editor.selectAndReveal(beginElementOffset, definitionRef.getText().length());
			} catch (BadLocationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public IRegion getHyperlinkRegion() {
		return region;
	}

	public String getHyperlinkText() {
		return this.definitionRef.getText();
	}
	
	public String getTypeLabel() {
		return null;	
	}

	public String getText() {
		return this.definitionRef.getText();
	}
	
}