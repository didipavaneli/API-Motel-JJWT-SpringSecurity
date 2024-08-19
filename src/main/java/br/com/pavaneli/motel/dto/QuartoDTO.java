package br.com.pavaneli.motel.dto;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import br.com.pavaneli.motel.entity.Quarto;
import br.com.pavaneli.motel.entity.TipoQuarto;
import br.com.pavaneli.motel.enums.StatusQuarto;
import br.com.pavaneli.motel.repository.TipoQuartoRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class QuartoDTO {
	private String idQuarto;
	private String numeroQuarto;
	private String descricaoQuarto;
	private String valorHoraQuarto;
	private String valorPernoiteQuarto;
	private String statusQuarto;
	private String tipoQuarto;
	private Long TipoQuartoId;	
	
	@Autowired
	private TipoQuartoRepository tipoQuartoRepository;
	
	
	  public QuartoDTO(Quarto quarto) {
	        BeanUtils.copyProperties(quarto, this);
	    }
	  
	  public Quarto toNovoQuarto(TipoQuartoRepository tipoQuartoRepository) {
	        Quarto quarto = new Quarto();
	        quarto.setNumero(Integer.parseInt(numeroQuarto));
	        quarto.setDescricao(descricaoQuarto);
	        quarto.setValorHora(Double.parseDouble(valorHoraQuarto));
	        quarto.setValorPernoite(Double.parseDouble(valorPernoiteQuarto));
	        quarto.setStatus(StatusQuarto.doValor(statusQuarto));

	        if (tipoQuarto != null && !tipoQuarto.isEmpty()) {
	            Long tipoQuartoId = Long.parseLong(tipoQuarto);
	            TipoQuarto tipoQuartoEntity = tipoQuartoRepository.findById(tipoQuartoId)
	                .orElseThrow(() -> new IllegalArgumentException("Tipo de quarto não encontrado"));
	            quarto.setTipoQuarto(tipoQuartoEntity);
	        }

	        return quarto;
	    }
	  
	  public String toDeleteQuarto() {
			String id = idQuarto;
			return id;
		}

}
