package io.sjcdigital.model.dto;

import io.sjcdigital.model.builder.CountDTOBuilder;

public class CountDTO {
	
	private String month;
	private String year;
	private String funeral;
	private long count;
	private boolean nonZeroAge = false;
	
	public static CountDTOBuilder create() {
		return new CountDTOBuilder();
	}
	
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}
	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getFuneral() {
		return funeral;
	}
	public void setFuneral(String funeral) {
		this.funeral = funeral;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public boolean isNonZeroAge() {
		return nonZeroAge;
	}
	public void setNonZeroAge(boolean nonZeroAge) {
		this.nonZeroAge = nonZeroAge;
	}
	
}
