package br.fiap.pos.qualidade.qa.mensagem.service;

public class MensagemNotFoundException extends RuntimeException {
    public MensagemNotFoundException(String mensagemNaoEncontrada) {
        super(mensagemNaoEncontrada);
    }
}
