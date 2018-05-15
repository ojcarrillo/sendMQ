package com.example.demo;

public class Gendata {

	public static String getDataInvoice() {
		return Utils.getNumeroFactura("I") + Utils.getDatosFactura(1, 30).replaceAll(",", ".");
	}

	public static String getDataBill() {
		return Utils.getNumeroFactura("B") + Utils.getDatosFactura(1, 15).replaceAll(",", ".");
	}

	public static String getDataReceipt() {
		return Utils.getNumeroFactura("R") + Utils.getDatosFacturaReceipt(1, 30).replaceAll(",", ".");
	}

}
