package br.com.pavaneli.motel.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pavaneli.motel.entity.ItemPedido;

public interface ItemPedidoRepository extends JpaRepository<ItemPedido, Long>{
	 List<ItemPedido> findByHospedagemId(Long hospedagemId);
}
