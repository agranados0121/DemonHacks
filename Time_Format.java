package demon;
import java.time.*;
import java.util.Arrays;

public class Time_Format {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String test = "2019-10-19T00:55:31";
		String hour = test.substring(11,13);
		String min = test.substring(14,16);
		String[] time = new String[2];
		time = getTime(hour, min);
		System.out.println(Arrays.toString(time));
	}

	private static String[] getTime(String arrival_hour, String arrival_min) {
		String[] s = new String[2];
		LocalTime tm = java.time.LocalTime.now();
		int hr = Integer.parseInt(arrival_hour);
		int min = Integer.parseInt(arrival_min);
		
		if(hr == 0) {
			s[0] = String.format("Arival Time: %d:%s AM", (Integer.parseInt(arrival_hour)+12), arrival_min);
			s[1] = String.format("Time Left: %d", (Integer.parseInt(arrival_min)- tm.getMinute()));
			
			
		}
		else if(hr == 12) {
			s[0] = String.format("Arival Time: %d:%s PM", (Integer.parseInt(arrival_hour)-12), arrival_min);
			s[1] = String.format("Time Left: %s", arrival_min);
		}
		else if(hr > 12 && hr < 0) {
			int hr_remaining = java.lang.Math.abs(tm.getHour()- hr);
			int min_remaining = java.lang.Math.abs(tm.getMinute()-min);
			s[0] = String.format("Arival Time: %s:%s PM", (Integer.parseInt(arrival_hour)-12), arrival_min);
			s[1] = String.format("Time Left: %d min" , min_remaining);
			
		}
		else {//hr > 24 && hr < 12
			int min_remaining = java.lang.Math.abs(tm.getMinute()-min);
			s[0] = String.format("Arival Time: %s:%s AM", (Integer.parseInt(arrival_hour)-12), arrival_min);
			s[1] = String.format("Time Left: %d min" , min_remaining);
		}
		return s;
	}
}
