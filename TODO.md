# Plano de Criação de Desafios de Lógica de Programação

## Informações Reunidas
- **Visão do Projeto**: Aplicação web Spring Boot Java para gerenciar jogos (operações CRUD). Arquivos principais analisados:
  | Arquivo | Resumo |
  |---------|--------|
  | `GameController.java` | Lida com requisições HTTP: lista jogos (`/game`), formulário de criação, salvar (com validação), deletar, editar. Usa templates Thymeleaf. Contém lógica como `if (result.hasErrors())`.
  | `GameServiceImpl.java` | Implementa CRUD: `findAll()`, `save()`, `findById()` com verificação `Optional` (`if (optional.isPresent())`), `deleteById()`. Interações básicas com repositório.
  | `Game.java` | Entidade JPA com campos (id, title, description, price, stockQuantity), validações (`@NotNull`, `@Min`, `@Size`). Getters/setters.
  | `GameService.java` | Interface definindo métodos CRUD.
  | Templates (ex: `index.html`, `create.html`) | Thymeleaf com loops (`th:each`), condicionais (`th:if`), formulários.

- Nenhum desafio ou quebra-cabeça de lógica existente. Lógica de jogo é CRUD simples; sem algoritmos complexos. Lógica existente é básica: verificações de validação, manipulação de Optional.

## Plano Aprovado (Traduzido para PT-BR)
Criar um **módulo dedicado 'desafios'** para exercícios de lógica de programação com lacunas para preencher ('___').

1. **Novo pacote**: `src/main/java/com/jogosdigitais/demo/challenges/` para classes Java de desafios.
2. **5 Desafios de Exemplo** (temáticos em 'jogos'):
   | Desafio | Descrição | Tipo | Lacunas |
   |---------|-----------|------|---------|
  | 1: Validar Preço de Jogo | Verificar se preço > 0 e < 1000. | if | Condição, valor de retorno |
   | 2: Calcular Desconto | 10% off se estoque < 5. | if/matemática | Fórmula de desconto |
   | 3: Listar Jogos com Baixo Estoque | Filtrar jogos com estoque < 10. | for-each/Stream | Condição do loop, adicionar à lista |
   | 4: Buscar Jogo por Título | Busca linear em lista. | for-loop | Condição do loop, comparação |
   | 5: Lógica Simples de Pontuação de Jogo | FizzBuzz: múltiplos de 3=\"Win\", 5=\"Loss\". | while/modulo | Loop, ifs |
3. **Formato**: Cada `.java` com Javadoc, método incompleto com `TODO` / lacunas, `main()` com testes.
4. **Integração**:
   - Link em `game/index.html` (seção \"Desafios de Lógica\").
   - Novo `ChallengeController.java` mapeando para HTML estático ou embed.
   - Novo template `challenges/index.html` listando desafios.
5. **Novo Diretório**: `src/main/resources/templates/challenges/`.
6. Sem alterações no núcleo existente.

## Arquivos Dependentes a Editar
- `src/main/resources/templates/game/index.html`: Adicionar link/botão para `/challenges`.

## Passos Lógicos a Completar
- [x] **Passo 1**: Criar `ChallengeController.java` e `challenges/index.html`. ✅
- [x] **Passo 2**: Criar 5 arquivos de desafios Java em `src/main/java/com/jogosdigitais/demo/challenges/`. ✅
- [x] **Passo 3**: Editar `game/index.html` para adicionar link aos desafios. ✅
- [ ] **Passo 4**: Testar com `mvn spring-boot:run` e verificar `/game` → link Desafios → `/challenges`.
- [ ] **Passo 5**: Completar tarefa com `attempt_completion`.

## Próximos Passos Após Edição
- Executar `mvn spring-boot:run`.
- Acessar `http://localhost:8080/game` e clicar em Desafios.
- Usuários editam arquivos Java localmente para resolver lacunas e rodam `main()`.

**Progresso: 3/5 passos completos. Desafios criados com lacunas intencionais (erros de compilação esperados até o usuário preencher).**

Atualizarei este TODO.md após cada passo completado.

