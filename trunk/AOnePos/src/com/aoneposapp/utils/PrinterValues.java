package com.aoneposapp.utils;

public class PrinterValues {
	private static PrinterValues printerObj;

	public static PrinterValues getPrinterObj() {
		if (printerObj == null) {
			PrinterValues printerObj = new PrinterValues();
			return printerObj;
		} else
			return printerObj;
	}

	int connectionType;

	public int getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(int connectionType) {
		this.connectionType = connectionType;
	}

	public String getOpenDeviceName() {
		return openDeviceName;
	}

	public void setOpenDeviceName(String openDeviceName) {
		this.openDeviceName = openDeviceName;
	}

	public String getPrinterName() {
		return printerName;
	}

	public void setPrinterName(String printerName) {
		this.printerName = printerName;
	}

	public int getLanguage() {
		return language;
	}

	public void setLanguage(int language) {
		this.language = language;
	}

	private String openDeviceName;
	private String printerName;
	private int language;

}
