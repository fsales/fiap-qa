package br.fiap.pos.qualidade.qa.mensagem.service;

import java.time.LocalDateTime;
import java.util.UUID;

import br.fiap.pos.qualidade.qa.mensagem.model.Mensagem;
import br.fiap.pos.qualidade.qa.mensagem.repository.MensagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MensagemServiceImpl implements MensagemService {

    private final MensagemRepository mensagemRepository;

    @Override
    public Mensagem criarMensagem(Mensagem mensagem) {
        mensagem.setId(UUID.randomUUID());
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Mensagem buscarMensagem(UUID id) {
        return mensagemRepository.findById(id)
                .orElseThrow(() -> new MensagemNotFoundException("mensagem não encontrada"));
    }

    @Override
    public Mensagem alterarMensagem(UUID id, Mensagem mensagemAtualizada) {
        var mensagem = buscarMensagem(id);
        if (!mensagem.getId().equals(mensagemAtualizada.getId())) {
            throw new MensagemNotFoundException("mensagem não apresenta o ID correto");
        }
        mensagem.setDataAlteracao(LocalDateTime.now());
        mensagem.setConteudo(mensagemAtualizada.getConteudo());
        return mensagemRepository.save(mensagem);
    }

    @Override
    public boolean apagarMensagem(UUID id) {
        var mensagem = buscarMensagem(id);
        mensagemRepository.delete(mensagem);
        return true;
    }

    @Override
    public Mensagem incrementarGostei(UUID id) {
        var mensagem = buscarMensagem(id);
        mensagem.setGostei(mensagem.getGostei() + 1);
        return mensagemRepository.save(mensagem);
    }

    @Override
    public Page<Mensagem> listarMensagens(Pageable pageable) {
        return mensagemRepository.listarMensagens(pageable);
    }
}
