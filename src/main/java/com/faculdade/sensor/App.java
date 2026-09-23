package com.faculdade.sensor;

public class App {

    public static void main(String[] args) {
        System.out.println("=== Central de Monitoramento Industrial ===\n");

        SensorFactory fabrica = SensorFactory.getInstance();

        LeituraSensor sensorTemperatura = fabrica.obterSensor("Temperatura");
        LeituraSensor sensorPressao = fabrica.obterSensor("Pressao");

        double[] leiturasTemperatura = {45.0, 75.0, 95.0};
        double[] leiturasPressao = {5.0, 9.0, 13.0};

        System.out.println("--- Sensor: " + sensorTemperatura.getTipo() + " ---");

        for (double valor : leiturasTemperatura) {
            System.out.println(sensorTemperatura.ler(valor));
        }

        System.out.println();

        System.out.println("--- Sensor: " + sensorPressao.getTipo() + " ---");
        for (double valor : leiturasPressao) {
            System.out.println(sensorPressao.ler(valor));
        }

        System.out.println("\nA fábrica usada pelos dois sensores é a mesma instância? "
                + (fabrica == SensorFactory.getInstance()));
    }
}
