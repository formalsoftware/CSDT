package simulinkeditor.project;

import java.util.List;

/**
 * @author Cauanne e Gustavo
 */
public class Project {

	private String projectName;

	private List sourceDirs;

	private String mainSourceFileName;

	private String outputDir;

	private String projectLocation;

	/**
	 * 
	 */
	public Project() {
		// projectName = CspSuitePlugin.getResourceString("project_name");
		//
		// sourceDirs = new ArrayList();
		//
		// mainSourceFileName =
		// CspSuitePlugin.getResourceString("main_source_file_name");
		//
		// outputDir = CspSuitePlugin.getResourceString("output_dir");
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		if (projectName != null) {
			this.projectName = projectName;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public List getSourceDirs() {
		return sourceDirs;
	}

	public void setSourceDirs(List sourceDirs) {
		if (sourceDirs != null) {
			this.sourceDirs = sourceDirs;
		} else {
			throw new IllegalArgumentException();
		}
	}

	public String getProjectLocation() {
		return projectLocation;
	}

	public void setProjectLocation(String text) {
		this.projectLocation = text;
	}

	public String getMainSourceFileName() {
		return mainSourceFileName;
	}

	public void setMainSourceFileName(String sourceFile) {
		this.mainSourceFileName = sourceFile;
	}

	public String getOutputDir() {
		return outputDir;
	}

	public void setOutputDir(String outputDir) {
		this.outputDir = outputDir;
	}
}