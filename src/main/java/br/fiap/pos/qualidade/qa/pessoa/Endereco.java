package br.fiap.pos.qualidade.qa.pessoa;



public class Endereco {
    private String rua;

    public Endereco(String rua) {
        this.rua = rua;
    }

    public String getRua() {
        return rua;
    }

    public Endereco setRua(String rua) {
        this.rua = rua;
        return this;
    }
}
