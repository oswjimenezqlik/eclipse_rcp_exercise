package org.talend.rcp.exercise.model;

import java.util.ArrayList;
import java.util.List;

public class CsvRow {

	List<CsvCell> cells;

	public void setCells(String... cellvalues) {
		cells = new ArrayList<>();
		for (String cellValue : cellvalues) {
			CsvCell cell = new CsvCell(cellValue);
			cells.add(cell);
		}
	}
	
	public List<CsvCell> getCells() {
		return cells;
	}

	public void setCells(List<CsvCell> cells) {
		this.cells = cells;
	}

}
