package org.talend.rcp.exercise.model;

public class CsvCell {
	
	
	private String value;
	
	public CsvCell(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String text) {
		this.value = text;
	}

}
