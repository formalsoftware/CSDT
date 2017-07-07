package com.formallab.simulink.mdl.node;

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class MdlSection extends MdlNode implements Iterable<MdlNode> {

	protected final Set<MdlNode> entries;

//	private int name_max_length = 0; /* Computed differentially. */

	public MdlSection(String nodeName) {
		this(null, nodeName);
	}

	public MdlSection(MdlNode parent, String nodeName) {
		super(parent, nodeName);

		entries = new LinkedHashSet<MdlNode>();
	}

//	public int getNameMaxLength() {
//		return name_max_length;
//	}

	public void addEntry(MdlNode node) {
		assert node != null
			&& (node.getParent() == null || node.getParent() == this);

		entries.add(node);
		node.setParent(this);

//		if (node instanceof MdlField) {
//			String name = ((MdlField) node).getNodeName();
//			if (name.length() > name_max_length) {
//				name_max_length = name.length();
//			}
//		}
	}

	@Override
	public boolean hasChildren() {
		return true;
	}

	public Iterator<MdlNode> iterator() {
		return entries.iterator();
	}

	public void removeEntry(MdlNode node) {
		if (entries.remove(node)) {
			node.setParent(null);
		}
//		recalcNameMaxLength();
	}

//	protected void recalcNameMaxLength() {
//		name_max_length = 0;
//		for (MdlNode node : this) {
//			if (node instanceof MdlField) {
//				String name = ((MdlField) node).getNodeName();
//				if (name.length() > name_max_length) {
//					name_max_length = name.length();
//				}
//			}
//		}
//	}

	public boolean containsEntry(String name) {
		return getEntry(name) != null;
	}

	@SuppressWarnings("unchecked")
	public <T extends MdlElement> T getEntry(String nodeName) {
		nodeName = nodeName.trim();
		for (MdlNode node : entries) {
			if (node.nodeName.equals(nodeName)) {
				return (T) node;
			}
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T extends MdlElement> Set<T> listEntries(String nodeName) {
		Set<T> nodes = new LinkedHashSet<T>();
		for (MdlNode node : entries) {
			if (node.nodeName.equals(nodeName)) {
				nodes.add((T) node);
			}
		}
		return nodes;
	}

	@SuppressWarnings("unchecked")
	public <T extends MdlElement> T removeEntry(String name) {
		T t = null;
		name = name.trim();
		for (MdlNode node : entries) {
			if (node.nodeName.equals(name)) {
				t = (T) node;
				break;
			}
		}
		entries.remove(t);
		return t;
	}

	@SuppressWarnings("unchecked")
	public <T extends MdlElement> Set<T> removeEntries(String nodeName) {
		Set<T> nodes = new LinkedHashSet<T>();
		Iterator<MdlNode> iterator = entries.iterator();
		MdlNode mdlNode;
		while (iterator.hasNext()) {
			mdlNode = (MdlNode) iterator.next();
			if (mdlNode.nodeName.equals(nodeName)) {
				nodes.add((T) mdlNode);
				iterator.remove();
			}
		}
		return nodes;
	}

	public void copyFieldsTo(MdlSection dst) {
    	if (dst != null) {
    		for (MdlElement node : this) {
    			if (node instanceof MdlField) {
    				dst.addEntry((MdlField) node);
    			}
    		}
    	}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(nodeName);
		sb.append("{\n");
		for (MdlElement n : entries) {
			sb.append(n).append('\n');
		}
		return sb.append("\n}").toString();
	}

	public void introduce(List<MdlField> fields) {
		for (MdlField mdlField : fields) {
			if (getEntry(mdlField.nodeName) == null) {
				addEntry(mdlField);
			}
		}
	}

	public int cutPortNumber() {
		MdlElement node = getEntry("Port");
		String portNum;
		if (node instanceof MdlSection) {
			MdlSection section = (MdlSection) node;
			portNum = section.cutFieldValue("PortNumber");
		} else {
	        portNum = ((MdlField) node).getValue();
		}

        if (portNum == null) {
            return 1;
        } else {
            return Integer.parseInt(portNum);
        }
    }

	public String cutFieldValue(String name) {
        MdlField field = removeEntry(name);
        if (field == null) {
            return null;
        }
		return field.getValue().replaceAll("\"", "");
    }

	public int cutInt(String name) {
		String fieldValue = cutFieldValue(name);
		if (fieldValue == null) {
			return -1;
		}
		return Integer.parseInt(fieldValue);
	}

	public MdlField cutSampleTime() {
		MdlField attST = removeEntry("SampleTime");
        if (attST != null) return attST;

        attST = removeEntry("SystemSampleTime");
    	if (attST != null) return attST;

    	return removeEntry("SampleTimeMode");
	}
}