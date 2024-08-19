package br.com.pavaneli.motel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusHospedagem {
	ABERTA("A", "Aberta"), 
	FECHADA("F", "Fechada"), 
	CANCELADA("C", "Cancelada"); 
	
	
	
	private String codigo;
	private String descricao;
	
    private StatusHospedagem(String codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }
    @JsonValue
	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
    
	@JsonCreator
	public static StatusHospedagem doValor(String codigo) {
		if(codigo.equals("A")) {
			return ABERTA;
		}else if(codigo.equals("F")) {
			return FECHADA;		
		}else if(codigo.equals("C")) {
			return CANCELADA;
		}else {
			return null;
		}
    

	}
}
