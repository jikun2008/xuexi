package org.slive.quickcmd.actions;

import java.io.File;
import java.io.IOException;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.jdt.internal.core.JarPackageFragmentRoot;
import org.eclipse.jdt.internal.core.PackageFragment;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;
import org.slive.quickcmd.actions.utils.AutoWork;
import org.slive.quickcmd.actions.utils.AutoWork.OnCmdListener;
import org.slive.quickcmd.actions.utils.CmdCommandGenerator;

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
		if ((this.selected instanceof IResource)) {
			directory = new File(((IResource) this.selected).getLocation().toOSString());
		} else if ((this.selected instanceof File)) {
			directory = (File) this.selected;
		}

		try {
			String filePath = "";
			String startPath = "c:";
			if (directory != null) {
				if (directory.isDirectory()) {
					filePath = directory.getAbsolutePath();
				} else {
					filePath = directory.getParentFile().getAbsolutePath();
				}

				// 截取当前文件的开头盘符
				startPath = filePath.substring(0, 2);
			}

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
		autoWork.doProgress("build.xml", CmdCommandGenerator.buildbuildxmlFileCommand(path), new OnCmdListener() {

			@Override
			public void onsuccess() {
				// TODO Auto-generated method stub
				System.out.println("buildxml----" + "onsuccess");
				dojar(path);
				reshFile();

			}

			@Override
			public void onfailed() {
				// TODO Auto-generated method stub
				System.out.println("buildxml----" + "onfailed");
			}
		});
	}

	private void dojar(String path) {

		AutoWork autoWork = new AutoWork();
		autoWork.doProgressFilePath("生成jar", CmdCommandGenerator.buildbuildJarCommand(path), path, new OnCmdListener() {

			@Override
			public void onsuccess() {
				// TODO Auto-generated method stub
				System.out.println("生成jar----" + "onsuccess");
				reshFile();
			}

			@Override
			public void onfailed() {
				// TODO Auto-generated method stub
				System.out.println("生成jar----" + "onfailed");
			}
		});

	}

	private void reshFile() {
		if ((selected instanceof IResource)) {
			try {
				((IResource) selected).refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	/**
	 * 生产build.xml
	 * 
	 * @param path
	 * @throws IOException
	 */
	private boolean buildbuildxmlFile(String path) {
		File file = new File(path);
		file.getName();
		System.out.println("file===" + file.getName());
		String cmd = START_CMD + "android create uitest-project -n Uiauto -t 18 -p " + path;
		System.out.println("cmd===" + cmd);

		try {
			Process process = Runtime.getRuntime().exec(cmd, null, new File(path));
			process.waitFor();
			return true;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;

	}

	/**
	 * 生成JAR包
	 */
	private void generateJar(String path) {
		String cmd = START_CMD + "ant build";
		try {
			Runtime.getRuntime().exec(cmd, null, new File(path));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void doProgressbuildbuildxmlFile(String path) {
		final String otherpaty = path;
		Job job = new Job("Job") {

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				monitor.beginTask("Start Task", 100);
				monitor.worked(40);
				buildbuildxmlFile(otherpaty);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				monitor.done();
				return Status.OK_STATUS;
			}
		};
		job.schedule();
	}
}
