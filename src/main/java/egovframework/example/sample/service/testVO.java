package egovframework.example.sample.service;

public class testVO {

	/*출발역 코드*/
	private int dept_station_code;
	
	/*도착역 코드*/
	private int dest_station_code;
	
	/*주중, 토요일, 공휴일*/
	private String week;
	
	public int getDept_station_code() {
		return dept_station_code;
	}

	public void setDept_station_code(int dept_station_code) {
		this.dept_station_code = dept_station_code;
	}

	public int getDest_station_code() {
		return dest_station_code;
	}

	public void setDest_station_code(int dest_station_code) {
		this.dest_station_code = dest_station_code;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	
}
