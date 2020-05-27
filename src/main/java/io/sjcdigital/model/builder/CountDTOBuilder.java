package io.sjcdigital.model.builder;

import io.sjcdigital.model.dto.CountDTO;

public class CountDTOBuilder {

	private CountDTO countDTO;
	
	public CountDTOBuilder() {
		this.countDTO = new CountDTO();
	}
	
	public CountDTOBuilder month(String month) {
		this.countDTO.setMonth(month);
		return this;
	}
	
	public CountDTOBuilder year(String year) {
		this.countDTO.setYear(year);
		return this;
	}
	
	public CountDTOBuilder funeral(String funeral) {
		this.countDTO.setFuneral(funeral);
		return this;
	}
	
	public CountDTOBuilder count(long count) {
		this.countDTO.setCount(count);
		return this;
	}
	
	public CountDTOBuilder nonZeroAge(boolean nonZeroAge) {
		this.countDTO.setNonZeroAge(nonZeroAge);
		return this;
	}
	
	public CountDTO build() {
		return this.countDTO;
	}
	
}
