package br.com.pavaneli.motel.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pavaneli.motel.entity.Quarto;
import br.com.pavaneli.motel.enums.StatusQuarto;

public interface QuartoRepository extends JpaRepository<Quarto, Long>{
	

	List<Quarto> findByStatus(StatusQuarto status, Sort sort);

}
