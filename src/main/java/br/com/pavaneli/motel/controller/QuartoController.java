package br.com.pavaneli.motel.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pavaneli.motel.dto.QuartoDTO;
import br.com.pavaneli.motel.entity.Quarto;
import br.com.pavaneli.motel.entity.TipoQuarto;
import br.com.pavaneli.motel.enums.StatusHospedagem;
import br.com.pavaneli.motel.enums.StatusQuarto;
import br.com.pavaneli.motel.repository.QuartoRepository;
import br.com.pavaneli.motel.repository.TipoQuartoRepository;

@Controller
@RequestMapping("quarto")
@CrossOrigin
public class QuartoController {
	
	
	@Autowired
	private QuartoRepository quartoRepository;
	@Autowired
	private TipoQuartoRepository tipoQuartoRepository;
	
	@GetMapping("todos")
	@ResponseBody
	public List<Quarto> listarTodosQuartos() {
	    return quartoRepository.findAll(Sort.by("id").ascending());
	}
	
	
	
	@GetMapping("cadastrar")
    public ModelAndView novo() {
        ModelAndView mv = new ModelAndView("quarto/cadastrar");
        mv.addObject(new Quarto());

        // Carregar tipos de quarto
        List<TipoQuarto> listaTipoQuarto = tipoQuartoRepository.findAll(Sort.by("descricao").ascending());
        mv.addObject("listaTipoQuarto", listaTipoQuarto);

        // Carregar status dos quartos
        mv.addObject("statusQuartoList", StatusQuarto.values());

        return mv;
    }
	

	@GetMapping("alterar")
	public String listarQuartos(Model model) {
	    List<Quarto> quartos = quartoRepository.findAll(Sort.by("id").ascending());
	    List<TipoQuarto> listaTipoQuarto = tipoQuartoRepository.findAll(Sort.by("descricao").ascending());
	    model.addAttribute("quartos", quartos);
	    model.addAttribute("listaTipoQuarto", listaTipoQuarto);
	    model.addAttribute("statusQuartoList", StatusQuarto.values());
	    return "quarto/alterar";
	}
	
	@GetMapping("alterarstatus")
	public String formStatus(Model model) {
	    List<Quarto> quartos = quartoRepository.findAll(Sort.by("id").ascending());
	    model.addAttribute("quartos", quartos);
	    model.addAttribute("statusQuartoList", StatusQuarto.values());
	    return "quarto/alterarstatus";
	}
	@GetMapping("deletar")
    public String formdelete() {
        return "quarto/deletar";
    }
	@GetMapping("sucesso")
    public String formsucesso() {
        return "quarto/sucesso";
    }
	 @PostMapping("novo")
	    public String novo(@ModelAttribute QuartoDTO requisicao) {
	        Quarto quarto = requisicao.toNovoQuarto(tipoQuartoRepository);
	        quartoRepository.save(quarto);
	        return "redirect:/quarto/sucesso";
	    }

	 @PostMapping("alterarstatus")
	 public String alterarStatus(@ModelAttribute QuartoDTO requisicao, BindingResult result, RedirectAttributes redirectAttributes) {
	     if (result.hasErrors()) {
	         return "quarto/alterarstatus";
	     }
	     
	     // Verificação do ID
	     if (requisicao.getIdQuarto() == null || requisicao.getIdQuarto().isEmpty()) {
	         throw new IllegalArgumentException("O ID do quarto não pode ser nulo.");
	     }
	     
	     Long idQuarto = Long.parseLong(requisicao.getIdQuarto());
	     Quarto quarto = quartoRepository.findById(idQuarto)
	         .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));
	     
	     // Verificar se o quarto está vinculado a uma hospedagem aberta
	     boolean temHospedagemAberta = quarto.getHospedagemAtual().stream()
	         .anyMatch(hospedagem -> hospedagem.getStatus() == StatusHospedagem.ABERTA);
	     
	     if (temHospedagemAberta) {
	         redirectAttributes.addFlashAttribute("erro", "Não é possível alterar o status do quarto pois ele está vinculado a uma hospedagem aberta.");
	         return "redirect:/quarto/alterarstatus";
	     }
	     
	     // Se não estiver vinculado a uma hospedagem aberta, altera o status
	     quarto.setStatus(StatusQuarto.valueOf(requisicao.getStatusQuarto()));
	     quartoRepository.save(quarto);
	     
	     redirectAttributes.addFlashAttribute("sucesso", "Status do quarto alterado com sucesso.");
	     return "redirect:/quarto/sucesso";
	 }
	
	
	
	 @PostMapping("alterar")
	 public String alterarQuarto(@ModelAttribute QuartoDTO requisicao, BindingResult result, RedirectAttributes redirectAttributes) {
	     if (result.hasErrors()) {
	         return "quarto/alterar";
	     }

	     // Verificação do ID
	     if (requisicao.getIdQuarto() == null || requisicao.getIdQuarto().isEmpty()) {
	         throw new IllegalArgumentException("O ID do quarto não pode ser nulo.");
	     }

	     Long idQuarto = Long.parseLong(requisicao.getIdQuarto());
	     Quarto quarto = quartoRepository.findById(idQuarto)
	         .orElseThrow(() -> new RuntimeException("Quarto não encontrado"));

	     quarto.setNumero(Integer.parseInt(requisicao.getNumeroQuarto()));
	     quarto.setDescricao(requisicao.getDescricaoQuarto());
	     quarto.setValorHora(Double.parseDouble(requisicao.getValorHoraQuarto()));
	     quarto.setValorPernoite(Double.parseDouble(requisicao.getValorPernoiteQuarto()));

	     TipoQuarto tipoQuarto = tipoQuartoRepository.findById(requisicao.getTipoQuartoId())
	         .orElseThrow(() -> new RuntimeException("Tipo de Quarto não encontrado"));
	     quarto.setTipoQuarto(tipoQuarto);

	     quartoRepository.save(quarto);

	     redirectAttributes.addFlashAttribute("mensagemSucesso", "Quarto alterado com sucesso.");
	     return "redirect:/quarto/alterar";
	 }

	
	@GetMapping("consultar")
	public String quarto(Model model, Principal principal) {
	    Sort sort = Sort.by("id").ascending();	    
	    List<Quarto> quartos = quartoRepository.findAll();       
        model.addAttribute("quartos", quartos);
        return "quarto/consultar";
	}
	
	@DeleteMapping("deletar/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (quartoRepository.existsById(id)) {
        	quartoRepository.deleteById(id);
            return ResponseEntity.ok("Quarto deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quarto não encontrado.");
        }
    }
}


