# Rygiel
Projekt ułatwiający zarządzanie firmą rusztowaniową, napisany w javie, wykorzystujący takie technologie jak: javafx, spring, hibernate, baza danych H2 (możliwość wymiany na inną implementując interfejs `IDatabaseEngine`).
![Logo Rygla](src/main/resources/com/scaffolding/png/logo.png)
## Organizacja kodu

Opis najważniejszych pakietów
- `com.scaffolding` zawiera punkt wejścia programu (klasa `Launcher`) oraz klasę `ScaffoldingApplication` rozszerzająca klasę `javafx.application.Application` oraz inicjalizującą kontekst springa.
- `com.scaffolding.controllers` zawiera zestaw kontrolerów widoków FXML, których jedynym zadanie są aktualizacje interfejsu graficznego użytkownika.
- `com.scaffolding.managers` zawiera całą logikę biznesową programu.
- `com.scaffolding.services` zawiera usługi uruchamiane w nowych wątkach (głównie zapytania do bazy danych)
- `com.scaffolding.database` zawiera klasy dostępowe do bazy danych
- `com.scaffolding.model` zawiera zmapowane tabele bazodanowe na obiekty javy.
- `com.scaffolding.factories` zawiera klasy pomocnicze

## Zasoby

- `/com/scaffolding/view` pliki FXML zawierające definicje interfejsu użytkownika
- `/com/scaffolding/configuration` pliki konfiguracyjne spring i hibernate
