# Guia de Instalação e Execução - Catálogo de Jogos Digitais

Este documento fornece as instruções necessárias para clonar, configurar e rodar este projeto em qualquer máquina.

## 📋 Pré-requisitos

Antes de começar, certifique-se de ter instalado na máquina:

1.  **Java JDK 17 ou superior** (O projeto foi validado com Java 21).
2.  **Git** (Para clonar o repositório).
3.  **VS Code** (Recomendado) com o pacote de extensões "Extension Pack for Java".

---

## 🚀 Como Rodar o Projeto

### 1. Clonar o Repositório
Abra o terminal ou prompt de comando e execute:
```bash
git clone <URL_DO_SEU_REPOSITORIO>
cd Jogos-Digitais-WEB-2
```

### 2. Configuração do Banco de Dados
O projeto está configurado para usar o **H2 (Banco de Dados em Memória)** por padrão. Isso facilita a apresentação, pois **não exige a instalação do MySQL**.

*   **Vantagem:** Funciona instantaneamente em qualquer máquina.
*   **Observação:** Os dados (jogos e usuários cadastrados) são apagados sempre que você fechar a aplicação.

### 3. Executando a Aplicação

#### Opção A: Pelo Terminal (Mais garantido para apresentações)
No diretório raiz do projeto, execute o comando abaixo:

**No Windows:**
```powershell
.\mvnw spring-boot:run
```

**No Linux/Mac:**
```bash
chmod +x mvnw
./mvnw spring-boot:run
```

#### Opção B: Pelo VS Code
1. Abra a pasta do projeto no VS Code.
2. Vá em `src/main/java/com/jogosdigitais/demo/DemoApplication.java`.
3. Clique no botão **Run** acima do método `main`.

---

## 🔑 Acesso e Login

Após o projeto iniciar (você verá a mensagem `Started DemoApplication` no console):

1.  Acesse: [http://localhost:8080/game](http://localhost:8080/game)
2.  **Primeiro Acesso:** Como o banco inicia vazio, você precisa se cadastrar.
3.  Vá em [http://localhost:8080/register](http://localhost:8080/register).
4.  No campo **Autoridades/Roles**, escreva exatamente: `Admin` (Isso é necessário para ter permissão de gerenciar os jogos).
5.  Faça o login com o email e senha cadastrados.
6.  **Gerenciar Pedidos:** Agora você pode acessar o menu "Jogos > Listar Pedidos" para realizar vendas, selecionando jogos e definindo quantidades.

---

## 💎 Novas Funcionalidades (Sincronização)

Este projeto foi atualizado para incluir um **Sistema Completo de Pedidos**, sincronizado com as melhores práticas de desenvolvimento Spring Boot:

*   **Gestão de Estoque:** O sistema valida automaticamente se há unidades disponíveis antes de confirmar uma venda.
*   **Interface Dinâmica:** Adição de múltiplos itens em um único pedido de forma fluida.
*   **Arquitetura Limpa:** Uso de Services e Repositories dedicados para garantir a organização do código.

---

## 📚 Aprendizado e Documentação

Para entender o funcionamento interno do código (lógica, analogias e arquitetura), consulte o arquivo:
👉 `EXPLICACAO_TECNICA.md` (na raiz do projeto).

---

## 🛠️ Resolução de Problemas Comuns

*   **Erro: "Port 8080 was already in use":**
    Isso significa que outro programa está usando a porta 8080. Você pode fechar o programa ou mudar a porta no arquivo `src/main/resources/application.properties` adicionando a linha: `server.port=8081`.
*   **CSS não carrega:** 
    Se a página parecer "quebrada", faça um **Hard Refresh** no navegador (Ctrl + F5).
*   **Console do Banco de Dados:**
    Para verificar os dados inseridos manualmente, acesse: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
    - **JDBC URL:** `jdbc:h2:mem:testdb`
    - **User:** `sa`
    - **Password:** (deixe em branco)

---
*Boa sorte na apresentação!* 🎮
