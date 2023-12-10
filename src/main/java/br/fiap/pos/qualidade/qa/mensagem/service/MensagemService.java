package br.fiap.pos.qualidade.qa.mensagem.service;

import java.util.List;
import java.util.UUID;

import br.fiap.pos.qualidade.qa.mensagem.model.Mensagem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MensagemService {

    Mensagem criarMensagem(Mensagem mensagem);

    Mensagem buscarMensagem(UUID id);

    Mensagem alterarMensagem(UUID id, Mensagem mensagemNova);

    boolean apagarMensagem(UUID id);

    Mensagem incrementarGostei(UUID id);

    Page<Mensagem> listarMensagens(Pageable pageable);

}
