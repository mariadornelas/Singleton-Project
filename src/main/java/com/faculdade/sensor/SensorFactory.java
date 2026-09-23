package com.faculdade.sensor;

import java.lang.reflect.Constructor;

public class SensorFactory {

    private static SensorFactory instance = new SensorFactory();

    private SensorFactory() {}

    public static SensorFactory getInstance() {
        return instance;
    }

    public LeituraSensor obterSensor(String tipo) {
        Class<?> classe;
        Object objeto;

        try {
            classe = Class.forName("com.faculdade.sensor.Sensor" + tipo);
            Constructor<?> construtor = classe.getDeclaredConstructor();
            objeto = construtor.newInstance();
        } catch (Exception ex) {
            throw new IllegalArgumentException("sensor inexistente: " + tipo, ex);
        }

        if (!(objeto instanceof LeituraSensor)) {
            throw new IllegalArgumentException("sensor inválido: " + tipo);
        }

        return (LeituraSensor) objeto;
    }
}
