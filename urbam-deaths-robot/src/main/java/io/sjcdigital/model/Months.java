package io.sjcdigital.model;

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
	
	public Integer getValue() {
		return this.value; 
	}
	
	
	 
}
