package com.formallab.simulink.mdl;

import java.util.Arrays;
import java.util.Set;


public class MdlLibrary extends MdlAbstractSystemOwner {

	public MdlLibrary(String name) {
		this(name, new MdlSystem(name));
	}

	public MdlLibrary(String name, MdlSystem system) {
		super("Library", name);
    	assert system != null;
		this.system = system;
	}

	public MdlBlock getBlock(String path) {
		if (path != null) {
			MdlBlock ret = this.system.getBlock(path);
			if (ret != null) {
				return ret;
			}
			
			String[] namespaces = extractNamespaces(path);

			int len = namespaces.length - 1;
			String name = namespaces[len];
			if (namespaces.length > 1) {
				MdlLibrary lib = getSubLibrary(Arrays.copyOf(namespaces, len));
				return lib.getBlock(name);
			}
			return this.system.getBlock(name);
		}
		return null;
	}

	private String[] extractNamespaces(String path) {
		path = path.replaceAll("//", "{"); // BUGFIX
		String[] namespaces = path.split("/");
		for (int i = 0; i < namespaces.length; i++) {
			namespaces[i] = namespaces[i].replaceAll("\\{", "/");
		}
		return namespaces;
	}

	private MdlLibrary getSubLibrary(String... namespaces) {
		MdlLibrary lib = this;
		Set<MdlLibrary> subLibraries;
		for (int i = 0; i < namespaces.length; i++) {
			subLibraries = lib.system.listEntries("Library");
			for (MdlLibrary l : subLibraries) {
				if (l.getName().equals(namespaces[i])) {
					lib = l;
					break;
				}
			}
		}
		return lib;
	}

	public MdlLibrary addSubLibrary(MdlLibrary subLibrary) {
		this.system.addEntry(subLibrary);
		return subLibrary;
	}

	public MdlBlock addBlock(MdlBlock block) {
		this.system.addBlock(block);
		return block;
	}

}
