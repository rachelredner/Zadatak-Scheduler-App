package org.zadatak.zadatak;

public class TaskBlock {
	public Integer startTime;
	public Integer endTime;
	public Task task;
	
	@Override
	public int hashCode() {
		int hash = startTime.hashCode();
		return hash;
	}
}
