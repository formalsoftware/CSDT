package simulinkeditor.wizards;

import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.actions.WorkspaceModifyDelegatingOperation;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

//import com.fware.cspdt.cspm.editor.project.CspMProject;
import simulinkeditor.project.Project;

public class NewProjectWizard extends Wizard implements INewWizard, IExecutableExtension {

	private Project project;

	private IConfigurationElement configElement;

	/**
	 * Useful method for e.g. loading images for the wizard.
	 * 
	 * @param workbench
	 * @param selection
	 */
	public void init(IWorkbench workbench, IStructuredSelection selection) {
		setNeedsProgressMonitor(true);
	}

	/**
	 * Useful method for e.g. getting config element info.
	 * 
	 * @param config
	 * @param propertyName
	 * @param data
	 */
	public void setInitializationData(IConfigurationElement config, String propertyName, Object data)
			throws CoreException {
		configElement = config;
	}

	@Override
	public boolean performFinish() {
		IRunnableWithProgress runnable = new NewProjectOpperation(project);
		runnable = new WorkspaceModifyDelegatingOperation(runnable);

		boolean result = true;
		try {
			getContainer().run(false, true, runnable);
		} catch (InterruptedException e) {
			result = false;
		} catch (InvocationTargetException e) {
			handleTargetException(e.getTargetException());
			result = false;
		}

		BasicNewProjectResourceWizard.updatePerspective(configElement);
		return result;
	}

	private void handleTargetException(Throwable targetException) {
		;
	}

}
