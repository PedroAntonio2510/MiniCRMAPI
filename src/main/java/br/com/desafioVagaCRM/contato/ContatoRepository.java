package br.com.desafioVagaCRM.contato;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContatoRepository extends JpaRepository<Contato, Long> {
    List<Contato> findAllByCliente_Id(Long clienteId);
}
