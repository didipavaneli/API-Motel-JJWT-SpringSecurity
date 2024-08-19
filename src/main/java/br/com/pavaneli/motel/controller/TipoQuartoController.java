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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.pavaneli.motel.dto.TipoQuartoDTO;
import br.com.pavaneli.motel.entity.TipoQuarto;
import br.com.pavaneli.motel.repository.TipoQuartoRepository;

@Controller
@RequestMapping("tipoquarto")
@CrossOrigin
public class TipoQuartoController {
    @Autowired
    private TipoQuartoRepository tipoQuartoRepository;

    @GetMapping("todos")
    @ResponseBody
    public List<TipoQuarto> listarTodosTipoQuartos() {
        return tipoQuartoRepository.findAll(Sort.by("id").ascending());
    }

    @GetMapping("cadastrar")
    public String formulario() {
        return "tipoquarto/cadastrar";
    }

    @GetMapping("alterar")
    public String listarTipoQuartos(Model model) {
        List<TipoQuarto> tipoQuartos = tipoQuartoRepository.findAll(Sort.by("id").ascending());
        model.addAttribute("tipoQuartos", tipoQuartos);
        return "tipoquarto/alterar"; 
    }

    @GetMapping("deletar")
    public String formdelete() {
        return "tipoquarto/deletar";
    }

    @GetMapping("sucesso")
    public String formsucesso() {
        return "tipoquarto/sucesso";
    }

    @PostMapping("novo")
    public String novo(TipoQuartoDTO requisicao) {
        TipoQuarto tipoQuarto = requisicao.toNovoTipoQuarto();
        tipoQuartoRepository.save(tipoQuarto);
        return "tipoquarto/sucesso";
    }

    @PostMapping("alterar")
    public String alterar(TipoQuartoDTO requisicao, BindingResult result, RedirectAttributes redirectAttributes) {
        TipoQuarto tipoQuarto = requisicao.toAlterarTipoQuarto();
        tipoQuartoRepository.save(tipoQuarto);
        redirectAttributes.addFlashAttribute("mensagemSucesso", "Tipo de Quarto alterado com sucesso.");

        return "redirect:/tipoquarto/alterar";
    }

    @GetMapping("consultar")
    public String tipoQuarto(Model model, Principal principal) {
        List<TipoQuarto> tipoQuartos = tipoQuartoRepository.findAll(Sort.by("id").ascending());
        model.addAttribute("tipoQuartos", tipoQuartos); // Use 'tipoQuartos' consistentemente
        return "tipoquarto/consultar";
    }

    @DeleteMapping("deletar/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (tipoQuartoRepository.existsById(id)) {
            tipoQuartoRepository.deleteById(id);
            return ResponseEntity.ok("Tipo de Quarto deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Tipo de Quarto não encontrado.");
        }
    }
}