package org.talend.rcp.exercise.model;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.List;
import java.util.stream.Collectors;

import org.talend.rcp.exercise.constants.FileConstants;

public class CsvContentLoader {

	/**
	 * Populates CsvContent from csv File
	 * 
	 * @param csvFile
	 * @return
	 */
	public static CsvContent fromFile(File csvFile) {
		CsvContent csvContent = new CsvContent();

		List<String> lines;
		try {
			lines = Files.readAllLines(csvFile.toPath());

			for (int i = 0; i < lines.size(); i++) {
				String line = lines.get(i);
				String[] records = line.split(FileConstants.CSV_COMMA_DELIMITER);
				if (i == 0) {
					csvContent.setHeader(records);
				} else {
					csvContent.addRows(records);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return csvContent;
	}

	/**
	 * Writes contents to csvFile. Does not handle scaping of special characters
	 * 
	 * @param content
	 * @param csvFile
	 */
	public static void toFile(CsvContent csvContent, File csvFile) {
		String headerLine = joinCsvCells(csvContent.getHeader().getCells());

		try (PrintWriter pw = new PrintWriter(csvFile)) {
			pw.println(headerLine);

			for (CsvRow row : csvContent.getRows()) {
				String rowLine = joinCsvCells(row.getCells());
				pw.println(rowLine);
			}
		} catch (Exception e) {
			System.err.println("CsvContentLoader - toFile: An error occured trying to write file: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static String joinCsvCells(List<CsvCell> cells) {
		return cells.stream().map(CsvCell::getValue).collect(Collectors.joining(FileConstants.CSV_COMMA_DELIMITER));
	}
}
