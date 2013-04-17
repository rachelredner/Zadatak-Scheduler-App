package org.zadatak.zadatak;

public class TaskBlock {
	public Integer startTime;
	public Integer endTime;
	public Task task;
	
	// A constructor to create a new task block with all the elements
	public TaskBlock(Integer _startTime, Integer _endTime, Task _task) {
		startTime = _startTime;
		endTime = _endTime;
		task = _task;
	}
	
	// A constructor to create a new task block with only the hashable element
	// used for lookups of this object in a hash table where the end time and task
	// are trying to be determined
	public TaskBlock(Integer _startTime) {
		startTime = _startTime;
	}
	
	
	// A hashing function to allow lookup of the taskblock with only the start time
	@Override
	public int hashCode() {
		int hash = startTime.hashCode();
		return hash;
	}
}
