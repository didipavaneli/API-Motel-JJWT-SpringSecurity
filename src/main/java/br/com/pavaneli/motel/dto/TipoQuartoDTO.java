package br.com.pavaneli.motel.dto;

import org.springframework.beans.BeanUtils;

import br.com.pavaneli.motel.entity.TipoQuarto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TipoQuartoDTO {
	private String idTipoQuarto;			
	private String descricaoTipoQuarto;	
	
	
	  public TipoQuartoDTO(TipoQuarto tipoQuarto) {
	        BeanUtils.copyProperties(tipoQuarto, this);
	    }
	  
	  public TipoQuarto toNovoTipoQuarto() {
			TipoQuarto tipoQuarto = new TipoQuarto();
			tipoQuarto.setDescricao(descricaoTipoQuarto);
			
			return tipoQuarto;
		}
	  
	  public TipoQuarto toAlterarTipoQuarto() {
			TipoQuarto tipoQuarto = new TipoQuarto();
			if (idTipoQuarto != null && !idTipoQuarto.isEmpty()) {
				tipoQuarto.setId(Long.parseLong(idTipoQuarto));
			}
			tipoQuarto.setDescricao(descricaoTipoQuarto);
			
			return tipoQuarto;
		}
	  
	  public String toDeleteTipoQuarto() {
			String id = idTipoQuarto;
			return id;
		}


}
