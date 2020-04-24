package com.fware.csdt.simulink.mdl.editor.partition;

import org.eclipse.jface.text.rules.FastPartitioner;

import com.fware.csdt.simulink.mdl.editor.SimulinkEditor;

public class Partitioner extends FastPartitioner {

	public Partitioner() {
		super(SimulinkEditor.gePartitionScanner(), PartitionScanner.PARTITION_TYPES);
	}
}
