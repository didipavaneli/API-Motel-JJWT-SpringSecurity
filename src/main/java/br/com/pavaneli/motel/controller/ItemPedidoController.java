package br.com.pavaneli.motel.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.pavaneli.motel.dto.ItemPedidoDTO;
import br.com.pavaneli.motel.entity.Hospedagem;
import br.com.pavaneli.motel.entity.ItemPedido;
import br.com.pavaneli.motel.entity.Produto;
import br.com.pavaneli.motel.enums.StatusHospedagem;
import br.com.pavaneli.motel.repository.HospedagemRepository;
import br.com.pavaneli.motel.repository.ItemPedidoRepository;
import br.com.pavaneli.motel.repository.ProdutoRepository;
import br.com.pavaneli.motel.service.ItemPedidoService;

@Controller
@RequestMapping("pedido")
public class ItemPedidoController {
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private HospedagemRepository hospedagemRepository;
	@Autowired	
	private ItemPedidoService itemPedidoService; 

	
	@GetMapping("cadastrar")
    public String listarPedidos(Model model) {
        List<ItemPedido> itemPedidos = itemPedidoRepository.findAll(Sort.by("id").ascending());    
        List<Hospedagem> hospedagens = hospedagemRepository.findByStatus(StatusHospedagem.ABERTA, Sort.by("descricao").ascending());
        List<Produto> produtos = produtoRepository.findAll(Sort.by("id").ascending());   
        
        model.addAttribute("hospedagens", hospedagens); 
         
        model.addAttribute("itemPedidos", itemPedidos);
        model.addAttribute("produtos", produtos);
        return "pedido/cadastrar";
    }
	@PostMapping("novo")
	public String novo(@ModelAttribute ItemPedidoDTO requisicao) {
	    ItemPedido itemPedido = requisicao.toNovoItemPedido(produtoRepository, hospedagemRepository);
	    
	    // Salvar o ItemPedido e a Hospedagem atualizada
	    itemPedidoRepository.save(itemPedido);
	    if (itemPedido.getHospedagem() != null) {
	        hospedagemRepository.save(itemPedido.getHospedagem());
	    }
	    
	    return "redirect:/pedido/sucesso";
	}
		
	@GetMapping("sucesso")
	public String sucesso() {
	    return "pedido/sucesso";
	}
	
	
	@GetMapping("deletar")
	public String formdelete(Model model) {
	    List<Hospedagem> hospedagens = hospedagemRepository.findByStatus(StatusHospedagem.ABERTA, Sort.by("descricao").ascending());
	    model.addAttribute("hospedagens", hospedagens);
	    return "pedido/deletar";
	}
	
	@GetMapping("pedidosporhospedagem/{hospedagemId}")
	@ResponseBody
	public String getPedidosPorHospedagem(@PathVariable Long hospedagemId) throws JsonProcessingException {
	    List<ItemPedido> pedidos = itemPedidoRepository.findByHospedagemId(hospedagemId);
	    List<ItemPedidoDTO> pedidosDTO = pedidos.stream()
	        .map(this::convertToDto)
	        .collect(Collectors.toList());
	    
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	    return mapper.writeValueAsString(pedidosDTO);
	}

	private ItemPedidoDTO convertToDto(ItemPedido itemPedido) {
	    ItemPedidoDTO dto = new ItemPedidoDTO();
	    dto.setId(itemPedido.getId());
	    dto.setProdutoNome(itemPedido.getProduto().getDescricao());
	    dto.setQuantidadePedido(itemPedido.getQuantidade());
	    dto.setPrecoTotal(itemPedido.getPrecoTotal());
	    return dto;
	}
	 @DeleteMapping("deletar/{id}")
	    @ResponseBody
	    public ResponseEntity<String> delete(@PathVariable Long id) {
	        try {
	            itemPedidoService.deletarEDesvincularPedido(id);
	            return ResponseEntity.ok("Pedido deletado e desvinculado com sucesso.");
	        } catch (Exception e) {
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
	        }
	    }

}
