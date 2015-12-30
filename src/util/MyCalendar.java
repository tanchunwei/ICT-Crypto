package util;

import java.util.*;
public class MyCalendar {
	
	public static String getTodayDate(){
		GregorianCalendar gc = new GregorianCalendar();
		return formatDate(gc);
	}

	public static long getDifference (GregorianCalendar d1, GregorianCalendar d2) {
		long firstDate = d1.getTimeInMillis();
		long secDate = d2.getTimeInMillis();
		
		return (firstDate - secDate)/ (24*60*60*1000);
		
	}
	
	public static String formatDate (GregorianCalendar d1 ) {
		int i = d1.get(Calendar.DATE);
		int j = d1.get(Calendar.MONTH)+01;
		
		String temp = "";
		String temp1 = "";
		if(i<10) {
			temp = "0"+i;
			if(j<10){
				temp1 = "0" + j;
				return d1.get(Calendar.YEAR) +"-" +
				temp1 + "-" +
				temp;
			}else{
				
				return d1.get(Calendar.YEAR) +"-" +
				(d1.get(Calendar.MONTH)+01) + "-" +
				temp;
			}
		}
		else if(j<10) {
			temp1 = "0"+j;
			if(i<10){
				temp = "0" + i;
				return d1.get(Calendar.YEAR) +"-" +
				temp1 + "-" +
				temp;
			}else{
				
				return d1.get(Calendar.YEAR) +"-" +
				temp1 + "-" +
				(d1.get(Calendar.DATE));
			}
		}
		else {
			return d1.get(Calendar.YEAR) +"-" +
			(d1.get(Calendar.MONTH)+01) + "-" +
			d1.get(Calendar.DATE);
		}
	}

	
	public static GregorianCalendar convertDate (String date) {
		Scanner sc = new Scanner(date);
		sc.useDelimiter("-");
		int iday, imonth, iyear;
		iyear = sc.nextInt();
		imonth = sc.nextInt();
		iday = sc.nextInt();
		GregorianCalendar d1 = new GregorianCalendar(iyear,(imonth-1),iday);
		return d1;
		
	}
}
