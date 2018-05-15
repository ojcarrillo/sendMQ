package com.example.demo;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

public class Utils {

	private final static String LETRAS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	private final static String NUMEROS = "1234567890";
	private final static String FORMATO_FECHA = "yyyyMMdd";
	private final static Integer FACTOR_PRECIO = 50;
	private final static Double FACTOR_PRECIO_DINERO = 12000D;
	private final static Double IVA = 0.81D; // 19%

	private final static List<String> nombres = Arrays.asList(
			"ANTONIO,JOSE,MANUEL,FRANCISCO,DAVID,JUAN,JOSE ANTONIO,JAVIER,JOSE LUIS,DANIEL,FRANCISCO JAVIER,JESUS,CARLOS,ALEJANDRO,MIGUEL,JOSE MANUEL,RAFAEL,PEDRO,MIGUEL ANGEL,ANGEL,JOSE MARIA,PABLO,FERNANDO,SERGIO,LUIS,JORGE,ALBERTO,JUAN CARLOS,JUAN JOSE,ALVARO,DIEGO,ADRIAN,RAUL,JUAN ANTONIO,ENRIQUE,IVAN,RAMON,RUBEN,VICENTE,OSCAR"
					.split(","));

	private final static List<String> apellidos = Arrays.asList(
			"GARCIA,GONZALEZ,RODRIGUEZ,FERNANDEZ,LOPEZ,MARTINEZ,SANCHEZ,PEREZ,GOMEZ,MARTIN,JIMENEZ,RUIZ,HERNANDEZ,DIAZ,MORENO,MUÑOZ,ALVAREZ,ROMERO,ALONSO,GUTIERREZ,NAVARRO,TORRES,DOMINGUEZ,VAZQUEZ,RAMOS,GIL,RAMIREZ,SERRANO,BLANCO,MOLINA,MORALES,SUAREZ,ORTEGA,DELGADO,CASTRO,ORTIZ,RUBIO,MARIN,SANZ,NUÑEZ"
					.split(","));

	public static String getDatosFactura(int diaspasados, int diasfuturos) {
		return getFechaFactura(new Date(), diaspasados) + getNumeroDocumentoCliente() + getTipoCliente()
				+ getNombreCliente() + getValoresMonetarios() + getFechaFactura(new Date(), diasfuturos)
				+ getEstadoFactura();
	}

	public static String getDatosFacturaReceipt(int diaspasados, int diasfuturos) {
		return getFechaFactura(new Date(), diaspasados) + getNumeroDocumentoCliente() + getNombreCliente()
				+ getTipoCliente() + getValoresMonetarios() + "A"; //getEstadoFactura();
	}

	public static String getNumeroFactura(String prefijo) {
		return prefijo + generateRandomStr(LETRAS, 3) + String.format("%08d", new Random().nextInt(10000000) + 1);
	}

	public static String getFechaFactura(Date fechaInicio, int dias) {
		try {
			return parseDate(getFutureDay(fechaInicio, getRandomInt(dias)), FORMATO_FECHA);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getNumeroDocumentoCliente() {
		return StringUtils.leftPad(generateRandomStr(NUMEROS, 10), 15, "0");
	}

	public static String getNombreCliente() {
		return StringUtils.rightPad(getNombres() + " " + getApellidos(), 70, " ");
	}

	public static String getTipoCliente() {
		return generateRandomStr("pj", 1);
	}

	public static String getValorFactura() {
		return StringUtils.leftPad(String.valueOf(getValor(1)), 17, "0");
	}

	public static String getValorIVA() {
		return StringUtils.leftPad(String.valueOf(getValor(1)), 17, "0");
	}

	public static String getValorDescuento() {
		return StringUtils.leftPad(String.valueOf(getValor(1)), 17, "0");
	}

	public static String getValoresMonetarios() {
		Double valorFactura = getValor(1);
		Double valorIva = getValorIVA(valorFactura);
		int porc = new Random().nextInt(10) + 1;
		Double valorDescuento = Math.floor(valorFactura * porc / 100);
		return StringUtils.leftPad(String.valueOf(porc) + ",00", 5, "0")
				+ StringUtils.leftPad(String.valueOf(valorDescuento), 17, "0")
				+ StringUtils.leftPad(round((1 - IVA) * 100, 2).toString(), 5, "0")
				+ StringUtils.leftPad(String.valueOf(valorIva), 17, "0")
				+ StringUtils.leftPad(String.valueOf(valorFactura), 17, "0");
	}

	public static String getEstadoFactura() {
		return generateRandomStr("PAD", 1);
	}

	/***********************************************************************/

	/* metodos generales */

	public static String getNombres() {
		return nombres.get(new Random().nextInt(40) + 1) + " " + nombres.get(new Random().nextInt(nombres.size()));
	}

	public static String getApellidos() {
		return apellidos.get(new Random().nextInt(40) + 1) + " "
				+ apellidos.get(new Random().nextInt(apellidos.size()));
	}

	public static String generateRandomStr(String aToZ, Integer size) {
		Random rand = new Random();
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < size; i++) {
			int randIndex = rand.nextInt(aToZ.length());
			res.append(aToZ.charAt(randIndex));
		}
		return res.toString();
	}

	public static int getRandomInt(int seed) {
		return new Random().nextInt(seed) + 1;
	}

	public static Date getFutureDay(Date date, Integer days) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, days); // number of days to add
		return c.getTime();
	}

	public static String parseDate(Date fecha, String formato) throws ParseException {
		SimpleDateFormat formatoDate = new SimpleDateFormat(formato);
		return formatoDate.format(fecha);
	}

	public static Date parseDate(String fecha, String formato) throws ParseException {
		SimpleDateFormat formatoDate = new SimpleDateFormat(formato);
		return formatoDate.parse(fecha);
	}

	public static Double getValor(Integer cant) {
		Double valorPto = (new Random().nextInt(FACTOR_PRECIO) + 1D) * FACTOR_PRECIO_DINERO;
		return round(cant * valorPto, 2);
	}

	public static Double getValorIVA(Double valorTotal) {
		return round(valorTotal - (valorTotal * IVA), 2);
	}

	public static Double round(Double val, Integer decimals) {
		return new BigDecimal(val.toString()).setScale(decimals, RoundingMode.HALF_UP).doubleValue();
	}

}
