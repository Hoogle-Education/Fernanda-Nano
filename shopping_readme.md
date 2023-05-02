# Shopping app

```java
package models;

public class Item {
    public String name;
    public double price;
}
```

usando a classe Item:

```java
// criando a variável
Item ipad = new Item();

// atribuindo valores para a variável
ipad.name = "Ipad Pro";
```

Classe Item significa ter um novo tipo no Java, dedicada a manipular itens.

- Classe: Molde, novo Tipo de variável
- Objeto: Fruto do molde

Classe é definida pelo que tem e pelo que faz.

Carro:
tem:

- placa : String
- ano : int
- dono : String
- valor: double
  faz:
- ligarMotor() : void
- ligarFarol() : void
- desligarMotor() : void
- desligarFarol() : void
- acelerar(double forcaNoPedal) : double

```java
public class Carro {

  // attributes
  public String id;
  public int year;
  public String owner;
  public double price;
  public boolean motor;

  // methods
  public void ligarMotor() {
    motor = true;
  }

  public void desligarMotor() {
    motor = false;
  }

}
```

usando Carro:

```java
Carro ferrari = new Carro();
ferrari.id = "ABC1D23";
ferrari.valor = 10;
ferrari.valor = 100000000;

ferrari.valor = 0;
```

## Motivação Construtores, Getters and Setters

### problemas atuais

- Criando objetos sem garantir valores básicos
- Utilização dos objetos

### Construtores

é definir o que é essencial na criação de um objeto de um determinado tipo.

```java
public class Carro {

  // atributos...

  // construtor
  public Carro (int year, double price) {
    this.year = year;
    this.price = price;
  }

  // métodos...
}
```

usar carro:

❌ errado

```java
Carro ferrari = new Carro();
```

✅ correto

```java
// Crio o ferrari e já atribuo valores essenciais para um Carro.
Carro ferrari = new Carro(2020, 300);
```

### Encapsulamento, Access Modifiers, Getters and Setters

Não devemos deixar o acesso `public` para atributos na maioria dos casos.

É uma melhor prática privar e posteriormente o acesso deve ser concedido.

Ordenando privacidades:

- public: permite o acesso em todas as classes do projeto.
- private: permite o acesso somente na classe de origem.

```java
public class Carro {

  // attributes
  private String id;
  private int year;
  private String owner;
  private double price;
  private boolean motor;

  public void setPrice(double price) {
    this.price = price;
  }

  public double getPrice() {
    return price;
  }

  public int getYear() {
    return year;
  }
}
```

utilização

```java
Carro ferrari = new Carro(2020, 300);
ferrari.setPrice(400);
// ferrari.setYear() // erro, pois não existe setYear
System.out.println("Year: " + ferarri.getYear());
System.out.println("Price: " + ferarri.getPrice());
```

## Método especial `toString()`

Este método nos ajuda a transformar um objeto em texto.

chamada implicita para a `toString()`

```java
System.out.println(ipad);
```

## Ordem

1. atributos
2. construtores
3. getters and setters
4. métodos personalizados
5. toString
6. equals and hashcode

> podemos analisar a 5 e a 6 como apenas o bloco de overrides a serem colocados.

> o bloco 3 e 4 podem trocar de posição

## Relacionamento

1. Herança (é, is)

Recebendo características de uma classe mãe.

2. Composição (tem um, tem vários)

Atributos originados de uma outra classe criada.

### hasOne

basicamente utiliza um atributo do tipo desejado.

```java
public class CartElement {
  // atributos - tem
  private Item item; // tem um item
  private int quantity;
  private int priority;
}
```

### hasMany

Usamos alguma estrutura de dados capaz de lidar com vários dados sendo inseridos nesse relacionamento, geralmente escolhendo a Lista.

```java
public class InstagramUser {

  private String name;
  private List<InstagramUser> followers;
  private List<InstagramUser> followeing;

}
```

## Ordenando sequencias

1. critério de comparação

"beatriz"
"amanda"
"alberto"

> uma sequencia para ser ordenável tem objetos que cumprem o contrato de serem comparáveis

```java
public class CartElement implements Comparable<CartElement> {

  @Override
  public int compareTo(CartElement other) {
      return Integer.compare(this.priority, other.priority);
  }

}
```

2. estratégia de ordenação

bubble sort:

trocar dois a dois por exaustão

4 3 2 1
3 4 2 1
3 2 4 1
3 2 1 4
2 3 1 4
2 1 3 4
1 2 3 4
1 2 3 4
