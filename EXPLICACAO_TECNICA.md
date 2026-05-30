# 🎮 Explicação Técnica: Sistema de Pedidos (GamesStore)

Este documento explica de forma visual e didática como as novas funcionalidades de pedidos foram implementadas no projeto, utilizando analogias com o mundo real.

---

## 1. O Mapa do Tesouro (Arquitetura)
Imagine que o sistema é uma **Loja Física de Games**. A informação viaja seguindo este caminho:

```text
USUÁRIO (Cliente)  <--->  CONTROLLER (Atendente) <---> SERVICE (Gerente) <---> REPOSITORY (Estoquista) <---> BANCO DE DADOS (Depósito)
      |                         |                         |                         |
  Vê o site              Anota o pedido            Confere o estoque         Pega/Guarda a caixa
```

---

## 2. O Recibo e os Itens (Models)
Para o sistema saber o que é um "Pedido", usamos dois conceitos fundamentais:

### Analogia do Recibo:
*   **Order (O Recibo):** É o papel principal. Contém a data e o valor total da compra.
*   **OrderItem (As Linhas do Recibo):** Cada linha detalha um produto específico. Ex: "1x Jogo God of War - R$ 200,00".

### Estrutura de Conexão:
```text
📦 PEDIDO #101 (Order)
|-- 📅 Data: 29/05/2026
|-- 💰 Total: R$ 450,00
|
|   [ Linhas do Pedido (OrderItems) ]
|   -------------------------------------------
|   1. 🎮 Elden Ring  | Qtd: 1 | R$ 250,00  <-- (Cada linha aponta para um Jogo)
|   2. 🎮 Minecraft   | Qtd: 2 | R$ 100,00
```

**Conceitos Técnicos:**
*   **@ManyToOne:** Define que **Muitos** itens podem pertencer a **Um** único pedido.
*   **Lombok (@Data):** Um assistente que escreve automaticamente os métodos de acesso (getters/setters), mantendo o código limpo.

---

## 3. O Gerente e a Regra do Estoque (Service)
O `OrderServiceImpl` atua como o **Gerente da Loja**. Ele é o cérebro que decide se uma venda pode acontecer.

### O Fluxo de Decisão:
1.  **Analisa o Pedido:** "O cliente quer levar 3 unidades do jogo X".
2.  **Consulta o Depósito:** "Temos essas 3 unidades na prateleira?".
3.  **Ação:**
    *   ✅ **Sucesso:** Se houver estoque, ele subtrai a quantidade e salva o pedido.
    *   ❌ **Erro:** Se não houver, ele cancela a operação e avisa: *"Estoque insuficiente!"*.

---

## 4. O Atendente (Controller)
O `OrderController` é o funcionário que interage diretamente com o cliente (o navegador):

*   **Listar Pedidos:** Busca todos os recibos salvos para mostrar na tela.
*   **Formulário de Criação:** Entrega uma página em branco para o cliente preencher.
*   **Salvar:** Recebe os dados preenchidos e os envia para o Gerente (Service) processar.

---

## 5. A Fachada da Loja (Thymeleaf + CSS)
A parte visual foi construída para manter a imersão gamer:

*   **Fragments (LEGO):** O menu e o rodapé são peças modulares. Se mudarmos o menu uma vez, a mudança reflete em todas as páginas da loja.
*   **JavaScript Dinâmico:** No formulário de pedido, o botão `+ Adicionar Jogo` permite adicionar novas linhas ao "recibo" sem precisar recarregar a página, tornando a experiência fluida.

---

## 6. O Segurança (SecurityConfig)
O sistema possui um "segurança" que controla quem pode acessar cada área:

*   **Registro/Home:** Acesso livre para todos.
*   **Catálogo/Pedidos:** Acesso apenas para usuários logados.
*   **Gerenciar Jogos (Criar/Editar/Deletar):** Acesso restrito a usuários com o crachá de **Admin**.

---
*Documento gerado para fins educacionais e suporte à apresentação do projeto.* 🚀
