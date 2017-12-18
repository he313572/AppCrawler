package com.honpe.table;

public enum TableType {
	CAR("car"), TECHNOLOGY("technology"), DIGITAL("digital"), PHONE("phone");
	private String table;

	private TableType(String table) {
		this.table = table;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}
}
