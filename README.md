# Projeto Spring Boot com Kotlin: Demonstração SOLID e POO

Este projeto é uma API REST implementada com Spring Boot e Kotlin que demonstra a aplicação dos princípios SOLID e conceitos de Programação Orientada a Objetos. O sistema simula uma loja online com gerenciamento de pedidos, produtos, clientes, pagamentos e notificações.

## Requisitos do Sistema

- JDK 17 ou superior
- Gradle 7.5 ou superior (ou use o wrapper incluso)
- Git

## Como clonar e executar o projeto

### 1. Clone o repositório

```bash
git clone https://github.com/seu-usuario/solid-poo-kotlin-springboot.git
cd solid-poo-kotlin-springboot
```

### 2. Execute o projeto

Usando o Gradle wrapper:

```bash
./gradlew bootRun
```

No Windows:

```bash
gradlew.bat bootRun
```

### 3. Acesse o projeto

- API REST: http://localhost:8080/api
- Console H2 (banco de dados): http://localhost:8080/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Usuário: `sa`
  - Senha: `password`

## Estrutura do Projeto

```
com.example.solidpoo/
├── SolidPooApplication.kt
├── domain/
│   ├── model/                    # Entidades de domínio
│   │   ├── Order.kt
│   │   ├── Product.kt
│   │   └── Customer.kt
│   └── service/                  # Serviços de negócio
│       ├── OrderService.kt
│       ├── PaymentService.kt
│       ├── InventoryService.kt
│       └── NotificationService.kt
├── infrastructure/
│   ├── repository/               # Interfaces e implementações de repositórios
│   ├── payment/                  # Processadores de pagamento
│   └── notification/             # Serviços de notificação
└── api/
    ├── controller/               # Controladores REST
    └── dto/                      # Objetos de transferência de dados
```

## Endpoints da API

### Pedidos

- **Criar novo pedido**: `POST /api/orders`
  ```json
  {
    "customerId": 1,
    "items": [
      {"productId": 1, "quantity": 2},
      {"productId": 3, "quantity": 1}
    ]
  }
  ```

- **Listar todos os pedidos**: `GET /api/orders`
- **Obter um pedido específico**: `GET /api/orders/{orderId}`
- **Listar pedidos de um cliente**: `GET /api/orders/customer/{customerId}`
- **Processar pagamento**: `POST /api/orders/{orderId}/payment`
  ```json
  {
    "paymentMethod": "CREDIT_CARD"
  }
  ```
- **Marcar pedido como enviado**: `POST /api/orders/{orderId}/ship`
- **Marcar pedido como entregue**: `POST /api/orders/{orderId}/deliver`
- **Cancelar pedido**: `POST /api/orders/{orderId}/cancel`

### Produtos

- **Listar todos os produtos**: `GET /api/products`
- **Obter um produto específico**: `GET /api/products/{productId}`

## Dados de Exemplo

O sistema é inicializado com os seguintes dados de exemplo:

### Clientes
- João Silva (ID: 1)
- Maria Oliveira (ID: 2)
- Carlos Santos (ID: 3)

### Produtos
- Smartphone Galaxy S22 - R$ 3.999,99 (ID: 1)
- Notebook Dell XPS 13 - R$ 7.999,99 (ID: 2)
- Smart TV 4K 55" - R$ 2.799,99 (ID: 3)
- Fone de Ouvido Bluetooth - R$ 299,99 (ID: 4)
- Console PlayStation 5 - R$ 4.499,99 (ID: 5)

## Princípios SOLID Demonstrados

### 1. S - Single Responsibility Principle
Cada classe tem uma única responsabilidade. Por exemplo:
- `OrderService`: Gerencia apenas a lógica relacionada a pedidos
- `PaymentService`: Trata apenas do processamento de pagamentos

### 2. O - Open/Closed Principle
As classes estão abertas para extensão, mas fechadas para modificação:
- O sistema de pagamentos pode ser estendido com novos processadores sem modificar o código existente

### 3. L - Liskov Substitution Principle
As implementações concretas podem substituir suas interfaces:
- `CreditCardProcessor` e `PayPalProcessor` são intercambiáveis onde `PaymentProcessor` é esperado

### 4. I - Interface Segregation Principle
Interfaces específicas e coesas:
- `NotificationSender` contém apenas métodos relacionados ao envio de notificações
- `PaymentProcessor` contém apenas métodos relacionados ao processamento de pagamentos

### 5. D - Dependency Inversion Principle
Dependências de abstrações, não de implementações concretas:
- Serviços dependem de interfaces como `OrderRepository` e `PaymentProcessor`

## Conceitos de POO Demonstrados

- **Encapsulamento**: Dados privados com acesso controlado (ex: classe `Product`)
- **Herança**: Via interfaces e implementações
- **Polimorfismo**: Diferentes processadores de pagamento e canais de notificação
- **Abstração**: Modelagem de entidades de domínio como classes e interfaces
- **Composição**: Relações entre entidades como `Order` e `OrderItem`

## Executando os Testes

```bash
./gradlew test
```

## Construindo o Projeto

```bash
./gradlew build
```

O arquivo JAR será gerado no diretório `build/libs/`.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou enviar pull requests.
