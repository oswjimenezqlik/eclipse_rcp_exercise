package org.talend.rcp.exercise.utils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * File system operation utilities
 */
public class FileSystemUtils {

	/**
	 * Gets contents of a file as string. To be used with text files
	 * 
	 * @param file to read
	 * @return String contents of the file
	 */
	public static String getFileContent(File file) {
		String fileContent = "";

		try {
			fileContent = Files.readString(file.toPath());
		} catch (Exception e) {
			System.out.println(FileSystemUtils.class.getSimpleName()
					+ " - there was an error getting file contents for file " + file.getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		return fileContent;
	}

	/**
	 * Gets file count within the selected directory. Includes sub-directories and
	 * its contents. The folder given as parameter is excluded from the count
	 * 
	 * @param directory
	 * @return number of files inside the directory
	 * @throws IOException if an error arises reading the file
	 */
	public static long countFilesInDirectory(String directory) throws IOException {
		return Files.walk(Path.of(directory)).map(Path::toFile).count() - 1;
	}

	/**
	 * Get all files and folders from a given root path.
	 * 
	 * @param directory
	 * @return
	 */
	public static List<File> getFilesInDirectory(String directory) {
		List<File> files = new ArrayList<>();

		try {
			files = Files.list(Paths.get(directory)).map(Path::toFile).toList();
		} catch (Exception e) {
			System.err.println(
					FileSystemUtils.class.getSimpleName() + "- error getting files in directory: " + e.getMessage());
			e.printStackTrace();
		}
		return files;
	}

	/**
	 * Overwrites text content of a file
	 * 
	 * @param file   to write
	 * @param string content to write
	 */
	public static void writeFileContents(File file, String string) {
		try {
			Files.writeString(file.toPath(), string);
		} catch (Exception e) {
			System.err.println(FileSystemUtils.class.getSimpleName() + "- error writing contents to file "
					+ file.getName() + "" + e.getMessage());
			e.printStackTrace();
		}
	}
}
