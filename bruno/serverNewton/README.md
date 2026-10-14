# Collection Bruno — serverNewton (API PW44S)

Collection para testar a API REST do projeto `pw44sMestreDoNo` (Spring Boot, porta 8080).

## Como usar

1. Inicie a aplicação: `./mvnw spring-boot:run` (ou pelo seu IDE).
2. No Bruno: **File → Open Collection** e selecione a pasta `bruno/serverNewton`.
3. Selecione o ambiente **local** (canto superior direito). A variável `baseUrl` aponta para `http://localhost:8080`.
4. Siga a ordem sugerida pelas pastas:
   1. **Users → Create User** — cria um usuário de teste (username gerado a cada execução em `testUsername`, senha `Senha123`).
   2. **Auth → Login** — autentica e **salva o token automaticamente** na variável de ambiente `token`.
   3. **Categories / Products** — CRUD público (não precisa de token). Cada pasta começa com *Create* e guarda o id criado (`categoryId` / `productId`), usado por Get/Update/Delete. Os dados do `import.sql` não são alterados nem apagados.
   4. **Addresses / Orders** — endpoints protegidos por JWT (Bearer `{{token}}`). Em Orders, *Create Order* guarda o `orderId` usado em *Get Order by Id*.
   5. **Auth → Login Invalido / Requisicao Sem Token** — verificam os erros 401.

## Observações

- A API aceita `username` duplicado no cadastro e, depois disso, o login do usuário retorna 500. Por isso o `Create User` gera um username novo a cada execução.
- Excluir categoria com produtos vinculados retorna 500 (violação de FK), por isso o *Delete* só remove registros criados pela própria collection.
- O banco é **H2 em memória** (`jdbc:h2:mem:testdb`): os dados são perdidos ao reiniciar. O console do H2 fica em `http://localhost:8080/h2-console` (user `sa`, sem senha).
- Os usuários criados pelo `import.sql` (joao.silva, maria.souza) têm hashes de senha fictícios — **não** servem para login. Crie um usuário pelo endpoint antes de logar.
- Senha deve ter no mínimo 6 caracteres, com letra minúscula, maiúscula e dígito.
- O Bruno também tem CLI (`bru run`) para executar a collection automaticamente:
  `npx @usebruno/cli run bruno/serverNewton --env local`

## Estrutura

```
serverNewton/
├── bruno.json            # metadados da collection
├── environments/local.bru  # baseUrl e token
├── Auth/                 # login, login inválido, request sem token
├── Users/                # create user (público)
├── Categories/           # CRUD completo (público)
├── Products/             # CRUD completo (público)
├── Addresses/            # listar/criar (protegido)
├── Orders/               # listar/criar (protegido)
└── Infra/                # H2 console
```
