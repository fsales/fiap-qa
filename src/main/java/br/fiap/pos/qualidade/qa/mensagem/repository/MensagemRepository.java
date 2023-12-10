package br.fiap.pos.qualidade.qa.mensagem.repository;

import java.util.UUID;

import br.fiap.pos.qualidade.qa.mensagem.model.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {

    @Query("SELECT m FROM Mensagem m ORDER BY m.dataCriacao DESC")
    Page<Mensagem> listarMensagens(Pageable pageable);
}
