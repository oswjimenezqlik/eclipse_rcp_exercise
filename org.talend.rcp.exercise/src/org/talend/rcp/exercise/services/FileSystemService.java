package org.talend.rcp.exercise.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/*
 * TODO: Inject service using DI
 */
public class FileSystemService {

	public static String getFileContent(File file) {
		String fileContent = "";

		try {
			fileContent = Files.readString(file.toPath());
		} catch (Exception e) {
			System.out.println(FileSystemService.class.getSimpleName()
					+ " - there was an error getting file contents for file " + file.getName() + ": " + e.getMessage());
			e.printStackTrace();
		}
		return fileContent;
	}

	/**
	 * Get file count within the selected directory. The folder given as parameter
	 * is excluded from the count
	 * 
	 * @param directory
	 * @return number of files inside the directory
	 * @throws IOException if an error arises reading the file
	 */
	public static long countFilesInDirectory(String directory) throws IOException {
		return Files.list(Paths.get(directory)).map(Path::toFile).count();
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
		} catch (IOException e) {
			System.err.println(
					FileSystemService.class.getSimpleName() + "- error getting files in directory: " + e.getMessage());
			e.printStackTrace();
		}
		return files;
	}

	public static void writeFileContents(File file, String string) {
		try {
			Files.writeString(file.toPath(), string);
		} catch (IOException e) {
			System.err.println(FileSystemService.class.getSimpleName() + "- error writing contents to file "
					+ file.getName() + "" + e.getMessage());
			e.printStackTrace();
		}
	}
}
