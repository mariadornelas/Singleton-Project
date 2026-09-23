package com.faculdade.sensor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testa a {@link SensorFactory}, que agora acumula dois papéis:
 * <ul>
 *   <li><b>Factory Method</b>: {@code obterSensor(tipo)} encapsula a
 *       criação do produto concreto e devolve sempre o tipo abstrato
 *       {@link LeituraSensor};</li>
 *   <li><b>Singleton</b>: {@code getInstance()} sempre retorna a mesma
 *       instância da fábrica.</li>
 * </ul>
 */
class SensorFactoryTest {

    @Test
    void getInstanceDeveSempreRetornarAMesmaReferencia() {
        SensorFactory instancia1 = SensorFactory.getInstance();
        SensorFactory instancia2 = SensorFactory.getInstance();

        // Prova central do padrão Singleton: as duas variáveis apontam
        // para o mesmo objeto na memória.
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
        // O Singleton é a FÁBRICA, não o produto: cada chamada a
        // obterSensor() ainda deve criar um novo objeto LeituraSensor.
        LeituraSensor sensor1 = SensorFactory.getInstance().obterSensor("Temperatura");
        LeituraSensor sensor2 = SensorFactory.getInstance().obterSensor("Temperatura");

        assertNotSame(sensor1, sensor2);
    }
}
