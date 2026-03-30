# Завдання з рефакторингу: Додавання логування

## Мета
Додати логування до існуючого проєкту (використовуючи SLF4J та Log4j2) для відстеження основних кроків виконання тестів та сервісів.

## Вимоги до виконання
1.  **Логування**: Використовувати SLF4J для виводу інформації про:
    *   Початок та завершення тесту.
    *   Ключові дії в сервісах (відкриття сторінки, заповнення полів, кліки).
    *   Результати перевірок (`assertions`).

2.  **Налаштування рівнів логування**:
    Конфігурація логера знаходиться у файлі `src/test/resources/log4j2.properties`.

    Який рівень де виставляти:
    *   **INFO**: Використовуйте для основних кроків бізнес-логіки, які мають бути видимі при звичайному запуску тестів (наприклад: "Starting test...", "User logged in successfully").
    *   **DEBUG**: Для детальної інформації про технічні дії, які допомагають при налагодженні (наприклад: "Wait for element 'submit_button' to be clickable", "Entering text 'user@email.com' into email field").
    *   **ERROR**: Для логування помилок, критичних збоїв або непередбачених ситуацій (наприклад, у блоках `catch`).
    *   **TRACE**: Найбільш детальний рівень, зазвичай використовується для дуже дрібних деталей (наприклад, стан об'єкта драйвера).
    *   **WARN**: Для попереджень про потенційні проблеми, які не зупиняють виконання (наприклад: "Element found, but it is not yet visible, waiting...").

---

# Refactoring Task: Adding Logging

## Goal
Add logging to the existing project (using SLF4J and Log4j2) to track the main execution steps in tests and services.

## Requirements
1.  **Logging**: Use SLF4J to output information about:
    *   Test it start and end.
    *   Key actions in services (opening pages, filling fields, clicks).
    *   Assertion results.

2.  **Logging Level Configuration**:
    Logger configuration is located in `src/test/resources/log4j2.properties`.

    Which level to use where:
    *   **INFO**: Use for main business logic steps that should be visible during normal test execution (e.g., "Starting test...", "User logged in successfully").
    *   **DEBUG**: For detailed information about technical actions that help with debugging (e.g., "Wait for element 'submit_button' to be clickable", "Entering text 'user@email.com' into the email field").
    *   **ERROR**: For logging errors, critical failures, or unexpected situations (e.g., in `catch` blocks).
    *   **TRACE**: The most detailed level, usually used for very fine-grained details (e.g., driver object state).
    *   **WARN**: For warnings about potential issues that do not stop execution (e.g., "Element found, but it is not yet visible, waiting...").
