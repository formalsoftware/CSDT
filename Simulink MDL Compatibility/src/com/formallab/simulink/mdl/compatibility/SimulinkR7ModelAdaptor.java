package com.formallab.simulink.mdl.compatibility;


/* Tool component which adapts models saved with Simulink up to version 7.4.0
 * to be processed by CLawZ!
 *
 * The implementation might be a bit naive since I couldn't exploit very much
 * knowledge about Simulink mdl files, however nevertheless should be fairly
 * robust. */

public class SimulinkR7ModelAdaptor {

//	public static void invoke(Block model) {
//		discardProblematicBlocks(model);
//	}
//
//	private static void discardProblematicBlocks(Block model) {
//		List<Block> culprits = new LinkedList<Block>();
//		for (BlockElement element : model) {
//			if (element.isBlock()) {
//				Block block = (Block) element;
//				if (checkForProblematicSymbols(block)) {
//					culprits.add(block);
//				}
//			}
//		}
//		/*
//		 * We have to do the removal in a separate step as we cannot modify the
//		 * underlying collection while iterating.
//		 */
//		for (Block block : culprits) {
//			/*
//			 * Note the following doesn't remove the block from culprits, but
//			 * from its context in model.
//			 */
//			block.remove();
//			System.out.println("Removed Simulink clause " + block.getName()
//					+ " (contains $ character(s))");
//		}
//	}
//
//	private static boolean checkForProblematicSymbols(Block block) {
//		return block.serialise().contains("$");
//	}

}
