Exercicio 03 - Processamento de Pedidos com Spring

Realizar um Pedido

Vamos realizar um pedido em nosso sistema seguindo os passos abaixo:

Reservar fundos: Para reservar fundos, precisamos validar que:
a. O número do cartão de crédito não começa com 4000.
b. O número do cartão de crédito deve estar entre 4111 e 4222.
c. O valor total deve ser maior que zero.

Quando todas as validações acima estiverem OK, vamos marcar o resultado como Fundos Reservados e gerar um UUID que representa o código da reserva.

Atualizar Inventário: Vamos atualizar o inventário quando:
a. Os fundos foram reservados.
b. O código do produto não está entre 239 – 384.
c. A quantidade do produto é maior que zero.

Quando todas as validações acima estiverem OK, vamos marcar a resposta como Inventário Atualizado e debitar o cartão de crédito.

Efetuar pagamento: Após o inventário ser atualizado, vamos debitar o cartão de crédito do cliente gerando um código UUID que representa o código de verificação do pagamento.

Gerar Pedido: Agora que temos tudo no lugar, vamos gerar outro UUID que representará o Número do Pedido.

Você tem liberdade para decidir como essa sequência de passos será projetada e codificada, mas aqui está uma ideia do input que você poderia lidar e da informação mínima esperada como resultado.

Input:

Número do Cartão de Crédito
Código do Produto
Quantidade
Output:

Código de Fundos Reservados
Código de Pagamento
ID do Pedido
Nota: Lembre-se de que agora podemos usar Maven e Spring Framework para gerar nosso projeto e que podemos agrupar nosso código em diferentes pacotes que adicionem significado ao que será codificado. Tente seguir as melhores práticas e projetar uma boa solução.

Bônus:

Tente pensar em como nosso processo mudaria se amanhã decidirmos adicionar etapas extras, como entrega, notificação ao cliente, etc.
Tente pensar no que aconteceria se nosso serviço de pagamento mudasse.
