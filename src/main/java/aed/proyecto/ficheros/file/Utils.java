package aed.proyecto.ficheros.file;

import java.io.File;

/**
 * @author Ayoze Amaro
 *
 */
public class Utils {

	public static void deleteDirectory(File directory) {
		try {
			File[] files = directory.listFiles();

			for (int x = 0; x < files.length; x++) {
				if (files[x].isDirectory()) { deleteDirectory(files[x]); }
				files[x].delete();
			}
		} catch (Exception e) { System.err.println(e); }
	}
}
