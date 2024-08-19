package br.com.pavaneli.motel.dto;

import org.springframework.beans.BeanUtils;

import br.com.pavaneli.motel.entity.Produto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ProdutoDTO {
	private String idProduto;	
	private String descricaoProduto;	
	private String precoProduto;	
	private String unidadeProduto;	
	private String statusProduto;
	
	  public ProdutoDTO(Produto produto) {
	        BeanUtils.copyProperties(produto, this);
	    }
	  
	  public Produto toNovoProduto() {
			Produto produto = new Produto();
			produto.setDescricao(descricaoProduto);
			produto.setUnidade(unidadeProduto);
			Double precoCerto = Double.parseDouble(precoProduto);
			produto.setPreco(precoCerto);
			produto.setStatus(statusProduto);
			return produto;
		}
	  public Produto toAlterarProduto() {
		    Produto produto = new Produto();
		    if (idProduto != null && !idProduto.isEmpty()) {
		        produto.setId(Long.parseLong(idProduto));
		    }
		    produto.setDescricao(descricaoProduto);
		    produto.setUnidade(unidadeProduto);
		    produto.setPreco(Double.parseDouble(precoProduto));
		    produto.setStatus(statusProduto);
		    return produto;
		}
	  
	  public String toDeleteProduto() {
			String id = idProduto;
			return id;
		}

}
