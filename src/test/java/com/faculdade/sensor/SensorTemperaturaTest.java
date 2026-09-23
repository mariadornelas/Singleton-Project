package com.faculdade.sensor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorTemperaturaTest {

    private final LeituraSensor sensor = new SensorTemperatura();

    @Test
    void deveClassificarComoNormalAbaixoDoLimiteDeAlerta() {
        ResultadoLeitura resultado = sensor.ler(45.0);

        assertEquals("NORMAL", resultado.getStatus());
        assertEquals("TEMPERATURA", resultado.getTipoSensor());
    }

    @Test
    void deveClassificarComoAlertaEntreOsLimites() {
        ResultadoLeitura resultado = sensor.ler(75.0);

        assertEquals("ALERTA", resultado.getStatus());
    }

    @Test
    void deveClassificarComoCriticoAcimaDoLimiteCritico() {
        ResultadoLeitura resultado = sensor.ler(95.0);

        assertEquals("CRITICO", resultado.getStatus());
        assertTrue(resultado.getMensagem().toLowerCase().contains("superaquecimento"));
    }

    @Test
    void deveRetornarOTipoCorreto() {
        assertEquals("TEMPERATURA", sensor.getTipo());
    }
}
