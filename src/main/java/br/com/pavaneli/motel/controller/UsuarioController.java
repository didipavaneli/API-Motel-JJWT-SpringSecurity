package br.com.pavaneli.motel.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
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

import br.com.pavaneli.motel.dto.UsuarioDTO;
import br.com.pavaneli.motel.entity.Usuario;
import br.com.pavaneli.motel.repository.UsuarioRepository;
import br.com.pavaneli.motel.service.UsuarioService;

@Controller
@RequestMapping("usuario")
@CrossOrigin
public class UsuarioController {
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@GetMapping("todos")
	@ResponseBody
	public List<Usuario> listarTodosUsuarios() {
	    return usuarioRepository.findAll(Sort.by("id").ascending());
	}
	
	
	@GetMapping("sucesso")
    public String formsucesso() {
        return "Usuario/sucesso";
    }
	
	@GetMapping("cadastrar")
	public String formulario() {
		return "Usuario/cadastrar";
	}
	@GetMapping("deletar")
    public String formdelete() {
        return "usuario/deletar";
    }
	@GetMapping("alterar")
    public String listarUsuarios(Model model) {
        List<Usuario> usuarios = usuarioRepository.findAll(Sort.by("id").ascending());
        model.addAttribute("usuarios", usuarios);
        return "usuario/alterar";
    }
	@GetMapping("consultar")
	public String usuario(Model model, Principal principal) {
	    Sort sort = Sort.by("id").ascending();	    
	    List<Usuario> usuarios = usuarioRepository.findAll();       
        model.addAttribute("usuarios", usuarios);
        return "usuario/consultar";
	}
	
	@PostMapping("novo")
	public String novo(UsuarioDTO requisicao) {
	    Usuario usuario = requisicao.toNovoUsuario();
	    usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
	    usuarioRepository.save(usuario);
	    return "usuario/sucesso";
	}
	@PostMapping("alterar")
	public String alterar(UsuarioDTO requisicao, BindingResult result, RedirectAttributes redirectAttributes) {
		Usuario usuario = requisicao.toAlterarUsuario();
		usuarioRepository.save(usuario);
		redirectAttributes.addFlashAttribute("mensagemSucesso", "Usuario alterado com sucesso.");
		return "redirect:/usuario/alterar";
	}
	
	@DeleteMapping("deletar/{id}")
    @ResponseBody
    public ResponseEntity<String> delete(@PathVariable Long id) {
        if (usuarioRepository.existsById(id)) {
        	usuarioRepository.deleteById(id);
            return ResponseEntity.ok("Usuario deletado com sucesso.");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario não encontrado.");
        }
    }

}
