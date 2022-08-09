package com.app.brs.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ReservationSystemUtils {

	private ReservationSystemUtils() {

	}

	public static String getId(String prefix) {
		Long nanoTime = System.nanoTime();
		return prefix + nanoTime;
	}

	public static String getDayFromDate(String stringDate) {
		try {
			Date date = new SimpleDateFormat("dd/MM/yyyy").parse(stringDate);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			String dayWeekTest = new SimpleDateFormat("EEEE").format(date);
			return dayWeekTest;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

}
