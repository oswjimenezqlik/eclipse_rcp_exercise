package org.talend.rcp.exercise.services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/*
 * TODO: Inject service using DI
 */
public class FileSystemService {

	public static List<File> getNestedFilesInDirectory(String directory) throws IOException {
		return Files.walk(Path.of(directory)).map(Path::toFile).toList();
	}

	public static List<File> getFilesInDirectory(String directory) throws IOException {
		return Files.list(Paths.get(directory)).map(Path::toFile).toList();
	}
}
