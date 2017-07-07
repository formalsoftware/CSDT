package com.formallab.simulink.mdl.compatibility;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.StringTokenizer;


/**
 * 
 * 
 * @author Joabe Jesus Junior
 */
public class MdlTextFileFilter {

	/**
	 * This method filters the undesired parts of a Simulink Model.
	 * 
	 * @param inputFileName
	 * @return A String that represents the new mdl text produced.
	 * @throws Exception
	 */
    public String filter(String inputFileName) throws Exception {
        StringWriter out = new StringWriter();

        boolean read = true;

        BufferedReader stdin = new BufferedReader(new StringReader(inputFileName));
        PrintWriter stdout = new PrintWriter(out);

        // insert first line
        stdout.println(stdin.readLine());
        // insert the second line
        stdout.println(stdin.readLine());

        String s;
        while (read) {
            s = stdin.readLine();

            if (s.startsWith("  System {")) {
                // it starts the description of the whole system
                stdout.println(s);
                while ((s = stdin.readLine()) != null) {
                    if (isValidLine(s)) {
                        // check if the line contains the desired keywords or
                        // parameters.
                        stdout.println(s);
                        System.out.println(s);
                    } else s = "";
                }
                read = false;
            } else s = "";
        }// end while
        stdin.close();
        stdout.close();

        return out.getBuffer().toString();
    }

    private boolean isValidLine(String temp) {
        // the method checks whether the input string contains the desired
        // keywords or parameters.
        // the checking is done by split the string word by word using the
        // stringtokenizer inner method, and we focus on the first word.
        boolean result = true;

        if ("".equals(temp.trim())) {
            return true;
        }

        StringTokenizer st = new StringTokenizer(temp);
        String fw = (String) st.nextToken();

        // comparing the word with supported keywords and parameters.
        if (fw.equals("#") ||
                fw.equals("Model") || fw.equals("System") || fw.equals("Block")
                || fw.equals("Stateflow") || fw.equals("machine")
                || fw.equals("chart") || fw.equals("state") || fw.equals("transition")
                || fw.equals("firstTransition") || fw.equals("instance")
                || fw.equals("target")
                || fw.equals("data") || fw.equals("name")
                || fw.equals("id") || fw.equals("src") || fw.equals("dst")
                || fw.equals("labelString") || fw.equals("id")
                || fw.equals("Annotation")
                || fw.equals("Line") || fw.equals("Branch")
                || fw.equals("Name") || fw.equals("BlockType")
                || fw.equals("Value") || fw.equals("Operator")
                || fw.equals("Inputs") || fw.equals("Criteria")
                || fw.equals("SrcBlock") || fw.equals("SrcPort")
                || fw.equals("DstBlock") || fw.equals("DstPort")
                || fw.equals("InitialCondition") || fw.equals("SampleTime")
                || fw.equals("}") || fw.equals("Port"))
            result = true;
        else
            result = false;
        return result;
    }

}