# TaskEasy - Projeto Final 📱

**Disciplina:** Desenvolvimento de Aplicativos Móveis

**Professor:** Jason Antonio Pedroso Sobreiro

**Aluno:** Maycon Marschall Zanyck

**RGM:** 8138657961

---

## 🚀 Descrição do Projeto

O **TaskEasy** é um aplicativo Android nativo para gerenciamento de tarefas pessoais. O projeto foi desenvolvido como trabalho final da disciplina, com foco na implementação de uma arquitetura moderna (MVVM), persistência de dados local (Room) e sincronização em tempo real com a nuvem (Firebase).

## ✅ Requisitos Funcionais Implementados

O aplicativo atende a todos os requisitos funcionais planejados na estrutura do projeto:

* **RF01:** Cadastro e Login de usuários via **Firebase Authentication** (E-mail e Senha).
* **RF02:** Cadastro de novas tarefas (com título e descrição) através de um diálogo.
* **RF03:** Exibição de uma lista de todas as tarefas cadastradas pelo usuário logado.
* **RF04:** Exclusão de tarefas e Edição (marcar/desmarcar como concluída).
* **RF05:** Filtragem visual de tarefas (concluídas são riscadas automaticamente).
* **RF06:** Sincronização automática das tarefas com o **Firebase Firestore**, permitindo persistência na nuvem e uso offline através do cache do Room.

## 🛠️ Arquitetura e Tecnologias Utilizadas

O projeto foi estruturado seguindo as melhores práticas do desenvolvimento Android moderno:

* **Arquitetura:** MVVM (Model-View-ViewModel)
* **Linguagem:** Kotlin
* **UI:** Jetpack Compose (para a construção de interfaces declarativas)
* **Persistência Local:** Room (para cache offline e uso sem internet)
* **Autenticação:** Firebase Authentication
* **Banco de Dados (Nuvem):** Firebase Firestore (para sincronização em tempo real)
* **Navegação:** Jetpack Navigation Compose (para o fluxo entre telas)
* **Assincronismo:** Coroutines (com `Flow` e `StateFlow` para comunicação reativa entre as camadas)
* **Injeção de Dependência (Manual):** Uso de uma classe `Application` e `ViewModelFactory` para prover o Repositório de forma centralizada.

## 🖥️ Ambiente de Desenvolvimento

Este projeto foi construído utilizando uma arquitetura moderna e pode exigir uma versão recente do Android Studio para ser compilado corretamente.

* **Android Studio:** Otter | 2025.2.1 (ou superior)
* **JDK (Java):** O projeto está configurado para o **JDK 17**. Versões do Android Studio que usam JDK 11 por padrão podem falhar na sincronização do Gradle.
* **Bibliotecas:** Todas as versões de bibliotecas (Room, Firebase, etc.) são gerenciadas automaticamente pelo arquivo `gradle/libs.versions.toml`.

## ⚠️ Como Executar o Projeto

Para compilar e executar o projeto, siga os passos abaixo:

1.  Clone este repositório (`git clone ...`).
2.  **Importante:** Este projeto utiliza o Firebase. O arquivo de credenciais (`google-services.json`) **não está incluído** no repositório por boas práticas de segurança (ele está no `.gitignore`).
3.  Obtenha o arquivo `google-services.json` (que foi enviado em anexo na entrega do trabalho).
4.  No Android Studio, mude a visualização de "Android" para **"Project"**.
5.  Arraste e cole o arquivo `google-services.json` que você baixou diretamente para dentro da pasta **`app/`**.
6.  O caminho final obrigatório do arquivo deve ser: `TaskEasy/app/google-services.json`.
7.  Sincronize o Gradle (`Sync Project`) e execute o app (Run 'app').