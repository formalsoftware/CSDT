package simulinkeditor.outline;

//falta os equivalentes a esses
import lmf.formula.csp.node.ACspAbstractDefinition;
import lmf.formula.csp.node.ACspChannel;
import lmf.formula.csp.node.ACspConstantDefinition;
import lmf.formula.csp.node.ACspDatatypeDefinition;
import lmf.formula.csp.node.ACspFunctionDefinition;
import lmf.formula.csp.node.ACspNametypeDefinition;
import lmf.formula.csp.node.ACspProcessDefinition;
import lmf.formula.csp.node.ACspSubtypeDefinition;
import lmf.formula.csp.node.Start;

import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.swt.graphics.Image;

//import com.fware.cspdt.cspm.editor.CspMEditorPlugin;
import simulinkeditor.SimulinkEditorPlugin;

class OutlineTreeLabelProvider extends LabelProvider {

	@Override
	public String getText(Object element) {
		String ret = null;
		if (element instanceof OutlineTreeNode) {
			OutlineTreeNode treeNode = (OutlineTreeNode) element;
			Object obj = (Object) treeNode.getValue();
			if (obj instanceof Start) {
				ret = "Mdl Specification";
			} else if (obj instanceof ACspAbstractDefinition) {
				ACspAbstractDefinition node2 = (ACspAbstractDefinition) obj;
				ret = node2.getName().getText();
			} else if (obj instanceof ACspFunctionDefinition) {
				ACspFunctionDefinition node2 = (ACspFunctionDefinition) obj;
				ret = node2.getName().getText();
				ret += ": " + node2.getParameters() + node2.getCspType();
				// CspFunctionType funType = (CspFunctionType)
				// node2.getCspType();
				// ret += "(" + funType.getParametersTypes();
				// ret += "): " + funType.getReturnType();
			} else if (obj instanceof ACspConstantDefinition) {
				ACspConstantDefinition node2 = (ACspConstantDefinition) obj;
				ret = node2.getName().getText();
				ret += ": " + node2.getCspType();
			} else if (obj instanceof ACspDatatypeDefinition) {
				ACspDatatypeDefinition node2 = (ACspDatatypeDefinition) obj;
				ret = node2.getName().getText();
			} else if (obj instanceof ACspSubtypeDefinition) {
				ACspSubtypeDefinition node2 = (ACspSubtypeDefinition) obj;
				ret = node2.getName().getText();
			} else if (obj instanceof ACspNametypeDefinition) {
				ACspNametypeDefinition node2 = (ACspNametypeDefinition) obj;
				ret = node2.getName().getText();
			} else if (obj instanceof ACspChannel) {
				ACspChannel node2 = (ACspChannel) obj;
				ret = node2.getName().getText();
			} else if (obj instanceof ACspProcessDefinition) {
				ACspProcessDefinition node2 = (ACspProcessDefinition) obj;
				ret = node2.getName().getText();
				if (node2.getParameters() != null) {
					ret += "(...)";
				}
			}
		} else if (element instanceof TreeNode) {
			ret = (String) ((TreeNode) element).getValue();
		}

		return (ret != null) ? ret : super.getText(element);
	}

	@Override
	public Image getImage(Object element) {
		Image ret = null;

		if (element instanceof OutlineTreeNode) {
			OutlineTreeNode treeNode = (OutlineTreeNode) element;
			Object obj = (Object) treeNode.getValue();
			if (obj instanceof Start) {
				ret = SimulinkEditorPlugin.getImage("spec.png");
			} else if (obj instanceof ACspAbstractDefinition) {
				ret = SimulinkEditorPlugin.getImage("unknown.png");
			} else if (obj instanceof ACspFunctionDefinition) {
				ret = SimulinkEditorPlugin.getImage("function.png");
			} else if (obj instanceof ACspConstantDefinition) {
				ret = SimulinkEditorPlugin.getImage("const.png");
			} else if (obj instanceof ACspDatatypeDefinition) {
				ret = SimulinkEditorPlugin.getImage("datatype.png");
			} else if (obj instanceof ACspSubtypeDefinition) {
				ret = SimulinkEditorPlugin.getImage("subtype.png");
			} else if (obj instanceof ACspNametypeDefinition) {
				ret = SimulinkEditorPlugin.getImage("nametype.png");
			} else if (obj instanceof ACspChannel) {
				ret = SimulinkEditorPlugin.getImage("channel.png");
			} else if (obj instanceof ACspProcessDefinition) {
				ret = SimulinkEditorPlugin.getImage("process.png");
			}
		}

		return (ret != null) ? ret : super.getImage(element);
	}
}