package br.com.pavaneli.motel.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pavaneli.motel.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
	
	Optional<Usuario> findByLogin(String login);

}