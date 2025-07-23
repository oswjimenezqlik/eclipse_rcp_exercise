package org.talend.rcp.exercise.parts;

/**
 * Type of files supported in File info view
 */
public enum FileTypeEnum {

	DOCUMENT("Document"), FOLDER("Folder");

	private String label;

	FileTypeEnum(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

}
