package br.com.vanderson.app;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static String pattern_DD_MM_yyyy = new String( "dd/MM/yyyy" );
	public static String pattern_DD_MM_yyyy_hh_mm_ss = new String( "dd/MM/yyyy HH:mm:ss" );
	public static String pattern_DD_MM_yyyy_hh_mm = new String( "dd/MM/yyyy HH:mm" );
	public static String pattern_HH_mm_ss = new String( "HH:mm:ss" );
	public static String timeZonePadrao = "America/Sao_Paulo";
	
	public static Date getHoje(){
		return Calendar.getInstance().getTime();
	}
	
}
