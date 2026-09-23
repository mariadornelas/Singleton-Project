package com.faculdade.sensor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SensorFactoryTest {

    @Test
    void getInstanceDeveSempreRetornarAMesmaReferencia() {
        SensorFactory instancia1 = SensorFactory.getInstance();
        SensorFactory instancia2 = SensorFactory.getInstance();

        assertSame(instancia1, instancia2, "getInstance() deveria sempre retornar a mesma instância");
    }

    @Test
    void obterSensorDeveCriarUmSensorDeTemperatura() {
        LeituraSensor sensor = SensorFactory.getInstance().obterSensor("Temperatura");

        assertInstanceOf(SensorTemperatura.class, sensor);
        assertEquals("TEMPERATURA", sensor.getTipo());
    }

    @Test
    void obterSensorDeveCriarUmSensorDePressao() {
        LeituraSensor sensor = SensorFactory.getInstance().obterSensor("Pressao");

        assertInstanceOf(SensorPressao.class, sensor);
        assertEquals("PRESSAO", sensor.getTipo());
    }

    @Test
    void obterSensorDeveLancarExcecaoParaTipoInexistente() {
        assertThrows(IllegalArgumentException.class,
                () -> SensorFactory.getInstance().obterSensor("Umidade"));
    }

    @Test
    void obterSensorDeveRetornarInstanciasDiferentesACadaChamada() {

        LeituraSensor sensor1 = SensorFactory.getInstance().obterSensor("Temperatura");
        LeituraSensor sensor2 = SensorFactory.getInstance().obterSensor("Temperatura");

        assertNotSame(sensor1, sensor2);
    }
}
