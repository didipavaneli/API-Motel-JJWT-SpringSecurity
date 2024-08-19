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

import br.com.pavaneli.motel.dto.ProdutoDTO;
import br.com.pavaneli.motel.entity.Produto;
import br.com.pavaneli.motel.repository.ProdutoRepository;

@Controller
@RequestMapping("produto")
@CrossOrigin
public class ProdutoController {
	
	
	@Autowired
	private ProdutoRepository produtoRepository;
	
	@GetMapping("todos")
	@ResponseBody
	public List<Produto> listarTodosProdutos() {
	    List<Produto> produtos = produtoRepository.findAll(Sort.by("id").ascending());
	    produtos.forEach(produto -> System.out.println(produto));  
	    return produtos;
	}
	
	@GetMapping("cadastrar")
	public String formulario() {
		return "produto/cadastrar";
	}
	@GetMapping("alterar")
    public String listarProdutos(Model model) {
        List<Produto> produtos = produtoRepository.findAll(Sort.by("id").ascending());
        model.addAttribute("produtos", produtos);
        return "produto/alterar";
    }
	@GetMapping("deletar")
    public String formdelete() {
        return "produto/deletar";
    }
	@GetMapping("sucesso")
    public String formsucesso() {
        return "produto/sucesso";
    }
	@PostMapping("novo")
	public String novo(ProdutoDTO requisicao) {
		Produto produto = requisicao.toNovoProduto();
		produtoRepository.save(produto);
		return "produto/sucesso";
	}
	
	
	
	@PostMapping("alterar")
	public String alterar(ProdutoDTO requisicao, BindingResult result, RedirectAttributes redirectAttributes) {
	    Produto produto = requisicao.toAlterarProduto();
	    produtoRepository.save(produto);
	    redirectAttributes.addFlashAttribute("mensagemSucesso", "Produto alterado com sucesso.");

        return "redirect:/produto/alterar";
	}
	
	@GetMapping("consultar")
	public String barbeiro(Model model, Principal principal) {
	    Sort sort = Sort.by("id").ascending();	    
	    List<Produto> produtos = produtoRepository.findAll();       
        model.addAttribute("produtos", produtos);
        return "produto/consultar";
	}
	
	@DeleteMapping("deletar/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return ResponseEntity.ok("Produto deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Produto não encontrado.");
        }
    }
}


