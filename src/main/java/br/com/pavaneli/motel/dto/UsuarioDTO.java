package br.com.pavaneli.motel.dto;



import org.springframework.beans.BeanUtils;

import br.com.pavaneli.motel.entity.Usuario;
import br.com.pavaneli.motel.enums.TipoSituacaoUsuario;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UsuarioDTO {
	private String idUsuario;	
	private String nomeUsuario;	
	private String loginUsuario;	
	private String senhaUsuario;
	private String emailUsuario;
	private String situacaoUsuario;
	
	public UsuarioDTO(Usuario usuario) {
		BeanUtils.copyProperties(usuario, this);
	}
	
	public Usuario toNovoUsuario() {
		Usuario usuario = new Usuario();
		usuario.setNome(nomeUsuario);
		usuario.setLogin(loginUsuario);
		usuario.setSenha(senhaUsuario);
		usuario.setEmail(emailUsuario);	
		usuario.setSituacao(TipoSituacaoUsuario.doValor(situacaoUsuario));
		return usuario;
	}
	
	public Usuario toAlterarUsuario() {
		Usuario usuario = new Usuario();
		if (idUsuario != null && !idUsuario.isEmpty()) {
			usuario.setId(Long.parseLong(idUsuario));
		}
		usuario.setNome(nomeUsuario);
		usuario.setLogin(loginUsuario);
		usuario.setSenha(senhaUsuario);
		usuario.setEmail(emailUsuario);
		usuario.setSituacao(TipoSituacaoUsuario.doValor(situacaoUsuario));
		return usuario;
	}
	public String toExcluirUsuario() {
		String id = idUsuario;
		return id;
	}
	

}
