package com.faculdade.sensor;

public interface LeituraSensor {

    ResultadoLeitura ler(double valorMedido);

    String getTipo();
}
