import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class StopTime {
	//trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled
	
	int trip_id;
	LocalTime arrival_time;
	LocalTime departure_time;
	int stop_id;
	int stop_sequence;
	int pickup_type;
	int drop_off_type;
	int shape_dist_traveled;
	
public StopTime(String input) {
		
		String[] stopTimesArray = input.split(",");
		this.trip_id = Integer.parseInt(stopTimesArray[0]);	
		
		String timeCorrectFormat = stopTimesArray[1].replace(" ", "0");
		//System.out.println(timeCorrectFormat);
		Time arrTimeForm = Time.valueOf(timeCorrectFormat);
		//System.out.println(arrTimeForm);
		this.arrival_time = arrTimeForm.toLocalTime();
		
		timeCorrectFormat = stopTimesArray[2].replace(" ", "0");
		//System.out.println(timeCorrectFormat);
		Time leaTimeForm = Time.valueOf(timeCorrectFormat);
		//System.out.println(leaTimeForm);
		this.departure_time = leaTimeForm.toLocalTime();
		
		this.stop_id = Integer.parseInt(stopTimesArray[3]);
		this.stop_sequence = Integer.parseInt(stopTimesArray[4]);
		
		try {
			this.pickup_type = Integer.parseInt(stopTimesArray[5]);
		} catch (Exception e) {
			this.pickup_type = -1;
		}
		
		this.drop_off_type = Integer.parseInt(stopTimesArray[6]);
		this.shape_dist_traveled = Integer.parseInt(stopTimesArray[7]);	
	}	
	
	public int getTrip_id() {
		return trip_id;
	}
	public LocalTime getArrival_time() {
		return arrival_time;
	}
	public LocalTime getDeparture_time() {
		return departure_time;
	}
	public int getStop_id() {
		return stop_id;
	}
	public int getStop_sequence() {
		return stop_sequence;
	}
	public int getPickup_type() {
		return pickup_type;
	}
	public int getDrop_off_type() {
		return drop_off_type;
	}
	public int getShape_dist_traveled() {
		return shape_dist_traveled;
	}

	@Override
	public String toString() {
		return "stopTimes [trip_id=" + trip_id + ", arrival_time=" + arrival_time + ", departure_time=" + departure_time
				+ ", stop_id=" + stop_id + ", stop_sequence=" + stop_sequence + ", pickup_type=" + pickup_type
				+ ", drop_off_type=" + drop_off_type + ", shape_dist_traveled=" + shape_dist_traveled + "]";
	}
}
