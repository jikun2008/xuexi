package org.slive.quickcmd.actions;

import java.io.File;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.slive.quickcmd.QuickCmdActivator;
import org.slive.quickcmd.actions.preferences.WorkFlowPreferenceConstants;
import org.slive.quickcmd.actions.utils.AutoWork;
import org.slive.quickcmd.actions.utils.AutoWork.OnCmdListener;
import org.slive.quickcmd.actions.utils.CmdCommandGenerator;
import org.slive.quickcmd.actions.utils.ConsoleFactory;

/**
 * 
 * @author Slive
 *
 */
@SuppressWarnings("restriction")
public class QuickCmdAction implements IObjectActionDelegate {
	private Object selected = null;
	private Class<?> selectedClass = null;
	private static final String START_CMD = "cmd /c ";

	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
	}

	public void run(IAction action) {
		if (this.selected == null) {
			MessageDialog.openInformation(new Shell(), "Quick Cmd", "Unable to cmd " + this.selectedClass.getName());
			return;
		}
		File directory = null;
		String workName = null;
		if ((this.selected instanceof IResource)) {
			directory = new File(((IResource) this.selected).getLocation().toOSString());
		} else if ((this.selected instanceof File)) {
			directory = (File) this.selected;
		}
		workName = ((IResource) selected).getProject().getName().toString();
		try {
			String filePath = "";
			String startPath = "c:";
			if (directory != null) {
				if (directory.isDirectory()) {
					filePath = directory.getAbsolutePath();
				} else {
					filePath = directory.getAbsolutePath();
				}

				// 截取当前文件的开头盘符
				startPath = filePath.substring(0, 2);
			}

			filePath = getProjectPath() + File.separator + workName;

			// 运行CMD命令，并切换到当前目录下
			if (isBuildFileExists(filePath)) {
				dojar(filePath);
			} else {
				dobuildxml(filePath);
			}

			// Runtime.getRuntime().exec("cmd /c start cd " + filePath, null,
			// new File(startPath));
		} catch (Exception e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
	}

	public void selectionChanged(IAction action, ISelection selection) {
		IAdaptable adaptable = null;
		this.selected = null;
		if ((selection instanceof IStructuredSelection)) {
			adaptable = (IAdaptable) ((IStructuredSelection) selection).getFirstElement();
			this.selectedClass = adaptable.getClass();
			if ((adaptable instanceof IResource)) {
				this.selected = ((IResource) adaptable);
			} else if (((adaptable instanceof PackageFragment))
					&& ((((PackageFragment) adaptable).getPackageFragmentRoot() instanceof JarPackageFragmentRoot))) {
				this.selected = getJarFile(((PackageFragment) adaptable).getPackageFragmentRoot());
			} else if ((adaptable instanceof JarPackageFragmentRoot)) {
				this.selected = getJarFile(adaptable);
			} else {
				this.selected = ((IResource) adaptable.getAdapter(IResource.class));
			}
		}
	}

	protected File getJarFile(IAdaptable adaptable) {
		JarPackageFragmentRoot jpfr = (JarPackageFragmentRoot) adaptable;
		File selected = jpfr.getPath().makeAbsolute().toFile();
		if (!selected.exists()) {
			File projectFile = new File(jpfr.getJavaProject().getProject().getLocation().toOSString());
			selected = new File(projectFile.getParent() + selected.toString());
		}
		return selected;
	}

	public static String getProjectPath() {
		String path = null;
		path = Platform.getLocation().toString();
		path = path.replace("/", File.separator);
		return path;
	}

	/**
	 * 判断build.xml是否存在
	 * 
	 * @param path
	 * @return
	 */
	private boolean isBuildFileExists(String path) {
		File file = new File(path + File.separator + "build.xml");
		return file.exists();
	}

	private void dobuildxml(final String path) {
		AutoWork autoWork = new AutoWork();
		final String TAG = "build build.xml";
		autoWork.doProgress(TAG, CmdCommandGenerator.buildbuildxmlFileCommand(path), new OnCmdListener() {

			@Override
			public void onsuccess() {
				// TODO Auto-generated method stub

				dojar(path);
				reshFile();

			}

			@Override
			public void onfailed() {
				// TODO Auto-generated method stub

			}
		});
	}

	private void dojar(String path) {
		File file = new File(path);
		String name = file.getName();
		final String TAG = "build +" + name + ".jar";
		AutoWork autoWork = new AutoWork();
		autoWork.doProgressFilePath(TAG, CmdCommandGenerator.buildbuildJarCommand(path), path, new OnCmdListener() {

			@Override
			public void onsuccess() {
				// TODO Auto-generated method stub
				reshFile();
			}

			@Override
			public void onfailed() {
				// TODO Auto-generated method stub
			}
		});

	}

	private void showSettingDilaog(String path) {

		IPreferenceStore store = QuickCmdActivator.getDefault().getPreferenceStore();
		String info = store.getString(WorkFlowPreferenceConstants.ID);
		InputDialog inputDlg = new InputDialog(new Shell(), "enter", "please enter your ID", info,
				new IInputValidator() {
					public String isValid(String newText) {
						int i;
						try {
							i = Integer.parseInt(newText);
						} catch (NumberFormatException e) {
							return "Inter Only!";
						}

						if (i < 0) {
							return "too young!";
						}

						if (i > 150) {
							return "too old!";
						}

						return null;
					}
				});

		if (inputDlg.open() == Window.OK) {
			System.out.println(inputDlg.getValue());
			store.setValue(WorkFlowPreferenceConstants.ID, inputDlg.getValue());
			ConsoleFactory.printToConsole(CmdCommandGenerator.buildbuildxmlFileCommand(path));
			// store.putValue("JIKUNLOVEXU", inputDlg.getValue());
		}
	}

	private void reshFile() {
		if ((selected instanceof IResource)) {
			try {

				((IResource) selected).getProject().refreshLocal(IResource.DEPTH_INFINITE, null);
				// ResourcesPlugin.getWorkspace().getRoot().refreshLocal(IResource.DEPTH_INFINITE,
				// null);
				// ((IResource) selected).refreshLocal(IResource.DEPTH_INFINITE,
				// null);
				// ConsoleFactory.printToConsole("reshFile!");
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				ConsoleFactory.printToConsole("reshFile!" + e.toString());
				e.printStackTrace();
			}
		}
	}

}
