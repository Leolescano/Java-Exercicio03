# Processamento de Pedidos com Spring

## 📝 Descrição do Projeto

Este projeto é uma aplicação Java desenvolvida com o Spring Framework que simula o processamento de um pedido. O sistema valida as informações do produto e do cartão de crédito, reserva fundos, e processa o pagamento, demonstrando o uso de boas práticas de desenvolvimento e padrões de projeto em um contexto de aplicação real.

Este projeto foi desenvolvido como uma solução para a "Tarefa da Semana #4".

## 🚀 Tecnologias Utilizadas

*   **Java 11+**
*   **Spring Framework:** Utilizado para injeção de dependência e gerenciamento do ciclo de vida dos componentes.
*   **Maven:** Para gerenciamento de dependências e build do projeto.

## 🎨 Padrões de Projeto (Design Patterns)

A solução foi projetada utilizando diversos padrões de projeto para garantir um código desacoplado, flexível e de fácil manutenção.

### 1. Injeção de Dependência (Dependency Injection)
O Spring gerencia a criação e o "wiring" dos componentes da aplicação (Beans). Por exemplo, `OrderService` recebe suas dependências (`ReservationFactory`, `PaymentStrategy`) através do construtor, sem precisar criá-las manualmente. Isso é feito com as anotações `@Autowired`, `@Service`, e `@Component`.

### 2. Strategy Pattern
Utilizado para encapsular diferentes algoritmos de pagamento.
*   **`PaymentStrategy` (Interface):** Define o contrato que todas as estratégias de pagamento devem seguir (método `pay`).
*   **`CreditCardPaymentStrategy` (Implementação):** Uma implementação concreta que processa pagamentos via cartão de crédito e contém a lógica de validação específica.
*   **Vantagem:** Se no futuro precisarmos adicionar um novo método de pagamento (e.g., PayPal, Boleto), basta criar uma nova classe que implemente `PaymentStrategy` sem alterar o `OrderService`.

### 3. Factory Pattern
Utilizado para encapsular a lógica de criação de objetos.
*   **`ReservationFactory`:** Responsável por criar instâncias do objeto `Reservation`.
*   **Vantagem:** Desacopla o `OrderService` da criação concreta de uma `Reservation`. Se o processo de criação de reserva se tornar mais complexo, a mudança ficaria contida apenas na fábrica.

### 4. Singleton Pattern
*   A classe `Configuration` foi implementada seguindo o padrão Singleton clássico.
*   **Observação:** Em uma aplicação Spring, o próprio framework gerencia os beans (componentes anotados com `@Service`, `@Component`, etc.) como singletons por padrão. Portanto, a criação manual de singletons como na classe `Configuration` é geralmente desnecessária e pode ser considerada um anti-padrão no ecossistema Spring. A classe `Configuration` não está sendo utilizada no fluxo principal da aplicação.

## 📂 Estrutura do Projeto

O código está organizado nos seguintes pacotes para melhor semântica e organização:

```
org.kodigo
├── exception/      # Exceções customizadas da aplicação
├── factory/        # Padrão Factory para criação de objetos
├── model/          # Classes de modelo (entidades)
├── service/        # Lógica de negócio principal
├── singleton/      # Demonstração do padrão Singleton
└── strategy/       # Implementações do padrão Strategy
```

## ⚙️ Como Executar

1.  **Pré-requisitos:**
    *   JDK 11 ou superior instalado.
    *   Apache Maven instalado.

2.  **Build:**
    Navegue até o diretório raiz do projeto e execute o comando Maven para compilar e empacotar a aplicação:
    ```bash
    mvn clean install
    ```

3.  **Execução:**
    Após o build, execute a aplicação através da classe principal `MainApp`:
    ```bash
    java -cp target/Java-Exercicio03-1.0-SNAPSHOT.jar org.kodigo.MainApp
    ```
    A aplicação irá processar um pedido com os dados definidos no método `main` e imprimir o resultado no console.

## 🌊 Fluxo da Aplicação

O fluxo de processamento do pedido é orquestrado pelo `OrderService`:

1.  **Recebimento do Pedido:** O método `processOrder` recebe o número do cartão de crédito, o código do produto e a quantidade.
2.  **Validação do Produto:** O método `validateProduct` verifica se a quantidade é maior que zero e se o código do produto está fora do intervalo restrito (239 a 384). Se a validação falhar, uma `InvalidProductException` é lançada.
3.  **Criação da Reserva:** Uma `Reservation` é criada através da `ReservationFactory`, gerando um código de reserva (UUID).
4.  **Processamento do Pagamento:**
    *   O `OrderService` delega o pagamento para a `PaymentStrategy` injetada (neste caso, `CreditCardPaymentStrategy`).
    *   O valor total é calculado (hardcoded como `quantidade * 100`).
    *   A estratégia de pagamento valida os dados do cartão de crédito:
        *   Não pode começar com "4000".
        *   Deve estar entre 4111 e 4222.
        *   O valor deve ser positivo.
    *   Se a validação do cartão falhar, uma `InvalidCreditCardException` é lançada.
5.  **Resultado:**
    *   Se o pagamento for bem-sucedido, uma mensagem de sucesso com o código da reserva é exibida.
    *   Caso contrário, uma mensagem de falha no pagamento é exibida.

## 💡 Análise e Sugestões de Melhoria

Embora a implementação atual funcione e demonstre os padrões de projeto, ela pode ser aprimorada para alinhar-se melhor com os requisitos da tarefa e com práticas de sistemas robustos.

### Análise vs. Requisitos (`ENUNCIADO.md`)
*   **Múltiplos IDs:** O enunciado pedia um "Código de Fundos Reservados", "Código de Pagamento" e "ID do Pedido". A implementação atual gera apenas um UUID para a reserva.
*   **Fluxo Sequencial:** O enunciado sugere um fluxo com etapas distintas (Reservar Fundos -> Atualizar Inventário -> Efetuar Pagamento -> Gerar Pedido). A implementação atual combina a reserva e o pagamento em uma única transação lógica.

### Sugestões de Melhoria
1.  **Refatorar o `OrderService`:** Dividir o método `processOrder` em métodos privados menores que representem cada etapa do fluxo (e.g., `reserveFunds`, `updateInventory`, `chargePayment`). Isso tornaria o código mais legível e alinhado com os requisitos.
2.  **Separar Validação e Ação:** O método `pay` da `CreditCardPaymentStrategy` atualmente valida e "paga" (retorna `true`). Seria melhor separar a lógica de validação da lógica de cobrança. A validação poderia ocorrer antes da criação da reserva.
3.  **Gerar todos os IDs:** Modificar o fluxo para gerar e retornar todos os UUIDs solicitados (Reserva, Pagamento, Pedido).
4.  **Tratamento de Falhas (Rollback):** O que acontece se o pagamento falhar *depois* que os fundos foram "reservados" ou o inventário "atualizado"? Em um sistema real, seria necessário um mecanismo de rollback (e.g., Saga Pattern) para desfazer as operações anteriores e garantir a consistência dos dados.
5.  **Remover Singleton Manual:** A classe `Configuration` deve ser removida, pois o Spring já fornece um gerenciamento de singletons mais robusto e integrado.

## Bonus: Escalabilidade e Flexibilidade

### E se precisarmos adicionar novas etapas (entrega, notificação)?
A solução pode ser estendida usando o **Chain of Responsibility Pattern** ou um **Process Manager/Orchestrator**. Cada etapa (pagamento, inventário, notificação, entrega) se tornaria um "handler" ou um "serviço" em uma cadeia. O orquestrador chamaria cada serviço na sequência correta, passando o estado do pedido entre eles. Isso torna a adição, remoção ou reordenação de etapas muito mais simples.

### E se o serviço de pagamento mudar?
Graças ao **Strategy Pattern**, a mudança é trivial.
1.  Cria-se uma nova classe, por exemplo `PayPalPaymentStrategy`, que implementa a interface `PaymentStrategy`.
2.  Configura-se o Spring para injetar a nova implementação em vez da antiga. Isso pode ser feito usando perfis (`@Profile`) ou qualificadores (`@Qualifier`) do Spring, permitindo trocar a estratégia de pagamento via configuração, sem alterar o código do `OrderService`.