package projects.abhinav.loaders;

import java.lang.reflect.Field;
import java.util.Arrays;

public class NativeLibraryLoader {
	private static void addLibraryPath(String pathToAdd) throws Exception {
		final Field usrPathsField = ClassLoader.class.getDeclaredField("usr_paths");
		usrPathsField.setAccessible(true);

		// get array of paths
		final String[] paths = (String[]) usrPathsField.get(null);

		// check if the path to add is already present
		for (String path : paths) {
			if (path.equals(pathToAdd)) {
				return;
			}
		}

		// add the new path
		final String[] newPaths = Arrays.copyOf(paths, paths.length + 1);
		newPaths[newPaths.length - 1] = pathToAdd;
		usrPathsField.set(null, newPaths);
	}

	public static void init(String pathToLibrary, String nativeLibraryName) {
		try {
			addLibraryPath(pathToLibrary);
			System.loadLibrary(nativeLibraryName);
		} catch (Exception ignored) {
		}
	}
}
