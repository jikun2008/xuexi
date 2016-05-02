package org.slive.quickcmd.actions.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

public class AutoWork {

	public void doProgress(String TAG, String cmd, final OnCmdListener cmdListener) {

		final String mcmd = cmd;
		Job job = new Job(TAG) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				monitor.beginTask("Start Task", 100);
				monitor.worked(40);
				IStatus status = runCmd(mcmd);
				monitor.done();
				if (status == Status.OK_STATUS) {
					cmdListener.onsuccess();
				} else {
					cmdListener.onfailed();
				}

				return status;
			}
		};
		job.schedule();
	}

	public IStatus runCmd(String cmd) {
		IStatus currentstatus = Status.CANCEL_STATUS;
		try {
			ConsoleFactory.printToConsole(cmd);
			Process process = Runtime.getRuntime().exec(cmd);
			
		
			int code = writeCmdInfo(process);

			// int code = process.waitFor();

			Thread.sleep(1000);
			if (code == 0) {
				currentstatus = Status.OK_STATUS;
				sysoutSuccess(cmd);
			} else {
				currentstatus = Status.CANCEL_STATUS;
				sysoutFailed(code + "");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			currentstatus = Status.CANCEL_STATUS;
			sysoutFailed(e.toString());
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			currentstatus = Status.CANCEL_STATUS;
			sysoutFailed(e.toString());
			e.printStackTrace();

		}
		return currentstatus;

	}

	public void doProgressFilePath(String TAG, String cmd, final String filepath, final OnCmdListener cmdListener) {

		final String mcmd = cmd;
		Job job = new Job(TAG) {

			@Override
			protected IStatus run(IProgressMonitor monitor) {

				monitor.beginTask("Start Task", 100);
				monitor.worked(40);
				IStatus status = runCmdFilepath(mcmd, filepath);
				monitor.done();
				if (status == Status.OK_STATUS) {
					cmdListener.onsuccess();
				} else {
					cmdListener.onfailed();
				}

				return status;
			}
		};
		job.schedule();
	}

	public IStatus runCmdFilepath(String cmd, String filepath) {
		IStatus currentstatus = Status.CANCEL_STATUS;
		try {
			ConsoleFactory.printToConsole(cmd);
			Process process = Runtime.getRuntime().exec(cmd, null, new File(filepath));
			
			int code = writeCmdInfo(process);

			// int code = process.waitFor();

			Thread.sleep(1000);
			if (code == 0) {
				currentstatus = Status.OK_STATUS;
				sysoutSuccess(cmd);
			} else {
				currentstatus = Status.CANCEL_STATUS;
				sysoutFailed(code + "");
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			currentstatus = Status.CANCEL_STATUS;
			sysoutFailed(e.toString());
			e.printStackTrace();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			currentstatus = Status.CANCEL_STATUS;
			sysoutFailed(e.toString());
			e.printStackTrace();

		}
		return currentstatus;

	}

	public interface OnCmdListener {
		void onsuccess();

		void onfailed();
	}

	private void sysoutSuccess(String info) {
		//ConsoleFactory.printToConsole("cmd commad success!");

	}

	private void sysoutFailed(String info) {
		//ConsoleFactory.printToConsole("cmd commad failed:" + info);

	}

	public int writeCmdInfo(Process pr) {
		try {

			BufferedReader input = new BufferedReader(new InputStreamReader(pr.getInputStream(), "GBK"));

			String line = null;

			while ((line = input.readLine()) != null) {
				System.out.println(line);
				ConsoleFactory.printToConsole(line);
			}

			int exitVal = pr.waitFor();

			return exitVal;
		} catch (Exception e) {
			System.out.println(e.toString());
			e.printStackTrace();
			return -1;
		}
	}
}
