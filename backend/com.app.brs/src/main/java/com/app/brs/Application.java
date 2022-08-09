package com.app.brs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {

	public static void main(String[] args) throws ParseException {
		SpringApplication.run(Application.class, args);

//		System.out.println(LocalDate.now().getDayOfWeek().name());
//		Date d = new Date();
//		System.out.println("day of the week is  : " + d.getDay());

//		String sdate = "24/08/2022";
//		Date date = new SimpleDateFormat("dd/MM/yyyy").parse(sdate);
//		getDayy(date);
	}

	private static void getDayy(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
		String dayWeekTest = new SimpleDateFormat("EEEE").format(date);
		System.out.println(dayWeekTest);
	}

}
