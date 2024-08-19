package br.com.pavaneli.motel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pavaneli.motel.dto.QuartoDTO;
import br.com.pavaneli.motel.entity.Quarto;
import br.com.pavaneli.motel.repository.QuartoRepository;

@Service
public class QuartoService {

	@Autowired
	private QuartoRepository quartoRepository;
	
	public List<QuartoDTO> findAll(){
		List<Quarto> quartos = quartoRepository.findAll();
		return quartos.stream().map(QuartoDTO::new).toList();
    }
	public void insert(QuartoDTO quartoDto) {
		Quarto quarto = new Quarto(quartoDto);
        quartoRepository.save(quarto);
	}
	public QuartoDTO update(QuartoDTO quartoDto) {
        Quarto quarto = new Quarto(quartoDto);
        return new QuartoDTO(quartoRepository.save(quarto));
    }
	public void delete(Long id) {
        Quarto quarto = quartoRepository.findById(id).get();
		quartoRepository.delete(quarto);
    }
	public QuartoDTO findById(Long id) {
        return new QuartoDTO(quartoRepository.findById(id).get());
    }
}
