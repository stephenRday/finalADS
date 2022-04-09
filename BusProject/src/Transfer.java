
public class Transfer {
	
	//from_stop_id,to_stop_id,transfer_type,min_transfer_time
	
	int from_stop_id;
	int to_stop_id;
	int transfer_type;
	int min_transfer_time;
	
	public Transfer(String input) {
		
		String[] transfersArray = input.split(",");
		this.from_stop_id = Integer.parseInt(transfersArray[0]);
		this.to_stop_id = Integer.parseInt(transfersArray[1]);
		this.transfer_type = Integer.parseInt(transfersArray[2]);
		
		try {
			this.min_transfer_time = Integer.parseInt(transfersArray[3]);
		} catch (Exception e) {
			this.min_transfer_time = -1;
		}		
	}	
	
	/**
	 * @return the from_stop_id
	 */
	public int getFrom_stop_id() {
		return from_stop_id;
	}
	/**
	 * @return the to_stop_id
	 */
	public int getTo_stop_id() {
		return to_stop_id;
	}
	/**
	 * @return the transfer_type
	 */
	public int getTransfer_type() {
		return transfer_type;
	}
	/**
	 * @return the min_transfer_time
	 */
	public int getMin_transfer_time() {
		return min_transfer_time;
	}

}
