package com.formallab.simulink.mdl;

public class MdlReferenceBlock extends MdlBlock {

	private String sourceBlock;

	private String sourceType;

	private String sourceFile;

	private MdlBlock referencedBlock;

	public MdlReferenceBlock(String name, String sourceFile,
			String sourceBlock, String sourceType) {
		super("Reference", name);
		this.sourceFile = sourceFile;
		this.sourceBlock = sourceBlock;
		this.sourceType = sourceType;
	}

	public String getSourceFile() {
		return sourceFile;
	}

	public String getSourceBlock() {
		return sourceBlock;
	}

	public String getSourceType() {
		return sourceType;
	}

	public MdlBlock getReferencedBlock() {
		return referencedBlock;
	}

	public void setReferencedBlock(MdlBlock referencedBlock) {
		this.referencedBlock = referencedBlock;
	}

	public MdlBlock linkedBlock() {
		MdlBlock linkedBlock = getReferencedBlock();
		
		if (linkedBlock == null) {
			throw new InternalError("BAD LINK: " + getSourceFile() +
					"\\" + getSourceBlock());
		}

		if (!getSourceType().equals("SubSystem")) {
			String blockType = linkedBlock.getBlockType();
			MdlBlock referenceInstance;
			if (blockType.equals("SubSystem")) {
				MdlSubSystem subSystem = (MdlSubSystem)linkedBlock;
				MdlSubSystem mdlSubSystem = new MdlSubSystem(subSystem.getSystem());
				subSystem.copyMaskVariablesTo(mdlSubSystem);
				subSystem.copyFieldsTo(mdlSubSystem);
				referenceInstance = mdlSubSystem;
			} else {
				referenceInstance = new MdlBlock(blockType);
			}
			linkedBlock.copyFieldsTo(referenceInstance);
			copyFieldsTo(referenceInstance);
			referenceInstance.setName(getName());
			return referenceInstance;
		}
		return linkedBlock;
	}

}