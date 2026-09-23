package com.faculdade.sensor;

public class SensorPressao implements LeituraSensor {

    private static final double LIMITE_ALERTA = 8.0;
    private static final double LIMITE_CRITICO = 12.0;

    @Override
    public ResultadoLeitura ler(double valorMedido) {
        String status;
        String mensagem;

        if (valorMedido >= LIMITE_CRITICO) {
            status = "CRITICO";
            mensagem = "Pressão crítica: acionar válvula de alívio imediatamente.";
        } else if (valorMedido >= LIMITE_ALERTA) {
            status = "ALERTA";
            mensagem = "Pressão acima do normal: verificar sistema de alívio.";
        } else {
            status = "NORMAL";
            mensagem = "Pressão dentro da faixa operacional segura.";
        }

        return new ResultadoLeitura(getTipo(), valorMedido, status, mensagem);
    }

    @Override
    public String getTipo() {
        return "PRESSAO";
    }
}
