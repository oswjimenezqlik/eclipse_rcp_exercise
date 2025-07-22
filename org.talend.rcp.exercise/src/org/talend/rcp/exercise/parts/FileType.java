package org.talend.rcp.exercise.parts;

public enum FileType {

	DOCUMENT("Document"), 
	FOLDER("Folder");

	private String label;

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	FileType(String label) {
		this.label = label;
	}

}
