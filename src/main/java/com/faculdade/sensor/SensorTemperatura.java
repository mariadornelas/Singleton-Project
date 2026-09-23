package com.faculdade.sensor;

public class SensorTemperatura implements LeituraSensor {

    private static final double LIMITE_ALERTA = 70.0;
    private static final double LIMITE_CRITICO = 90.0;

    @Override
    public ResultadoLeitura ler(double valorMedido) {
        String status;
        String mensagem;

        if (valorMedido >= LIMITE_CRITICO) {
            status = "CRITICO";
            mensagem = "Temperatura crítica: risco de superaquecimento do equipamento.";
        } else if (valorMedido >= LIMITE_ALERTA) {
            status = "ALERTA";
            mensagem = "Temperatura acima do normal: recomenda-se monitoramento próximo.";
        } else {
            status = "NORMAL";
            mensagem = "Temperatura dentro da faixa operacional segura.";
        }

        return new ResultadoLeitura(getTipo(), valorMedido, status, mensagem);
    }

    @Override
    public String getTipo() {
        return "TEMPERATURA";
    }
}
