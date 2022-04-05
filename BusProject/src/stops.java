
public class stops {
	// stop_id,stop_code,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url,location_type,parent_station
	
	int stop_id;
	int stop_code;
	String stop_name;
	String stop_desc;
	double stop_lat;
	double stop_lon;
	String zone_id; 
	
	public stops(String input) {
		
		String[] stopsArray = input.split(",");
		this.stop_id = Integer.parseInt(stopsArray[0]);
		
		try {
			this.stop_code = Integer.parseInt(stopsArray[1]);
		} catch (Exception e) {
			this.stop_code = -1;
		}
		
		
		this.stop_name = stopsArray[2];
		this.stop_desc = stopsArray[3];
		this.stop_lat = Double.parseDouble(stopsArray[4]);
		this.stop_lon = Double.parseDouble(stopsArray[5]);
		this.zone_id = stopsArray[6];

	}

	public int getStop_id() {
		return stop_id;
	}


	public int getStop_code() {
		return stop_code;
	}


	public String getStop_name() {
		return stop_name;
	}


	public String getStop_desc() {
		return stop_desc;
	}


	public double getStop_lat() {
		return stop_lat;
	}


	public double getStop_lon() {
		return stop_lon;
	}
	

	public String getZone_id() {
		return zone_id;
	}

	@Override
	public String toString() {
		return "stops [stop_id=" + stop_id + ", stop_code=" + stop_code + ", stop_name=" + stop_name + ", stop_desc="
				+ stop_desc + ", stop_lat=" + stop_lat + ", stop_lon=" + stop_lon + ", zone_id=" + zone_id + "]";
	}
	
}
