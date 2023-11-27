package io.github.ekan.api.enumeration;

public enum TipoDocumentoEnum {
	
	CPF("CPF"), 
	RG("RG"),
	PASSAPORTE("PASSAPORTE");
	
	private String value;
	
	private TipoDocumentoEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
	
	public static TipoDocumentoEnum getEnum(String value) {
		
		for(TipoDocumentoEnum t : values()) {
			if(value.equals(t.getValue())) {
				return t;
			}
		}
		
		throw new RuntimeException("Type not found.");
	}

}
