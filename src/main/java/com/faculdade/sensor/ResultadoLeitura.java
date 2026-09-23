package com.faculdade.sensor;

import java.time.LocalDateTime;
import java.util.Objects;

public final class ResultadoLeitura {

    private final String tipoSensor;
    private final double valorLido;
    private final String status; // "NORMAL", "ALERTA" ou "CRITICO"
    private final String mensagem;
    private final LocalDateTime dataLeitura;

    public ResultadoLeitura(String tipoSensor, double valorLido, String status, String mensagem) {
        this.tipoSensor = Objects.requireNonNull(tipoSensor, "tipoSensor não pode ser nulo");
        this.valorLido = valorLido;
        this.status = Objects.requireNonNull(status, "status não pode ser nulo");
        this.mensagem = Objects.requireNonNull(mensagem, "mensagem não pode ser nula");
        this.dataLeitura = LocalDateTime.now();
    }

    public String getTipoSensor() {
        return tipoSensor;
    }

    public double getValorLido() {
        return valorLido;
    }

    public String getStatus() {
        return status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public LocalDateTime getDataLeitura() {
        return dataLeitura;
    }

    @Override
    public String toString() {
        return String.format(
                "ResultadoLeitura{sensor='%s', valor=%.1f, status=%s, mensagem='%s'}",
                tipoSensor, valorLido, status, mensagem);
    }
}
