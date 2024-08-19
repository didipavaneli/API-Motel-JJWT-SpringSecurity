package br.com.pavaneli.motel.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pavaneli.motel.entity.Hospedagem;
import br.com.pavaneli.motel.enums.StatusHospedagem;

public interface HospedagemRepository extends JpaRepository<Hospedagem, Long>{
	List<Hospedagem> findByStatus(StatusHospedagem status, Sort sort);
	Hospedagem findByQuartoIdAndStatus(Long quartoId, StatusHospedagem status);

}
