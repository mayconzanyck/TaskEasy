# TaskEasy - Projeto Final 📱

**Disciplina:** Desenvolvimento de Aplicativos Móveis

**Professor:** Jason Antonio Pedroso Sobreiro

**Aluno:** Maycon Marschall Zanyck

**RGM:** 8138657961

---

## 🚀 Descrição do Projeto

O **TaskEasy** é um aplicativo Android nativo para gerenciamento de tarefas pessoais. O projeto foi desenvolvido como trabalho final da disciplina, com foco na implementação de uma arquitetura moderna (MVVM), persistência de dados local (Room) e sincronização em tempo real com a nuvem (Firebase).

## ✅ Requisitos Funcionais Implementados

* **RF01:** Cadastro e Login de usuários via **Firebase Authentication**.
* **RF02:** Cadastro de novas tarefas (com título e descrição).
* **RF03:** Exibição da lista de tarefas cadastradas pelo usuário logado.
* **RF04:** Exclusão e Edição de tarefas (marcar/desmarcar como concluída).
* **RF05:** Filtragem visual de tarefas (concluídas são riscadas).
* **RF06:** Sincronização automática das tarefas com o **Firebase Firestore**.

---

## 🛠️ Arquitetura e Tecnologias Utilizadas

* **Arquitetura:** MVVM (Model-View-ViewModel)
* **Linguagem:** Kotlin
* **UI:** Jetpack Compose
* **Persistência Local:** Room
* **Autenticação:** Firebase Authentication
* **Banco de Dados (Nuvem):** Firebase Firestore
* **Navegação:** Jetpack Navigation Compose
* **Assincronismo:** Coroutines (com `Flow` e `StateFlow`)

## 🖥️ Ambiente de Desenvolvimento

* **Android Studio:** Otter | 2025.2.1 (ou superior)
* **JDK (Java):** O projeto está configurado para o **JDK 17**.
* **Bibliotecas:** Todas as versões de bibliotecas são gerenciadas automaticamente pelo arquivo `gradle/libs.versions.toml`.

---

## 🗺️ Diagrama de Navegação
O aplicativo utiliza uma única Activity com **Jetpack Navigation Compose**. O fluxo de navegação principal é o seguinte:

```
                              (Login OK)
                              
[Tela de Login (Start)] --------------------------------> [Tela Principal (Home)]
      |     ^                                                      |
(Clica 'Registrar') |     | (Clica 'Voltar' ou 'Salvar')                 (Clica 'Sair')
      |     |                                                      |
      v     |                                                      v
[Tela de Registro]  <----------------------------------------------+

```

## 🗃️ Diagrama do Banco de Dados (Entidade-Relacionamento)
O aplicativo utiliza uma relação de **1-N** (Um-para-Muitos). A entidade `Usuário` (gerenciada pelo Firebase) "possui" as `Tarefas` (gerenciadas pelo Firestore e cacheadas pelo Room).

```
[Usuário (Firebase Auth)]
       |
       | (1) - Possui - (N)
       |
       v
[Tarefa] (Entidade Room / Documento Firestore)
    - id (String) [PK]
    - titulo (String)
    - descricao (String)
    - data (String)
    - concluida (Boolean)
    - usuarioId (String) [FK p/ Usuário.uid]
```



## ☁️ Endpoints de API / Serviços de Nuvem
O projeto não utiliza uma API REST tradicional. Em vez disso, ele consome os serviços de *Backend-as-a-Service* (BaaS) do Google Firebase:

1.  **Firebase Authentication:**
    * **Método:** E-mail e Senha.
    * **Funções:** `createUserWithEmailAndPassword`, `signInWithEmailAndPassword`, `signOut`.
    * **Propósito:** Gerencia o cadastro (RF01) e fornece o `userId` para as regras de segurança.

2.  **Firebase Firestore:**
    * **Serviço:** Banco de Dados NoSQL em tempo real.
    * **Estrutura:** `usuarios/{userId}/tarefas/{tarefaId}`
    * **Propósito:** Armazena, atualiza e deleta as tarefas (RF02, RF04, RF06), garantindo a sincronização na nuvem. As regras de segurança garantem que um usuário só possa ver e editar suas próprias tarefas.

---

## ⚠️ Como Executar o Projeto

1.  Clone este repositório.
2.  **Importante:** Este projeto utiliza o Firebase. O arquivo de credenciais (`google-services.json`) **não está incluído** no repositório por boas práticas de segurança (ele está no `.gitignore`).
3.  Obtenha o arquivo `google-services.json` (que foi enviado em anexo na entrega do trabalho).
4.  No Android Studio, mude a visualização de "Android" para **"Project"**.
5.  Arraste e cole o arquivo `google-services.json` que você baixou diretamente para dentro da pasta **`app/`**.
6.  Sincronize o Gradle e execute o app (Run 'app').