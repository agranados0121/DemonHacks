package demon;
import java.time.*;

public class Time_Format {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String time = getTime();
		System.out.println(time);
	}

	private static String getTime() {
		String s = "";
		LocalTime tm = java.time.LocalTime.now();
		LocalTime noon = LocalTime.NOON;
		LocalTime mid = LocalTime.MIDNIGHT;
		if(tm.equals(mid)) {
			s = "12:00 AM";
		}
		else if(tm.equals(noon)) {
			s = "12:00 PM";
		}
		else if(tm.isAfter(noon)) {
			int t = tm.getHour()-12;
			s = String.format("%d:%d PM", t, tm.getMinute());
		}
		else {
			s = String.format("%d:%d AM", tm.getHour(), tm.getMinute());
		}
		return s;
	}
}
