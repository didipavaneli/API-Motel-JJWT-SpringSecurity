package br.com.pavaneli.motel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pavaneli.motel.dto.TipoQuartoDTO;
import br.com.pavaneli.motel.entity.TipoQuarto;
import br.com.pavaneli.motel.repository.TipoQuartoRepository;

@Service
public class TipoQuartoService {
	
	@Autowired
	private TipoQuartoRepository tipoQuartoRepository;	
	
	
	public List<TipoQuartoDTO> findAll(){
		List<TipoQuarto> tipoQuartos = tipoQuartoRepository.findAll();
		return tipoQuartos.stream().map(TipoQuartoDTO::new).toList();
		
	}
	public void insert(TipoQuartoDTO tipoQuartoDto){
		TipoQuarto tipoQuarto = new TipoQuarto(tipoQuartoDto);
        tipoQuartoRepository.save(tipoQuarto);
	}
	public TipoQuartoDTO update(TipoQuartoDTO tipoQuartoDto){
        TipoQuarto tipoQuarto = new TipoQuarto(tipoQuartoDto);
        return new TipoQuartoDTO(tipoQuartoRepository.save(tipoQuarto));
    }
	public void delete(Long id) {
        TipoQuarto tipoQuarto = tipoQuartoRepository.findById(id).get();
        tipoQuartoRepository.delete(tipoQuarto);
    }
	public TipoQuartoDTO findById(Long id) {
        return new TipoQuartoDTO(tipoQuartoRepository.findById(id).get());
    }

}
