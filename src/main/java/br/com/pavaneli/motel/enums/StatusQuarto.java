package br.com.pavaneli.motel.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum StatusQuarto {
	LIVRE("L", "Livre"), 
	OCUPADO("O", "Ocupado"), 
	RESERVADO("R", "Reservado"), 
	MANUTENCAO("M", "Manutencao"), 
	BLOQUEADO("B", "Bloqueado");
	
	
	private String codigo;
	private String descricao;
	
    private StatusQuarto(String codigo, String descricao) {
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
	public static StatusQuarto doValor(String codigo) {
		if(codigo.equals("L")) {
			return LIVRE;
		}else if(codigo.equals("O")) {
			return OCUPADO;
		}else if(codigo.equals("R")) {
			return RESERVADO;
		}else if(codigo.equals("M")) {
			return MANUTENCAO;
		}else if(codigo.equals("B")) {
			return BLOQUEADO;
		}else {
			return null;
		}
    

	}
}
