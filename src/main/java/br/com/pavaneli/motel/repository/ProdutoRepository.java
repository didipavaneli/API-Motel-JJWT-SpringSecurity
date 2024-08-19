package br.com.pavaneli.motel.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pavaneli.motel.entity.Produto;

public interface ProdutoRepository extends JpaRepository<Produto, Long>{

}
