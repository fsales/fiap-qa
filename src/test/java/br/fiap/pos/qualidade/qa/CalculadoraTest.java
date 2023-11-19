package br.fiap.pos.qualidade.qa;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CalculadoraTest {

    private Calculadora calculadora;

    @BeforeEach
    void setup() {
        calculadora = new Calculadora();
    }

    @Test
    void deveSomar() {
        var resultado = calculadora.somar(4, 2);
        assertEquals(6, resultado);
    }

    @Test
    void deveSubtrair() {
        var resultado = calculadora.subtrair(4, 2);
        assertEquals(2, resultado);
    }

    @Test
    void deveMultiplicar() {
        var resultado = calculadora.multiplicar(4, 2);
        assertEquals(8, resultado);
    }

    @Test
    void deveDividir() {

        var resultado = calculadora.dividir(4, 2);
        assertEquals(2, resultado);

    }

    @Test
    void deveDividir_GerarExcecaoQuandoDividirPorZero() {
        assertThrows(ArithmeticException.class, ()->{
            calculadora.dividir(4, 0);
        });
    }
}
