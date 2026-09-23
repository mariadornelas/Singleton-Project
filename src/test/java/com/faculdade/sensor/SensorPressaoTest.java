package com.faculdade.sensor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorPressaoTest {

    private final LeituraSensor sensor = new SensorPressao();

    @Test
    void deveClassificarComoNormalAbaixoDoLimiteDeAlerta() {
        ResultadoLeitura resultado = sensor.ler(5.0);

        assertEquals("NORMAL", resultado.getStatus());
        assertEquals("PRESSAO", resultado.getTipoSensor());
    }

    @Test
    void deveClassificarComoAlertaEntreOsLimites() {
        ResultadoLeitura resultado = sensor.ler(9.0);

        assertEquals("ALERTA", resultado.getStatus());
    }

    @Test
    void deveClassificarComoCriticoAcimaDoLimiteCritico() {
        ResultadoLeitura resultado = sensor.ler(13.0);

        assertEquals("CRITICO", resultado.getStatus());
        assertTrue(resultado.getMensagem().toLowerCase().contains("válvula"));
    }

    @Test
    void deveRetornarOTipoCorreto() {
        assertEquals("PRESSAO", sensor.getTipo());
    }
}
