package br.fiap.pos.qualidade.qa.mensagem.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;

import br.fiap.pos.qualidade.qa.mensagem.model.Mensagem;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@Transactional
class MensagemRepositoryIT {

    @Autowired
    private MensagemRepository mensagemRepository;


    @Test
    void devePermitirCriarTabela() {
        var totalDeRegistros = mensagemRepository.count();

        assertThat(totalDeRegistros).isPositive();
    }

    @Test
    void devePermitirRegistrarMensagem() {

        // Arrange
        var id = UUID.randomUUID();

        var mensagem = gerarMensagem();
        mensagem.setId(id);

        // ACT
        var mensagemRecebida = mensagemRepository.save(mensagem);

        // assert
        assertThat(mensagemRecebida)
                .isInstanceOf(Mensagem.class)
                .isNotNull();
        assertThat(mensagemRecebida.getId()).isEqualTo(id);
        assertThat(mensagemRecebida.getConteudo()).isEqualTo(mensagem.getConteudo());

    }

    @Test
    void devePermitirBuscarMensagem() {

        // Arrange
        var id = UUID.fromString("314c1dfe-8686-11ee-b9d1-0242ac120002");

        // ACT

        var mensagemRecebidaOptional = mensagemRepository.findById(id);

        // assert
        assertThat(mensagemRecebidaOptional)
                .isPresent();

        mensagemRecebidaOptional.ifPresent(mensagemRecebida -> {
            assertThat(mensagemRecebida.getId()).isEqualTo(id);
        });
    }

    @Test
    void devePermitirRemoverMensagem() {

        // Arrange
        var id = UUID.fromString("3718694a-8686-11ee-b9d1-0242ac120002");

        // ACT
        mensagemRepository.deleteById(id);

        var mensagemRecebidaOptional = mensagemRepository.findById(id);

        // assert
        assertThat(mensagemRecebidaOptional).isEmpty();
    }

    @Test
    void devePermitirListarMensagens() {

        // ACT
        var resultadoObjetido = mensagemRepository.findAll();

        // assert
        //assertThat(resultadoObjetido).hasSize(3); // igual a total de registro do data.sql
        assertThat(resultadoObjetido).hasSizeGreaterThan(0); // ter mais de um registros


    }

    private Mensagem gerarMensagem() {
        return Mensagem
                .builder()
                .usuario("José")
                .conteudo("Conteúdo da mensagem")
                .build();
    }
}
