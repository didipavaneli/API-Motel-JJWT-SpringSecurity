package br.com.pavaneli.motel.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.pavaneli.motel.dto.ProdutoDTO;
import br.com.pavaneli.motel.entity.Produto;
import br.com.pavaneli.motel.repository.ProdutoRepository;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository produtoRepository;

	public List<ProdutoDTO> findAll(){
		List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream().map(ProdutoDTO::new).toList();
	}
	public void insert(ProdutoDTO produtoDto) {
		Produto produto = new Produto(produtoDto);
		produtoRepository.save(produto);
		
	}
	public ProdutoDTO update(ProdutoDTO produtoDto) {
        Produto produto = new Produto(produtoDto);
        return new ProdutoDTO(produtoRepository.save(produto));
    }
	public void delete(Long id) {
		Produto produto = produtoRepository.findById(id).get();
        produtoRepository.delete(produto);
    }
		
	public ProdutoDTO findById(Long id) {
        return new ProdutoDTO(produtoRepository.findById(id).get());
    }
}
