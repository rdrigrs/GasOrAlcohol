# ⛽ GasOrAlcohol App

## 📖 Sobre

Um aplicativo Android simples para calcular se é mais vantajoso abastecer o carro com gasolina ou álcool. O usuário insere a autonomia (km/L) e o preço por litro para ambos os combustíveis, e o aplicativo indica qual oferece o melhor custo-benefício.

## 🛠️ Arquitetura

Este projeto foi construído seguindo a arquitetura **MVVM (Model-View-ViewModel)**, que é o padrão recomendado para o desenvolvimento Android moderno.

*   **View (`MainActivity.kt`)**: A camada de UI, construída com Jetpack Compose. É responsável por exibir os dados e encaminhar as interações do usuário para o ViewModel.
*   **ViewModel (`MainViewModel.kt`)**: Contém a lógica de negócio e gerencia o estado da UI. Ele sobrevive a mudanças de configuração e fornece dados para a UI através de `StateFlow`.
*   **Model**: Representa a camada de dados. Neste aplicativo simples, a data class `MainUiState` e a lógica de cálculo dentro do ViewModel cumprem essa função.

## 🚀 Tecnologias Utilizadas

*   **Kotlin**: Linguagem de programação principal.
*   **Jetpack Compose**: Para a construção da UI nativa de forma declarativa.
*   **StateFlow**: Para o gerenciamento reativo de dados entre o ViewModel e a UI.
*   **Android ViewModel**: Para gerenciar dados relacionados à UI de forma consciente do ciclo de vida.
*   **Material 3**: Para os componentes de UI e estilo visual.

## 📸 Screenshots

*(Adicione aqui os prints da tela do seu aplicativo. Você pode capturá-los do emulador ou de um dispositivo físico.)*

**Tela Principal:**

`[Insira aqui a imagem da tela principal]`

**Resultado do Cálculo:**

`[Insira aqui a imagem mostrando o resultado]`

## ⚙️ Como Executar

1.  Clone este repositório:
    ```bash
    git clone https://github.com/seu-usuario/GasOrAlcohol.git
    ```
2.  Abra o projeto no Android Studio.
3.  Aguarde o Gradle sincronizar as dependências.
4.  Execute o aplicativo em um emulador ou dispositivo físico.
