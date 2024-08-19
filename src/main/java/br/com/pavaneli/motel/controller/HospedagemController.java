package br.com.pavaneli.motel.controller;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import br.com.pavaneli.motel.dto.HospedagemDTO;
import br.com.pavaneli.motel.dto.ItemPedidoDTO;
import br.com.pavaneli.motel.entity.Hospedagem;
import br.com.pavaneli.motel.entity.ItemPedido;
import br.com.pavaneli.motel.entity.Quarto;
import br.com.pavaneli.motel.enums.StatusHospedagem;
import br.com.pavaneli.motel.enums.StatusQuarto;
import br.com.pavaneli.motel.repository.HospedagemRepository;
import br.com.pavaneli.motel.repository.ItemPedidoRepository;
import br.com.pavaneli.motel.repository.QuartoRepository;

@Controller
@RequestMapping("hospedagem")
public class HospedagemController {
	@Autowired
	private QuartoRepository quartoRepository;
	@Autowired
    private HospedagemRepository hospedagemRepository;
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	@GetMapping("livres")
	@ResponseBody
	public List<Quarto> listarQuartosLivres() {
		return quartoRepository.findByStatus(StatusQuarto.LIVRE, Sort.by("descricao").ascending());

	}
	
	@GetMapping("todos")
	@ResponseBody
	public List<Quarto> listarTodosQuartos() {
	    return quartoRepository.findAll(Sort.by("id").ascending());
	}
	
	@GetMapping("cadastrar")
	public String listarQuartos(Model model) {
		List<Hospedagem> hospedagens = hospedagemRepository.findAll(Sort.by("id").ascending());	    
	    List<Quarto> quartos = quartoRepository.findAll(Sort.by("descricao").ascending());
	    model.addAttribute("hospedagens", hospedagens);
	    model.addAttribute("quartos", quartos);	    
	    return "hospedagem/cadastrar";
	}
	
	@PostMapping("novo")
	public String novo(@ModelAttribute HospedagemDTO requisicao, RedirectAttributes redirectAttributes) {
	    System.out.println("Método novo() chamado");
	    System.out.println("Dados recebidos: " + requisicao.toString());
	    try {
	        if (requisicao.getQuartoId() == null) {
	            throw new IllegalArgumentException("ID do quarto não pode ser nulo");
	        }

	        Quarto quarto = quartoRepository.findById(requisicao.getQuartoId())
	            .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));

	        System.out.println("Quarto encontrado: " + quarto.getId());

	        if (quarto.getStatus() != StatusQuarto.LIVRE) {
	            System.out.println("Quarto não está livre");
	            redirectAttributes.addFlashAttribute("erro", "O quarto selecionado não está disponível.");
	            return "redirect:/hospedagem/cadastrar";
	        }

	        Hospedagem hospedagem = requisicao.toNovaHospedagem(quartoRepository);
	        System.out.println("Hospedagem criada: " + hospedagem.toString());
	        
	        // Atualiza o status do quarto
	        quarto.setStatus(StatusQuarto.OCUPADO);
	        quartoRepository.save(quarto);

	        hospedagemRepository.save(hospedagem);
	        
	        System.out.println("Hospedagem salva com sucesso");
	        redirectAttributes.addFlashAttribute("sucesso", "Hospedagem criada com sucesso.");
	        return "redirect:/hospedagem/sucesso";
	    } catch (Exception e) {
	        System.out.println("Erro ao criar hospedagem: " + e.getMessage());
	        e.printStackTrace();
	        redirectAttributes.addFlashAttribute("erro", "Erro ao criar hospedagem: " + e.getMessage());
	        return "redirect:/hospedagem/cadastrar";
	    }
	}
	@GetMapping("sucesso")
    public String formsucesso() {
        return "hospedagem/sucesso";
    }
	
	@GetMapping("fechar")
	public String formdelete(Model model) {
	    List<Hospedagem> hospedagens = hospedagemRepository.findByStatus(StatusHospedagem.ABERTA, Sort.by("descricao").ascending());
	    model.addAttribute("hospedagens", hospedagens);
	    return "hospedagem/fechar";
	}
	@GetMapping("pedidosporhospedagem/{hospedagemId}")
	@ResponseBody
	public String getPedidosPorHospedagem(@PathVariable Long hospedagemId) throws JsonProcessingException {
	    List<ItemPedido> pedidos = itemPedidoRepository.findByHospedagemId(hospedagemId);
	    List<ItemPedidoDTO> pedidosDTO = pedidos.stream()
	        .map(this::convertToDTO)
	        .collect(Collectors.toList());
	    
	    ObjectMapper mapper = new ObjectMapper();
	    mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	    return mapper.writeValueAsString(pedidosDTO);
	}
	private ItemPedidoDTO convertToDTO(ItemPedido itemPedido) {
	    ItemPedidoDTO dto = new ItemPedidoDTO();
	    dto.setId(itemPedido.getId());
	    dto.setProdutoNome(itemPedido.getProduto().getDescricao());
	    dto.setQuantidadePedido(itemPedido.getQuantidade());
	    dto.setPrecoTotal(itemPedido.getPrecoTotal());
	    return dto;
	}
	@GetMapping("getCheckinTime/{hospedagemId}")
	@ResponseBody
	public Map<String, Object> getCheckinTime(@PathVariable Long hospedagemId) {
	    Hospedagem hospedagem = hospedagemRepository.findById(hospedagemId).orElse(null);
	    Map<String, Object> response = new HashMap<>();
	    if (hospedagem != null) {
	        response.put("checkinTime", hospedagem.getCheckin());
	    }
	    return response;
	}
	@GetMapping("quartoInfo/{hospedagemId}")
	@ResponseBody
	public Map<String, Object> getQuartoInfo(@PathVariable Long hospedagemId) {
	    Map<String, Object> response = new HashMap<>();
	    Hospedagem hospedagem = hospedagemRepository.findById(hospedagemId).orElse(null);
	    if (hospedagem != null) {
	        Quarto quarto = hospedagem.getQuarto();
	        response.put("valorHora", quarto.getValorHora());
	        response.put("valorPernoite", quarto.getValorPernoite());
	    }
	    return response;
	}
	@PostMapping("checkout")
	public String alterar(@ModelAttribute HospedagemDTO requisicao) {
	    System.out.println("Valor da Hospedagem recebido: " + requisicao.getValorHospedagem());
	    System.out.println("Valor Total da Hospedagem recebido: " + requisicao.getValorTotalHospedagem());

	    // Verificar se os campos não são null antes de continuar
	    if (requisicao.getIdHospedagem() == null || requisicao.getIdHospedagem().trim().isEmpty()) {
	        throw new IllegalArgumentException("ID da Hospedagem é obrigatório");
	    }
	    if (requisicao.getValorHospedagem() == null || requisicao.getValorHospedagem().trim().isEmpty()) {
	        throw new IllegalArgumentException("Valor da Hospedagem é obrigatório");
	    }
	    if (requisicao.getValorTotalHospedagem() == null || requisicao.getValorTotalHospedagem().trim().isEmpty()) {
	        throw new IllegalArgumentException("Valor Total da Hospedagem é obrigatório");
	    }

	    Hospedagem hospedagem = hospedagemRepository.findById(Long.parseLong(requisicao.getIdHospedagem()))
	            .orElseThrow(() -> new IllegalArgumentException("Hospedagem não encontrada"));

	    // Usar um método de parsing mais robusto
	    double valorHospedagem = parseMonetaryValue(requisicao.getValorHospedagem());
	    double valorTotal = parseMonetaryValue(requisicao.getValorTotalHospedagem());
	    
	    

	    // Atualizar apenas os campos necessários
	    hospedagem.setValor_hospedagem(valorHospedagem);
	    hospedagem.setValor_total(valorTotal);
	    LocalDateTime agora = LocalDateTime.now();
	    Date dataAtual = Date.from(agora.atZone(ZoneId.systemDefault()).toInstant());
	    hospedagem.setCheckout(dataAtual);
	    hospedagem.setStatus(StatusHospedagem.FECHADA);

	    // Atualizar status do quarto se necessário
	    if (hospedagem.getQuarto() != null) {
	        hospedagem.getQuarto().setStatus(StatusQuarto.MANUTENCAO);
	    }

	    hospedagemRepository.save(hospedagem);
	    return "hospedagem/sucesso";
	}

	private double parseMonetaryValue(String value) {
	    // Remove qualquer caracter não numérico, exceto o ponto decimal
	    String cleanValue = value.replaceAll("[^\\d.]", "");
	    try {
	        return Double.parseDouble(cleanValue);
	    } catch (NumberFormatException e) {
	        throw new IllegalArgumentException("Valor inválido: " + value);
	    }
	}
	@GetMapping("cancelar")
    public String formdelete() {
        return "hospedagem/cancelar";
    }
	@DeleteMapping("cancelar/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (hospedagemRepository.existsById(id)) {
            hospedagemRepository.deleteById(id);
            return ResponseEntity.ok("Hospedagem deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Hospedagem não encontrado.");
        }
    }
	@GetMapping("todoshospedagem")
    @ResponseBody
    public List<Hospedagem> listarTodosHospedagem() {
        return hospedagemRepository.findAll(Sort.by("id").ascending());
    }
	
	@GetMapping("abertas")
	@ResponseBody
	public List<Hospedagem> listarHospedagensAbertas() {
	    return hospedagemRepository.findByStatus(StatusHospedagem.ABERTA, Sort.by("id").ascending());
	}
	@PostMapping("cancelar/{id}")
	@ResponseBody
	public ResponseEntity<String> cancelarHospedagem(@PathVariable Long id) {
	    try {
	        Hospedagem hospedagem = hospedagemRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Hospedagem não encontrada"));
	        
	        if (hospedagem.getStatus() != StatusHospedagem.ABERTA) {
	            return ResponseEntity.badRequest().body("Apenas hospedagens abertas podem ser canceladas.");
	        }

	        // Verificar se existem itens de pedido vinculados
	        List<ItemPedido> itensPedido = itemPedidoRepository.findByHospedagemId(id);
	        if (!itensPedido.isEmpty()) {
	            return ResponseEntity.badRequest().body("Não é possível cancelar a hospedagem pois existem itens de pedido vinculados.");
	        }

	        hospedagem.setStatus(StatusHospedagem.CANCELADA);
	        
	        // Liberar o quarto
	        Quarto quarto = hospedagem.getQuarto();
	        if (quarto != null) {
	            quarto.setStatus(StatusQuarto.LIVRE);
	            quartoRepository.save(quarto);
	        }
	        
	        hospedagemRepository.save(hospedagem);
	        
	        return ResponseEntity.ok("Hospedagem cancelada com sucesso.");
	    } catch (Exception e) {
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao cancelar hospedagem: " + e.getMessage());
	    }
	}

}