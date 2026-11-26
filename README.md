# VerdeEmCasa — Diário de Cultivo e Rega de Plantas

Aplicativo Android autoral desenvolvido como **Atividade Prática Final** da disciplina **Fundamentos do Desenvolvimento Mobile** (Centro Universitário Internacional UNINTER).

## Equipe

| Integrante | RU |
| :--- | :--- |
| **João Paulo Andrade Mathias** | `5180040` |
| **Gustavo Henrique Bozza** | `5226624` |
| **Mikayu Nascimento Silva** | `5252729` |

---

## Sobre o Aplicativo

O **VerdeEmCasa** é um diário offline de cuidados para plantas domésticas. Ele resolve um problema comum de moradores de apartamentos e iniciantes em jardinagem: lembrar o nível de luminosidade adequado, a frequência de rega e a data da última rega de cada planta.

### Funcionalidades Principais
1. **Cadastro de Plantas com Validação**: Registro de nome popular, luminosidade ideal (*Sol Pleno*, *Meia-Sombra* ou *Sombra*), intervalo de rega em dias (validado entre 1 e 90 dias) e observações de cultivo.
2. **Consulta e Filtro por Luminosidade**: Exibição das plantas em cards organizados com contagem resumida e chips de filtro (*Todas*, *Sol Pleno*, *Meia-Sombra*, *Sombra*).
3. **Registro de Rega ("Registrar Rega")**: Atualização rápida da data da última rega diretamente na tela inicial ou na ficha de detalhes, com feedback visual via `Snackbar`.
4. **Ficha de Detalhes e Exclusão com Confirmação**: Visualização completa das orientações de cultivo e opção de excluir a planta mediante diálogo de confirmação (`AlertDialog`).
5. **Persistência Local Duradoura**: Todos os registros são salvos localmente com **Jetpack DataStore Preferences** (serializados em JSON) e permanecem disponíveis após fechar e reabrir o aplicativo.

---

## Tecnologias Utilizadas

- **IDE**: Android Studio
- **Linguagem**: Kotlin `2.0.21`
- **Interface**: Jetpack Compose + Material 3 (`compose-bom:2024.11.00`)
- **Navegação**: Navigation Compose (`androidx.navigation:navigation-compose:2.8.4`)
- **Persistência**: Jetpack DataStore Preferences (`androidx.datastore:datastore-preferences:1.1.1`)
- **Testes Unitários**: JUnit 4 (`PlantValidatorTest`)
- **SDK**: `minSdk = 24` (Android 7.0) | `compileSdk = 35` | `targetSdk = 35`

---

## Estrutura do Projeto

```text
VerdeEmCasa/
├── app/
│   ├── build.gradle.kts
│   └── src/
│       ├── main/
│       │   ├── AndroidManifest.xml
│       │   ├── java/br/edu/uninter/verdeemcasa/
│       │   │   ├── MainActivity.kt
│       │   │   ├── data/
│       │   │   │   ├── PlantJsonSerializer.kt
│       │   │   │   └── PlantRepository.kt
│       │   │   ├── model/
│       │   │   │   ├── Plant.kt
│       │   │   │   └── PlantValidator.kt
│       │   │   └── ui/
│       │   │       ├── VerdeEmCasaApp.kt
│       │   │       ├── screens/
│       │   │       │   ├── HomeScreen.kt
│       │   │       │   ├── AddPlantScreen.kt
│       │   │       │   └── PlantDetailScreen.kt
│       │   │       └── theme/
│       │   │           └── Theme.kt
│       │   └── res/values/
│       │       ├── strings.xml
│       │       └── themes.xml
│       └── test/java/br/edu/uninter/verdeemcasa/
│           └── PlantValidatorTest.kt
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── README.md
```

---

## Como Abrir e Executar no Android Studio

1. **Clonar ou baixar o repositório**:
   ```bash
   git clone https://github.com/Joaopaulojpam/VerdeEmCasa.git
   ```
2. **Abrir no Android Studio**:
   - Abra o **Android Studio** (versão Ladybug / Meerkat ou superior recomendada, com JDK 17+).
   - Selecione **File > Open** e escolha a pasta `VerdeEmCasa`.
3. **Sincronizar o Gradle**:
   - Aguarde o *Gradle Sync* baixar as dependências automaticamente (conexão à internet necessária na primeira abertura).
4. **Executar no Emulador ou Dispositivo Físico**:
   - Selecione um emulador Android (API 24 ou superior, ex.: Pixel 6 – API 34/35) ou conecte um smartphone Android com Depuração USB ativa.
   - Clique no botão **Run (▶)** para compilar e instalar o aplicativo.
5. **Executar os Testes Unitários**:
   - Clique com o botão direito em `app/src/test/java/br/edu/uninter/verdeemcasa/PlantValidatorTest.kt` e selecione **Run 'PlantValidatorTest'**.

---

## Dados Fictícios para Teste Rápido

Você pode testar o fluxo completo cadastrando plantas fictícias como:
- **Jiboia** — *Meia-Sombra*, rega a cada `3` dias, observação: *"Manter solo levemente úmido; evitar sol direto."*
- **Manjericão** — *Sol Pleno*, rega a cada `1` dia, observação: *"Regar no início da manhã; podar flores."*
- **Espada-de-São-Jorge** — *Sombra*, rega a cada `10` dias, observação: *"Tolera períodos secos; evitar encharcar."*
