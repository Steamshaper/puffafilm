package org.steamshaper.ai.puffafilm.util;

import java.util.ArrayList;
import java.util.List;

public class TimeTracer {

	private List<TimeEntry> timestamps = new ArrayList<TimeEntry>(30);


	public void timerStart(String markerName){
		timestamps.add(new TimeEntry(markerName));
	}

	public String timerStop(){
		return timestamps.remove(timestamps.size()-1).stop().toString();
	}

	public Object timerStop(Object message) {
		return timestamps.remove(timestamps.size()-1).stop(message).toString();
	}







	private class TimeEntry{

		private String markerName;
		private long startTimestamp;
		private long stopTimestamp;

		public TimeEntry(String markerName) {
			this.markerName = markerName;
			this.startTimestamp = System.currentTimeMillis();
			this.stopTimestamp = startTimestamp;
		}

		public TimeEntry stop(){
			this.stopTimestamp = System.currentTimeMillis();
			return this;
		}
		public TimeEntry stop(Object message){
			markerName+= "stop message ["+message.toString()+"] ";
			this.stopTimestamp = System.currentTimeMillis();
			return this;
		}



		@Override
		public String toString() {
			return "Time for " + markerName +"\t"+ (stopTimestamp - startTimestamp)/1000F + " secs]";
		}



	}
}
