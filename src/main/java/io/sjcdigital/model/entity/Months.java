package io.sjcdigital.model.entity;

/**
 * 
 * @author pedro-hos
 *
 */
public enum Months {
	
	JANEIRO(1),
	FEVEREIRO(2),
	MARCO(3),
	ABRIL(4),
	MAIO(5),
	JUNHO(6),
	JULHO(7),
	AGOSTO(8),
	SETEMBRO(9),
	OUTUBRO(10),
	NOVEMBRO(11),
	DEZEMBRO(12);
	
	private final Integer value;
	
	private Months(Integer value) { 
		this.value = value; 
	}
	
	public static Months[] getMonthByValue(String ... values) {
		
		Months[] months = new Months[values.length];
		
		for (int i = 0; i < values.length; i++) {
			months[i] = withValue(Integer.valueOf(values[i]));
		}
		
		return months;
	}
	
	
	public Integer value() {
		return this.value; 
	}
	
	public static Months withValue(final Integer value) {
		
		Months[] monthList = values();
		
		for (int i = 0; i < monthList.length; i++) {
			if(monthList[i].value == value) {
				return monthList[i];
			}
		}
		
		return null;
	}
	
}
