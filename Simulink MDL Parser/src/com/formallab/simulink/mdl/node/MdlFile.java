package com.formallab.simulink.mdl.node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class MdlFile implements MdlElement, Iterable<MdlSection> {

	private String fileName;

	private List<MdlSection> sections;

	public MdlFile(String fileName) {
		this.fileName = fileName;
		this.sections = new ArrayList<MdlSection>(2);
	}

	public String getFileName() {
		return fileName;
	}

	public List<MdlSection> list() {
		return Collections.unmodifiableList(sections);
	}

	public List<MdlSection> list(String sectionName) {
		List<MdlSection> ret = new ArrayList<MdlSection>();
		for (MdlSection s : this) {
			if (s.nodeName.equals(sectionName)) {
				ret.add(s);
			}
		}
		return ret;
	}

	public void add(MdlSection section) {
		assert section != null;
		this.sections.add(section);
	}

	public void remove(String sectionName) {
		assert sectionName != null;
		this.sections.remove(sectionName);
	}

	public MdlSection get(int i) {
		return sections.get(i);
	}

	@Override
	public Iterator<MdlSection> iterator() {
		return sections.listIterator();
	}
	
	@Override
	public String toString() {
		return sections.toString();
	}

}