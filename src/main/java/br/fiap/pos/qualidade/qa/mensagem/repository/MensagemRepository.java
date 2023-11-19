package br.fiap.pos.qualidade.qa.mensagem.repository;

import java.util.UUID;

import br.fiap.pos.qualidade.qa.mensagem.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MensagemRepository extends JpaRepository<Mensagem, UUID> {
}
