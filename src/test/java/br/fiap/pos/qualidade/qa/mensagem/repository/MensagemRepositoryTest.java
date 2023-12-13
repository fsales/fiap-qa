package br.fiap.pos.qualidade.qa.mensagem.repository;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import br.fiap.pos.qualidade.qa.mensagem.model.Mensagem;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class MensagemRepositoryTest {

    AutoCloseable openMocks;
    @Mock
    private MensagemRepository mensagemRepository;

    @BeforeEach
    void setup() {
        openMocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void tearDown() throws Exception {
        openMocks.close();
    }

    @Test
    void devePermitirRegistrarMensagem() {

        // Arrange
        var mensagem = gerarMensagem();


        when(mensagemRepository.save(any(Mensagem.class))).thenReturn(mensagem);

        // ACT
        var mensagemArmazenada = mensagemRepository.save(mensagem);

        // Assert
        assertThat(mensagemArmazenada).isNotNull().isEqualTo(mensagem);

        verify(mensagemRepository, times(1)).save(any(Mensagem.class)); // verifica se o save foi chamado uma vez
    }

    @Test
    void devePermitirBuscarMensagem() {

        // Arrange
        var id = UUID.randomUUID();
        var mensagem = gerarMensagem();
        mensagem.setId(id);

        when(mensagemRepository.findById(any(UUID.class)))
                .thenReturn(Optional.of(mensagem));


        // ACT
        var mensagemRecebidaOpicional = mensagemRepository.findById(id);

        // assert
        assertThat(mensagemRecebidaOpicional).isPresent().containsSame(mensagem);


        mensagemRecebidaOpicional.ifPresent(mensagemRecebida -> {
            assertThat(mensagemRecebida.getId()).isEqualTo(mensagem.getId());
            assertThat(mensagemRecebida.getConteudo()).isEqualTo(mensagem.getConteudo());
        });

        verify(mensagemRepository, times(1)).findById(any(UUID.class)); // verifica se foi chamado uma vez
    }

    @Test
    void devePermitirRemoverMensagem() {

        // Arrange

        var id = UUID.randomUUID();

        doNothing().when(mensagemRepository).deleteById(any(UUID.class));

        // ACT
        mensagemRepository.deleteById(id);

        // assert
        verify(mensagemRepository, times(1)).deleteById(any(UUID.class)); // verifica se foi chamado uma vez
    }

    @Test
    void devePermitirListarMensagens() {

        var mensagem1 = gerarMensagem();
        var mensagem2 = gerarMensagem();

        // Arrange
        var listaMensagens = List.of(mensagem1, mensagem2);

        when(mensagemRepository.findAll()).thenReturn(listaMensagens);

        // ACT
        var mensagemRecebidas = mensagemRepository.findAll();

        // assert
        assertThat(mensagemRecebidas).hasSize(2).containsExactlyInAnyOrder(mensagem1, mensagem2);

        verify(mensagemRepository, times(1)).findAll(); // verifica se foi chamado uma vez


    }

    private Mensagem gerarMensagem() {
        return Mensagem
                .builder()
                .usuario("José")
                .conteudo("Conteúdo da mensagem")
                .build();
    }
}
