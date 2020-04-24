package com.fware.csdt.simulink.mdl.editor.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;

import com.fware.csdt.simulink.mdl.editor.project.Project;

public class NewProjectOpperation implements IRunnableWithProgress {

	private Project project;

	public NewProjectOpperation(Project project) {
		this.project = project;
	}

	public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
		IProgressMonitor progressMonitor = monitor;

		// this null-check is recommended by developers of other plugins
		if (progressMonitor == null) {
			progressMonitor = new NullProgressMonitor();
		}

		// try {
		// monitor.beginTask(CspSuitePlugin.getResourceString(
		// "projectWizardProgressCreating"), 12);
		//
		// IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
		// String name = project.getProjectName();
		// IProject project = root.getProject(name);
		// monitor.worked(1);
		//
		// createProject(project, monitor);
		// monitor.worked(1);
		// addProjectNature(project, monitor);
		// monitor.worked(1);
		// createProjectDirs(project, monitor);
		// monitor.worked(1);
		//
		// createMainFile(project, monitor);
		// monitor.worked(1);
		//
		// //monitor.subTask(CspPlugin.getResourceString(
		// // "projectWizardProgressSettingsFile"));
		// //CspProperties.saveProjectProperties(project);
		// //monitor.worked(1);
		//
		//// IDE.openEditor(CspPlugin.getCurrentWorkbenchPage(),
		//// CspProperties.getProjectSourceFile(project));
		//
		// monitor.worked(1);
		// } catch (CoreException e) {
		// CspSuitePlugin.log(CspSuitePlugin.getResourceString(
		// "projectWizardCreateError"), e);
		// } finally {
		// monitor.done();
		// }
	}

	private void createProjectDirs(IProject project2, IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	private void addProjectNature(IProject project2, IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	private void createProject(IProject project2, IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	/**
	 * Create main file of the project from template.
	 * 
	 * @param project
	 *            project
	 * @param monitor
	 *            progress monitor
	 * @throws CoreException
	 */
	private void createMainFile(IProject project, IProgressMonitor monitor) throws CoreException {
		// monitor.subTask(CspPlugin.getResourceString("projectWizardProgressFile"));
		//
		// String name = project.getSourceFile();
		// if (name == null || name.length() == 0) {
		// throw new CoreException(CspPlugin.stat("Null main file name"));
		// }
		//
		// CspProperties.setProjectProperty(project,
		// CspProperties.MAINFILE_PROPERTY, name);
		//
		// byte[] template = getTemplate(project.getTemplate());
		// if (template == null) {
		// template = new byte[0];
		// }
		// ByteArrayInputStream stream = new ByteArrayInputStream(template);
		//
		// IFile mainFile = CspProperties.getProjectSourceFile(project);
		// mainFile.create(stream, true, monitor);
	}
}