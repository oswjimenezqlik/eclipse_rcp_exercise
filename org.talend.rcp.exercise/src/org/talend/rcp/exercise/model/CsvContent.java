package org.talend.rcp.exercise.model;

import java.util.ArrayList;
import java.util.List;

public class CsvContent {

	private CsvRow header;
	private List<CsvRow> rows;

	public CsvContent() {
		header = new CsvRow();
		rows = new ArrayList<>();
	}

	public int getNumberOfColumns() {
		return header.getCells().size();
	}

	public int getNumberOfRows() {
		int numberOfRows = 0;
		if ((header != null) && header.getCells().size() != 0) {
			numberOfRows = +1;
		}

		numberOfRows = +rows.size();
		return numberOfRows;
	}

	public String getHeaderValue(int headerIndex) {
		return header.getCells().get(headerIndex).getValue();
	}

	public CsvRow getHeader() {
		return header;
	}

	public void setHeader(String... headers) {
		header.setCells(headers);
	}

	public void setHeader(CsvRow header) {
		this.header = header;
	}

	public List<CsvRow> getRows() {
		return rows;
	}

	public void setRows(List<CsvRow> rows) {
		this.rows = rows;
	}

	public CsvCell getElement(int column, int row) {
		if ((row == 0) && (header.getCells().size() <= column) || ((row > 0) && (rows.size() <= row))) {
			return null;
		}

		if (row == 0) {
			return header.getCells().get(column);
		}

		return rows.get(row).getCells().get(column);
	}

	public void addRows(String... cellValues) {
		CsvRow row = new CsvRow();
		row.setCells(cellValues);
		this.rows.add(row);

	}
}
