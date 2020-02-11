package simulinkeditor.outline;

import java.util.ArrayList;
import java.util.List;

//import lmf.formula.csp.node.Node;
import com.formallab.simulink.mdl.node.MdlNode;

import org.eclipse.jface.text.Position;
import org.eclipse.jface.viewers.TreeNode;

public class OutlineTreeNode extends TreeNode {

	public final Position position;

	public final List<OutlineTreeNode> childrenBackup;

	public OutlineTreeNode(MdlNode node, Position position) {
		super(node);

		this.position = position;
		this.childrenBackup = new ArrayList<OutlineTreeNode>();
	}

	public void addChild(OutlineTreeNode child) {
		childrenBackup.add(child);
	}

	void updateChildren() {
		this.setChildren(childrenBackup.toArray(new OutlineTreeNode[childrenBackup.size()]));
	}
}