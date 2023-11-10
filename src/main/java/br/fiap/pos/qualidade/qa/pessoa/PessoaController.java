package br.fiap.pos.qualidade.qa.pessoa;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/pessoas")
public class PessoaController {

    @GetMapping
    public ResponseEntity<Pessoa> teste(Pessoa pessoa) {

        return ResponseEntity.ok(pessoa);
    }
}

record Pessoa(
        String nome
) {
}
