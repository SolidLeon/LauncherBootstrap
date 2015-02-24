package bootstrap;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class Bootstrap {

	private static SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
	
	public static void main(String[] args) {
		PrintStream ps = null;
		try {
			File logsFile = new File("logs");
			if (!logsFile.exists()) logsFile.mkdir();
			 ps = new PrintStream(new File(logsFile, "bootstrap_" + UUID.randomUUID() + ".txt"));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.err.println("Cannot initialize logging");
			System.exit(0);
		}
		
		System.setOut(ps);
		
		File launcherFile = new File("launcher.jar");
		File launcherUpdateFile = new File("launcher_new.jar");
		
		if (launcherUpdateFile.exists()) {
			try {
				logInfo("Update launcher");
				Files.copy(launcherUpdateFile.toPath(), launcherFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
				logInfo("Delete launcher_new");
				launcherUpdateFile.delete();
			} catch (IOException e) {
				e.printStackTrace(ps);
			}
		}
		
		try {
			logInfo("java -jar " + launcherFile.getAbsolutePath());
			Runtime.getRuntime().exec("java -jar \"" + launcherFile.getAbsolutePath() + "\"");
			ps.close();
			System.exit(0);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(ps);
		}

	}


	private static void logInfo() {
		logInfo("");
	}
	private static void logInfo(String s) {
		System.out.println("["+sdf.format(new Date())+" INFO]: " + s);
	}
}
