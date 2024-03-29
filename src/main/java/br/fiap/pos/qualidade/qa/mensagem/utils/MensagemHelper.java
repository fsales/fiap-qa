package br.fiap.pos.qualidade.qa.mensagem.utils;

import java.time.LocalDateTime;
import java.util.UUID;

import br.fiap.pos.qualidade.qa.mensagem.dto.MensagemRequest;
import br.fiap.pos.qualidade.qa.mensagem.model.Mensagem;
import br.fiap.pos.qualidade.qa.mensagem.repository.MensagemRepository;

public abstract class MensagemHelper {

    public static final String XPTO_TEST = "xpto test";

    private MensagemHelper() {
    }

    public static MensagemRequest gerarMensagemRequest() {
        return MensagemRequest.builder()
                .usuario("joe")
                .conteudo(XPTO_TEST)
                .build();
    }

    public static Mensagem gerarMensagem() {
        return Mensagem.builder()
                .usuario("joe")
                .conteudo(XPTO_TEST)
                .build();
    }

    public static Mensagem gerarMensagemCompleta() {
        var timestamp = LocalDateTime.now();
        return Mensagem.builder()
                .id(UUID.randomUUID())
                .usuario("joe")
                .conteudo(XPTO_TEST)
                .dataCriacao(timestamp)
                .dataAlteracao(timestamp)
                .build();
    }

    public static Mensagem registrarMensagem(MensagemRepository repository) {
        var mensagem = gerarMensagem();
        mensagem.setId(UUID.randomUUID());
        return repository.save(mensagem);
    }
}
