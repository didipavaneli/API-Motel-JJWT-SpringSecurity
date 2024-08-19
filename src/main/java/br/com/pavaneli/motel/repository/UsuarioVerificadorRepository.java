package br.com.pavaneli.motel.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.pavaneli.motel.entity.UsuarioVerificador;

public interface UsuarioVerificadorRepository extends JpaRepository<UsuarioVerificador, Long>{

	public Optional<UsuarioVerificador> findByUuid(UUID uuid);
}

