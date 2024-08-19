package br.com.pavaneli.motel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pavaneli.motel.dto.HospedagemDTO;
import br.com.pavaneli.motel.entity.Hospedagem;
import br.com.pavaneli.motel.repository.HospedagemRepository;

@Service
public class HospedagemService {
	@Autowired
	private HospedagemRepository hospedagemRepository;

	public List<HospedagemDTO> findAll() {
		List<Hospedagem> hospedagens = hospedagemRepository.findAll();
		return hospedagens.stream().map(HospedagemDTO::new).toList();

	}
	public void insert(HospedagemDTO hospedagemDto) {
		Hospedagem hospedagem = new Hospedagem(hospedagemDto);
        hospedagemRepository.save(hospedagem);
        
	}
	public HospedagemDTO update(HospedagemDTO hospedagemDto) {
		Hospedagem hospedagem = new Hospedagem(hospedagemDto);
        return new HospedagemDTO(hospedagemRepository.save(hospedagem));
    }
	public void delete(Long id) {
		Hospedagem hospedagem = hospedagemRepository.findById(id).get();
        hospedagemRepository.delete(hospedagem);
    }
	public HospedagemDTO findById(Long id) {
		return new HospedagemDTO(hospedagemRepository.findById(id).get());
	}
	
	
}