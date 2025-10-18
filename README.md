# ⛽ GasOrAlcohol - Calculadora de Custo-Benefício de Combustível

## 📖 Sobre o Projeto

`GasOrAlcohol` é um aplicativo Android desenvolvido como parte de um trabalho acadêmico. Ele oferece uma solução simples e direta para um problema comum enfrentado por motoristas de veículos flex: decidir se é mais vantajoso, financeiramente, abastecer com gasolina ou álcool.

A funcionalidade principal do aplicativo consiste em:

1.  **Entrada de Dados**: O usuário insere quatro informações essenciais:
    *   A autonomia do veículo com gasolina (em km/L).
    *   A autonomia do veículo com álcool (em km/L).
    *   O preço atual do litro da gasolina.
    *   O preço atual do litro do álcool.

2.  **Cálculo de Custo-Benefício**: Ao clicar no botão "Calculate", o aplicativo processa os dados para determinar qual combustível oferece o maior rendimento por real gasto.

3.  **Exibição do Resultado**: O aplicativo exibe uma mensagem clara indicando qual combustível é a melhor opção no momento (`"Gas is better"`, `"Alcohol is better"`) ou se ambos são equivalentes.

## 👨‍🎓 Informações Acadêmicas

*   **Nome do Aluno**: `[Insira seu nome aqui]`
*   **Matrícula**: `[Insira sua matrícula aqui]`
*   **Instituição**: `[Insira o nome da sua instituição de ensino aqui]`
*   **Curso**: `[Insira o nome do seu curso aqui]`
*   **Disciplina**: `[Insira o nome da disciplina aqui]`
*   **Professor**: `[Insira o nome do seu professor aqui]`

## 🛠️ Arquitetura e Tecnologias

Este projeto foi construído seguindo a arquitetura **MVVM (Model-View-ViewModel)**, o padrão recomendado para o desenvolvimento Android moderno, utilizando as seguintes tecnologias:

*   **Kotlin**: Linguagem de programação principal.
*   **Jetpack Compose**: Para a construção da UI nativa de forma declarativa.
*   **StateFlow**: Para o gerenciamento reativo de dados entre o ViewModel e a UI.
*   **Android ViewModel**: Para gerenciar dados relacionados à UI de forma consciente do ciclo de vida.
*   **Material 3**: Para os componentes de UI e estilo visual.

## 📸 Screenshots

**Tela Principal:**

![Tela Principal do App](screenshots/tela_principal.png)

**Resultado do Cálculo:**

![Resultado do Cálculo](screenshots/resultado_calculo.png)

## ⚙️ Como Executar

1.  Clone este repositório.
2.  Abra o projeto no Android Studio.
3.  Aguarde a sincronização do Gradle.
4.  Execute o aplicativo em um emulador ou dispositivo físico.
