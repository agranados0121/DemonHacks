package demon;
import java.io.*;
import java.util.*;
public class CTAParser {

	public static void main(String[] args) throws FileNotFoundException {
		ArrayList <String> A = new ArrayList <String>();
        File file1 = new File("src/demon/CTA_-_System_Information_-_List_of__L__Stops.csv");
        Scanner s = new Scanner(file1);     
        while (s.hasNextLine()) {
        	A.add(s.nextLine());
        }
        s.close();

        String[] Station_Name = Station_Name(A);
        String[] Map_ID = Map_ID(A);
        String[] Location_X = X_Cord(A);
        String[] Location_Y = Y_Cord(A);
        
        String[][] CTA = {Map_ID, Station_Name, Location_X, Location_Y};

        for(int i = 0; i <Station_Name.length; i++) {
        	System.out.println(String.format("%s, %s, %s, %s \n", Station_Name[i], Map_ID[i], Location_X[i], Location_Y[i]));
        }
	}
	private static String[] Y_Cord(ArrayList<String> a) {
		String[] Y_cord = new String[(a.size()-1)];
		int holder = 0;
		for(int i = 1; i< a.size(); i++) {
			Y_cord[holder] = ((a.get(i).split(","))[3]);
			holder++;
		}
		return Y_cord;
	}
	private static String[] X_Cord(ArrayList<String> a) {
		String[] X_cord = new String[(a.size()-1)];
		int holder = 0;
		for(int i = 1; i< a.size(); i++) {
			X_cord[holder] = ((a.get(i).split(","))[2]);
			holder++;
		}
		return X_cord;
	}
	private static String[] Map_ID(ArrayList<String> a) {
		String[] stop_name = new String[(a.size()-1)];
		int holder = 0;
		for(int i = 1; i< a.size(); i++) {
			stop_name[holder] = (a.get(i).split(","))[1];
			holder++;
		}
		return stop_name;
	}
	private static String[] Station_Name (ArrayList<String> A) {
		String[] stop_id = new String[(A.size()-1)];
		int holder = 0;
		for(int i = 1; i< A.size(); i++) {
			stop_id[holder] = (A.get(i).split(","))[0];
			holder++;
		}
		return stop_id;
		
	}
}
