package simulinkeditor.partition;

import org.eclipse.jface.text.rules.FastPartitioner;

//import com.fware.cspdt.cspm.editor.CspMEditor;
import simulinkeditor.SimulinkEditor;

public class Partitioner extends FastPartitioner {

	public Partitioner() {
		super(SimulinkEditor.gePartitionScanner(), PartitionScanner.PARTITION_TYPES);
	}
}
