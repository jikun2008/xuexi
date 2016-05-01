package org.slive.quickcmd.actions.utils;

import java.io.File;
import java.io.IOException;

public class CmdCommandGenerator {
	private static final String START_CMD = "cmd /c ";

	/**
	 * 生成产生build.xml文件的cmd的指令
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static String buildbuildxmlFileCommand(String path) {
		File file = new File(path);
		String filename = file.getName();
		String cmd = START_CMD + "android create uitest-project -n " + filename + " -t 18 -p " + path;
		System.out.println("cmd=" + cmd);
		return cmd;
	}

	/**
	 * 生成生成jar文件的cmd指令
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static String buildbuildJarCommand(String path) {
		String cmd = START_CMD + "ant build";
		System.out.println("cmd=" + cmd);
		return cmd;
	}
}
