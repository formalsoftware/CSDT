package com.formallab.simulink;

import static com.formallab.simulink.mdl.SimulinkType.DOUBLE;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.formallab.simulink.libraries.Libraries;
import com.formallab.simulink.mdl.MdlAbstractSystemOwner;
import com.formallab.simulink.mdl.MdlBlock;
import com.formallab.simulink.mdl.MdlEnablePort;
import com.formallab.simulink.mdl.MdlFileReader;
import com.formallab.simulink.mdl.MdlInport;
import com.formallab.simulink.mdl.MdlLibrary;
import com.formallab.simulink.mdl.MdlModel;
import com.formallab.simulink.mdl.MdlOutport;
import com.formallab.simulink.mdl.MdlReferenceBlock;
import com.formallab.simulink.mdl.MdlSFunction;
import com.formallab.simulink.mdl.MdlSignal;
import com.formallab.simulink.mdl.MdlSignalBranch;
import com.formallab.simulink.mdl.MdlSubSystem;
import com.formallab.simulink.mdl.MdlSystem;
import com.formallab.simulink.mdl.MdlSystemOwner;
import com.formallab.simulink.mdl.MdlTimedSection;
import com.formallab.simulink.mdl.MdlTriggerPort;
import com.formallab.simulink.mdl.SimulinkType;
import com.formallab.simulink.mdl.lexer.MdlLexerException;
import com.formallab.simulink.mdl.node.MdlElement;
import com.formallab.simulink.mdl.node.MdlField;
import com.formallab.simulink.mdl.node.MdlFile;
import com.formallab.simulink.mdl.node.MdlSection;
import com.formallab.simulink.mdl.parser.MdlParserException;

public class MdlDOMFactory {

	private static String fileName;

	private static String path;

	private static MdlSystem mdlSystem;

	private static Map<String, MdlLibrary> linkedLibraries;

	private static Map<String, MdlBlock> linkedBlocks;

	private static List<MdlReferenceBlock> toBeLoaded;

	private static Hashtable<String, MdlDOMExtensionFactory> extensions;
	private static Hashtable<String, MdlDOMSFunctionExtensionFactory> sfunExtensions;

	static {
		extensions = new Hashtable<String, MdlDOMExtensionFactory>();
		sfunExtensions = new Hashtable<String, MdlDOMSFunctionExtensionFactory>();
	}

	public static void registerExtension(String sectionName, MdlDOMExtensionFactory extensionFactory) {
		extensions.put(sectionName, extensionFactory);
	}

	public static void registerSfunctionExtension(String functionName, MdlDOMSFunctionExtensionFactory extensionFactory) {
		sfunExtensions.put(functionName, extensionFactory);
	}

	public static MdlFile create(String inputfile) throws FileNotFoundException,
			MdlLexerException, MdlParserException {
		linkedLibraries = new HashMap<String, MdlLibrary>();
		linkedBlocks = new HashMap<String, MdlBlock>();
		return load(inputfile);
	}

	private static MdlFile load(String inputfile) throws FileNotFoundException,
			MdlLexerException, MdlParserException {
		File file = new File(inputfile);
		fileName = file.getName();
		path = file.getParent();
		
		List<MdlReferenceBlock> parentToBeLoaded = toBeLoaded;
		toBeLoaded = new ArrayList<MdlReferenceBlock>();

		System.out.println(file.getAbsolutePath());
		MdlFile mdlFile = MdlFileReader.open(file.getAbsolutePath());

		List<MdlSection> sections = mdlFile.list();

		mdlFile = new MdlFile(mdlFile.getFileName());
		for (MdlSection section : sections) {
			mdlFile.add(specialise(section));
		}
		
		MdlSection mdlSection = mdlFile.get(0);
		if (mdlSection instanceof MdlLibrary) {
			MdlLibrary lib = (MdlLibrary) mdlSection;
			MdlBlock referencedBlock;
			for (MdlReferenceBlock refBlock : toBeLoaded) {
				referencedBlock = lib.getBlock(refBlock.getSourceBlock());
				refBlock.setReferencedBlock(referencedBlock);
			}
		}

		toBeLoaded = parentToBeLoaded;

		return mdlFile;
	}

	private static MdlSection specialise(MdlSection section) throws FileNotFoundException,
			MdlLexerException, MdlParserException {
		String nodeName = section.getNodeName();

		if (nodeName.equals("Library")) {
	        String name = section.cutFieldValue("Name");
        	return convert(section, new MdlLibrary(name));

        }
		if (nodeName.equals("Model")) {
            String name = section.cutFieldValue("Name");
        	return convert(section, new MdlModel(name));

        }
		
        MdlDOMExtensionFactory factory = extensions.get(nodeName);
        if (factory != null) {
        	return factory.specialise(section);
        }

        throw new InternalError("Unknown section: " + nodeName + ".");
    }

	private static <T extends MdlAbstractSystemOwner> T convert(MdlSection section,
			T systemOwner) throws FileNotFoundException, MdlLexerException, MdlParserException {
        moveMaskVariables(section, systemOwner);

		Map<String, List<MdlField>> blockTypeDefaults = extractDefaults(section);

		MdlSection systemSection = section.removeEntry("System");
        MdlSystem genSystem = convert(systemSection, blockTypeDefaults);

		systemOwner.setSystem(genSystem);
		moveSampleTime(section, systemOwner);
		section.copyFieldsTo(systemOwner);

		return systemOwner;
	}

	private static void moveMaskVariables(MdlSection src, MdlSystemOwner dst) {
        String maskVariables = src.cutFieldValue("MaskVariables");
        if (maskVariables != null) {
            String maskValueString = src.cutFieldValue("MaskValueString");

            String[] vars = maskVariables.split(";");
            String[] values = maskValueString.split("\\|");
            for (int i = 0; i < vars.length; i++) {
                dst.addVariable(
                        vars[i].substring(0, vars[i].indexOf("=")),
                        values[i]);
            }
        }
    }

	private static Map<String, List<MdlField>> extractDefaults(MdlSection section) {
		MdlSection defaultsSection = section.removeEntry("BlockParameterDefaults");
		Set<MdlSection> defaults = defaultsSection.listEntries("Block");
		Map<String, List<MdlField>> blockTypeDefaults = new HashMap<String, List<MdlField>>();
		MdlField field;
		List<MdlField> defaultFields;
		for (MdlSection mdlSection : defaults) {
			field = mdlSection.removeEntry("BlockType");
			defaultFields = new ArrayList<MdlField>();
			for (MdlElement mdlNode : mdlSection) {
				if (mdlNode instanceof MdlField) {
					defaultFields.add((MdlField) mdlNode);
				}
			}
			blockTypeDefaults.put(field.getValue(), defaultFields);
		}
		return blockTypeDefaults;
	}

	private static MdlSystem convert(MdlSection section,
			Map<String, List<MdlField>> blockTypeDefaults)
			throws FileNotFoundException, MdlLexerException, MdlParserException {
		MdlSystem parentMdlSystem = mdlSystem;

		String systemName = section.cutFieldValue("Name");

        mdlSystem = new MdlSystem(systemName);
        
        moveSampleTime(section, mdlSystem);

        Set<MdlSection> blockSections = section.listEntries("Block");
        Set<MdlSection> lineSections = section.listEntries("Line");
        
        List<MdlSection> subsystems = new ArrayList<MdlSection>();
        MdlField field;
        for (MdlSection subSection : blockSections) {
 			field = subSection.getEntry("BlockType");
        	if (field.getValue().equals("SubSystem")) {
        		subsystems.add(subSection);
     		    
     		} else {
     			List<MdlField> defaults = blockTypeDefaults.get(field.getValue());
     			if (defaults != null) {
         			subSection.introduce(defaults);
         		}

     			mdlSystem.addBlock(specialiseBlock(subSection, blockTypeDefaults));
     		}
        }
        for (MdlSection subSection : subsystems) {
        	mdlSystem.addSubSystem(specialiseSubSystem(subSection, blockTypeDefaults));
        }

        for (MdlSection subSection : lineSections) {
        	mdlSystem.addSignal(specialiseSignal(subSection));
        }

        MdlSystem genSystem = mdlSystem;
        mdlSystem = parentMdlSystem;
		return genSystem;
    }

	private static SimulinkType cutDataType(MdlSection section) {
        String dt = section.cutFieldValue("OutDataType");

        if (dt != null) {
        	SimulinkType[] types = SimulinkType.values();
        	for (SimulinkType t : types) {
				if (t.toString().equals(dt)) {
					return t;
				}
			}
        }

        return SimulinkType.UINT_16;
    }

	private static MdlBlock specialiseBlock(MdlSection subSection,
			Map<String, List<MdlField>> blockTypeDefaults) throws FileNotFoundException,
			MdlLexerException, MdlParserException {
		String name = subSection.cutFieldValue("Name");
		String type = subSection.cutFieldValue("BlockType");

		MdlBlock mdlBlock;
		if (type.equals("Inport")) {
			MdlInport mdlInport = new MdlInport(name,
					subSection.cutPortNumber(), cutDataType(subSection));
		    mdlBlock = mdlInport;
		    
		} else if (type.equals("EnablePort")) {
		    String stateWhenEnabling = subSection.cutFieldValue("StatesWhenEnabling");
			MdlEnablePort mdlEnablePort = new MdlEnablePort(name, stateWhenEnabling);
		    mdlBlock = mdlEnablePort;
		    
		} else if (type.equals("TriggerPort")) {
		    String stateWhenEnabling = subSection.cutFieldValue("StatesWhenEnabling");
		    MdlTriggerPort mdlTriggerPort = new MdlTriggerPort(name, stateWhenEnabling);
		    mdlTriggerPort.setTriggerType(subSection.cutFieldValue("TriggerType"));
		    mdlBlock = mdlTriggerPort;
		    
		} else if (type.equals("Outport")) {
		    MdlOutport mdlOutport = new MdlOutport(name,
		    		subSection.cutPortNumber(), cutDataType(subSection));
		    mdlBlock = mdlOutport;
		    
		} else {
			if (type.equals("Reference")) {
				String fieldSourceBlock = subSection.cutFieldValue("SourceBlock");
				String sourceType = subSection.cutFieldValue("SourceType");
		    	String sourceFile = fieldSourceBlock.substring(0, fieldSourceBlock.indexOf('/'));
		    	String sourceBlock = fieldSourceBlock.substring(fieldSourceBlock.indexOf('/') + 1);

		    	MdlReferenceBlock referenceBlock = new MdlReferenceBlock(name,
		    			sourceFile, sourceBlock, sourceType);
				MdlBlock linkedBlock = linkedBlocks.get(fieldSourceBlock);
				if (linkedBlock == null) {
			    	if (sourceFile.equals("simulink_extras")) {
			    		linkedBlock = Libraries.SimulinkExtras.getBlock(sourceBlock);
			    	} else if ((sourceFile + ".mdl").equals(fileName)) {
			    		linkedBlock = mdlSystem.getBlock(sourceType);
						
						if (linkedBlock == null) {
//			    			throw new InternalError("BAD LINK: " + sourceFile + "\\" + sourceBlock);
							toBeLoaded.add(referenceBlock);
						}
			    	} else {
			    		MdlLibrary library = loadLinkedLibrary(sourceFile + ".mdl");
			    		linkedBlock = library.getBlock(sourceBlock);
			    		if (linkedBlock == null) {
			    			throw new InternalError("BAD LINK: " + sourceFile + "\\" + sourceBlock);
			    		}
			    		linkedBlocks.put(fieldSourceBlock, linkedBlock);
			    	}
				}
				
				referenceBlock.setReferencedBlock(linkedBlock);
				mdlBlock = referenceBlock;
		    } else if (type.equals("S-Function")) {
				String tag = subSection.cutFieldValue("Tag");
				String functionName = subSection.cutFieldValue("FunctionName");
		    	
				MdlDOMSFunctionExtensionFactory extension = sfunExtensions.get(functionName);
				if (extension != null) {
					mdlBlock = extension.specialiseSfunction(subSection, name, tag);
				} else {
					MdlSFunction mdlSFunction = new MdlSFunction(name, tag, functionName);

					mdlBlock = mdlSFunction;
				}
		    } else {
		    	mdlBlock = new MdlBlock(type, name);
		    }
		}
		moveSampleTime(subSection, mdlBlock);
		subSection.copyFieldsTo(mdlBlock);

		return mdlBlock;
	}

	private static MdlLibrary loadLinkedLibrary(String sourceFile)
			throws FileNotFoundException, MdlLexerException, MdlParserException {
		MdlLibrary library = linkedLibraries.get(sourceFile);
		if (library == null) {
			String parentFileName = fileName;

			fileName = sourceFile;
			MdlFile file = load(path + File.separator + fileName);
			library = (MdlLibrary) file.list("Library").get(0);
    		linkedLibraries.put(sourceFile, library);

    		fileName = parentFileName; // back to previous file.
		}
		return library;
	}

	private static MdlSubSystem specialiseSubSystem(MdlSection subSection,
			Map<String, List<MdlField>> blockTypeDefaults)
			throws FileNotFoundException, MdlLexerException, MdlParserException {
		String name = subSection.cutFieldValue("Name");
		MdlSection systemSection = subSection.removeEntry("System");
		MdlField maskTypeField = subSection.removeEntry("MaskType");
		MdlSystem internalSystem = convert(systemSection, blockTypeDefaults);

		MdlSubSystem mdlBlockSubSystem = new MdlSubSystem(name, internalSystem);
		moveMaskVariables(subSection, mdlBlockSubSystem);
		moveSampleTime(subSection, mdlBlockSubSystem);
		subSection.copyFieldsTo(mdlBlockSubSystem);
		return mdlBlockSubSystem;
	}

    private static MdlSignal specialiseSignal(MdlSection signalSection) {
        String srcBlockName = signalSection.cutFieldValue("SrcBlock");
        MdlBlock srcBlock = mdlSystem.getBlock(srcBlockName);
        String srcPortNumber = signalSection.cutFieldValue("SrcPort");
        
        SimulinkType simulinkType = srcBlock.getOutputDataType(DOUBLE);

        MdlSignal signal = new MdlSignal();
		signal.setSrcBlock(srcBlock);
        signal.setSrcPort(Integer.parseInt(srcPortNumber));
        signal.setType(simulinkType);
        MdlSignalBranch mdlTarget = specialiseBranches(signalSection);
        signal.setTarget(mdlTarget);

        return signal;
    }

    private static MdlSignalBranch specialiseBranches(MdlSection section) {
    	MdlSection branchBlock = null;
        MdlSignalBranch branch;

//        String name = getFieldValue(section, "Name");
//        if (name != null) {
//            branch.setName(name);
//        }

        String dstBlockName = section.cutFieldValue("DstBlock");
        String dstPortNum = section.cutFieldValue("DstPort");

        MdlSignalBranch mdlTarget = new MdlSignalBranch();

        String nodeName;
        for (MdlElement node : section) {
            if (node instanceof MdlSection) {
                nodeName = ((MdlSection) node).getNodeName();
				if (nodeName.equals("Branch")) {
                    branchBlock = (MdlSection) node;
                    branch = specialiseBranches(branchBlock);
                    mdlTarget.addBranch(branch);
                }
            }
        }

        if (branchBlock == null) {
            MdlBlock dstBlock = mdlSystem.getBlock(dstBlockName);
            mdlTarget.setDstBlock(dstBlock);
            mdlTarget.setDstPort(dstPortNum);
        }

        return mdlTarget;
    }

	private static void moveSampleTime(MdlSection section, MdlTimedSection timedSection) {
		MdlField attST = section.cutSampleTime();
        if (attST != null) {
    		timedSection.setSampleTime(attST.getValue().replaceAll("\"", ""));
        }
	}

}
