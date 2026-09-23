# Sistema de Monitoramento de Sensores Industriais — Factory Method + Singleton

Projeto acadêmico que demonstra a aplicação do padrão de projeto
**Factory Method** (GoF, categoria *Criacional*) em um domínio
industrial — sensores de **Temperatura** e **Pressão** de uma planta —
com a particularidade de que a classe criadora (`SensorFactory`) foi
implementada também como um **Singleton**, usando *Reflection* para
decidir dinamicamente qual produto instanciar.

---

## 1. O que mudou em relação à versão anterior

Na primeira versão deste projeto, `SensorFactory` era uma **classe
abstrata**, com uma subclasse concreta para cada tipo de sensor:

```java
// versão anterior
public abstract class SensorFactory {
    protected abstract LeituraSensor criarSensor();
    // ...
}
public class SensorTemperaturaFactory extends SensorFactory {
    protected LeituraSensor criarSensor() { return new SensorTemperatura(); }
}
public class SensorPressaoFactory extends SensorFactory {
    protected LeituraSensor criarSensor() { return new SensorPressao(); }
}
```

Nesta versão, seguindo o mesmo estilo usado no exemplo do escritório de
advocacia (a classe `FactoryMethod` daquele projeto), a `SensorFactory`
deixou de ser abstrata e de ter subclasses. Agora ela é uma
**classe única, concreta, e Singleton**, que decide qual sensor criar
**em tempo de execução**, via *Reflection*, a partir do nome do tipo:

```java
public class SensorFactory {

    private static SensorFactory instance = new SensorFactory();

    private SensorFactory() { }

    public static SensorFactory getInstance() {
        return instance;
    }

    public LeituraSensor obterSensor(String tipo) {
        Class<?> classe = Class.forName("com.faculdade.sensor.Sensor" + tipo);
        Object objeto = classe.getDeclaredConstructor().newInstance();
        // ... validações ...
        return (LeituraSensor) objeto;
    }
}
```

As classes `SensorTemperaturaFactory` e `SensorPressaoFactory` **não
existem mais** — não são mais necessárias, porque a decisão de qual
classe instanciar passou da hierarquia de subclasses (polimorfismo)
para a string passada em `obterSensor(tipo)` (Reflection).

---

## 2. Por que isso ainda é Factory Method?

O papel central do padrão continua presente: existe um **método
fábrica** (`obterSensor`) que:

- encapsula toda a lógica de criação de um objeto concreto;
- devolve sempre o **tipo abstrato** `LeituraSensor` ao cliente;
- garante que o código cliente (a classe `App`) **nunca** escreve
  `new SensorTemperatura()` ou `new SensorPressao()` diretamente.

A diferença é *como* a fábrica decide qual classe concreta instanciar:

| | Versão com subclasses de fábrica | Versão com Singleton + Reflection |
|---|---|---|
| Quem decide o produto | Cada subclasse de `SensorFactory`, via método sobrescrito | A própria `SensorFactory`, lendo uma `String` em tempo de execução |
| Mecanismo | Polimorfismo (herança) | Reflection (`Class.forName`) |
| Quantas classes de fábrica existem | Uma abstrata + uma concreta por produto | Uma única classe, para todos os produtos |
| Adicionar um novo sensor | Cria-se uma nova subclasse de fábrica | Basta criar a nova classe de produto (`SensorUmidade`, por exemplo) — nenhuma classe de fábrica nova é necessária |

---

## 3. Por que também é Singleton?

Só faz sentido existir **uma única fábrica** de sensores na aplicação
inteira — não há motivo para instanciar `SensorFactory` várias vezes,
já que ela não guarda nenhum estado próprio de negócio (é só um ponto
central de criação). O padrão Singleton garante isso através de três
elementos clássicos, visíveis diretamente na classe:

1. **Construtor privado** (`private SensorFactory()`) — impede
   `new SensorFactory()` de fora da classe.
2. **Campo estático com a única instância** (`private static SensorFactory instance`).
3. **Ponto de acesso estático único** (`public static SensorFactory getInstance()`).

> **Atenção didática:** o Singleton aqui é a **fábrica**
> (`SensorFactory`), não os **produtos** (`SensorTemperatura`,
> `SensorPressao`). Cada chamada a `obterSensor(tipo)` continua criando
> um objeto `LeituraSensor` novo — isso é inclusive verificado pelo
> teste `obterSensorDeveRetornarInstanciasDiferentesACadaChamada`.

---

## 4. Estrutura das classes

| Papel                    | Classe/Interface                                | Responsabilidade                                                                 |
|----------------------------|----------------------------------------------------|-------------------------------------------------------------------------------------|
| **Product**                | `LeituraSensor` (interface)                        | Contrato comum a todo sensor (`ler`, `getTipo`).                                    |
| **Concrete Product**       | `SensorTemperatura`, `SensorPressao`                | Implementam as regras específicas de classificação de cada tipo de sensor.          |
| **Creator + Singleton**    | `SensorFactory`                                     | Construtor privado + `getInstance()` + `obterSensor(tipo)` (Factory Method via Reflection). |
| Objeto de valor (apoio)    | `ResultadoLeitura`                                  | Carrega o resultado de uma leitura (tipo, valor, status, mensagem, data).           |
| Cliente                    | `App`                                               | Usa apenas `SensorFactory.getInstance()` e o tipo abstrato `LeituraSensor`.         |

---

## 5. Diagrama UML

```
                ┌───────────────────────┐
                │   <<interface>>       │
                │   LeituraSensor       │
                ├───────────────────────┤
                │ + ler(valor): Resultado │
                │ + getTipo(): String   │
                └──────────▲────────────┘
                           │ implements
              ┌────────────┴────────────┐
              │                         │
   ┌──────────┴─────────┐    ┌──────────┴─────────┐
   │  SensorTemperatura  │    │    SensorPressao    │
   └──────────▲──────────┘    └──────────▲──────────┘
              │                          │
              │   cria via Reflection    │
              │   (Class.forName +      │
              │    newInstance)          │
              └────────────┬─────────────┘
                           │
                ┌──────────┴────────────────┐
                │       SensorFactory        │
                ├─────────────────────────────┤
                │ - instance: SensorFactory   │  <-- static, única instância
                │ - SensorFactory()           │  <-- construtor privado
                ├─────────────────────────────┤
                │ + getInstance(): SensorFactory │ <-- Singleton
                │ + obterSensor(tipo): LeituraSensor │ <-- Factory Method
                └─────────────────────────────┘
                             ▲
                             │ usa (só conhece a abstração)
                        ┌────┴────┐
                        │   App   │  (cliente)
                        └─────────┘
```

O arquivo `docs/diagrama-uml.puml` contém a mesma estrutura em sintaxe
**PlantUML**.

---

## 6. Estrutura de pastas do projeto

```
factory-method-sensores/
├── pom.xml
├── README.md
├── docs/
│   └── diagrama-uml.puml
└── src/
    ├── main/java/com/faculdade/sensor/
    │   ├── LeituraSensor.java      (Product)
    │   ├── ResultadoLeitura.java   (Value Object)
    │   ├── SensorTemperatura.java  (Concrete Product)
    │   ├── SensorPressao.java      (Concrete Product)
    │   ├── SensorFactory.java      (Creator + Singleton, Factory Method via Reflection)
    │   └── App.java                (Cliente / demo)
    └── test/java/com/faculdade/sensor/
        ├── SensorTemperaturaTest.java
        ├── SensorPressaoTest.java
        └── SensorFactoryTest.java
```

---

## 7. Como rodar

Pré-requisitos: **Java 17+** e **Maven** instalados.

```bash
# Compilar
mvn compile

# Rodar os testes unitários (JUnit 5)
mvn test

# Rodar a aplicação de demonstração
mvn compile exec:java

# Gerar o .jar executável
mvn package
java -jar target/factory-method-sensores-industriais.jar
```

Este projeto foi compilado e testado manualmente com `javac`/JUnit 5
(13 testes, todos passando) antes da entrega; ao rodar `mvn test` na
sua máquina, o Maven vai baixar as dependências do Maven Central
normalmente.

---

## 8. Testes implementados

- **`SensorTemperaturaTest` / `SensorPressaoTest`**: validam as regras
  de negócio específicas de cada tipo de sensor (limiares de alerta e
  crítico), sem depender da fábrica.
- **`SensorFactoryTest`** — o mais importante do ponto de vista dos
  dois padrões combinados:
  - `getInstanceDeveSempreRetornarAMesmaReferencia`: usa `assertSame`
    para provar o **Singleton** (mesmo objeto na memória em duas
    chamadas a `getInstance()`).
  - `obterSensorDeveCriarUmSensorDeTemperatura` /
    `...DePressao`: provam o **Factory Method** (a string certa produz
    a classe concreta certa).
  - `obterSensorDeveLancarExcecaoParaTipoInexistente`: garante que um
    tipo desconhecido não gera um `ClassCastException` confuso, e sim
    um erro de negócio claro.
  - `obterSensorDeveRetornarInstanciasDiferentesACadaChamada`: reforça
    a diferença entre "a fábrica é única" (Singleton) e "os produtos
    continuam sendo criados normalmente, um novo a cada chamada"
    (Factory Method).

---

## 9. Como estender o sistema

Adicionar um novo tipo de sensor (ex: `Umidade`) agora é ainda mais
simples do que na versão anterior — **nenhuma classe de fábrica nova
precisa ser criada**:

1. Criar `SensorUmidade implements LeituraSensor` (com construtor
   público sem argumentos, exigido pela Reflection).
2. Pronto — `SensorFactory.getInstance().obterSensor("Umidade")` já
   funciona, sem tocar em nenhuma outra classe.

O "custo" dessa flexibilidade é que erros de nome só aparecem em
tempo de execução (ex: `obterSensor("Umidade")` antes de a classe
`SensorUmidade` existir lança `IllegalArgumentException`), em vez de
serem pegos pelo compilador como aconteceria com subclasses de fábrica.
Esse é um trade-off clássico do uso de Reflection e vale a pena
mencionar na apresentação.
