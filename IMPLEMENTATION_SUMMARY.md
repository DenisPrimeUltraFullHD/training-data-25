# Реалізація операцій з Vector, PriorityQueue та Map

## Виконані поліпшення

### 1. **BasicDataOperationUsingList.java**
   - ✅ Додано розширене логування в конструктор з інформацією про розмір Vector
   - ✅ Структуровано виконання операцій на 4 етапи:
     - **ЕТАП 1**: Пошук і визначення мін/макс у Vector (до сортування)
     - **ЕТАП 2**: Сортування Vector
     - **ЕТАП 3**: Пошук і визначення мін/макс (після сортування)
     - **ЕТАП 4**: Збереження відсортованого масиву
   - ✅ Додано форматування з використанням блоків та emoji для наглядності

### 2. **BasicDataOperationUsingQueue.java**
   - ✅ Додано розширене логування в конструктор
   - ✅ Структуровано виконання на 4 етапи аналогічно List:
     - **ЕТАП 1**: Пошук і визначення мін/макс у PriorityQueue
     - **ЕТАП 2**: Базові операції (peek, poll)
     - **ЕТАП 3**: Операції з масивом
     - **ЕТАП 4**: Збереження результатів
   - ✅ Поліпшено метод `performQueueOperations()`:
     - Додано відображення розміру черги перед операціями
     - Додано вимірювання часу виконання peek() і poll()
     - Додано інформацію про розмір після видалення

### 3. **BasicDataOperationUsingMap.java** ⭐ НОВИЙ МОДУЛЬ
   - ✅ **Замінено клас `Canary` на Java record-подібний клас `Pet`**:
     - Простий data class з перевизначеними методами `equals()`, `hashCode()`, та `toString()`
     - Еквівалент Java 14+ record, але сумісний з Java 11
     - Методи доступу: `nickname()` та `species()`
   
   - ✅ **Створено спеціалізований `Comparator` для Pet**:
     ```java
     private static final Comparator<Pet> PET_COMPARATOR = 
         Comparator.comparing(Pet::nickname).thenComparing(Pet::species, Comparator.reverseOrder());
     ```
     - Сортування за nickname в порядку зростання
     - При однаковому nickname: сортування за species в порядку спадання
   
   - ✅ **Аналіз швидкості обробки даних** (метод `compareMapPerformance()`):
     - Тестування на **10,000 елементів**
     - Вимірювання операцій:
       - **Додавання елементів**: HashMap vs LinkedHashMap vs TreeMap
       - **Пошук елементів**: HashMap vs LinkedHashMap vs TreeMap
     - Вивід результатів у форматованій таблиці
     - Висновки про складність O(1) для HashMap/LinkedHashMap та O(log n) для TreeMap

### 4. **Вимірювання продуктивності**
   - ✅ Всі операції відстежуються за допомогою оновленого `PerformanceTracker`
   - ✅ Час вимірюється в наносекундах та мілісекундах
   - ✅ Логування включає:
     - Час пошуку в Vector / PriorityQueue / Map
     - Час визначення мін/макс
     - Час сортування
     - Час операцій peek() / poll()
     - Час операцій додавання/пошуку в Map структурах

## Приклади виконання

```bash
# Запуск List операцій з пошуком символа '&'
java BasicDataOperation "&"

# Запуск Map операцій
java BasicDataOperationUsingMap

# Запуск Queue операцій з пошуком символа 'A'
java BasicDataOperation "A"
```

## Структура логів

Кожен запуск демонструє:

1. **Таблиця операцій Vector<Character>**
   - Розмір: 176 елементів
   - Операції на несортованому Vector
   - Операції на сортованому Vector

2. **Таблиця операцій PriorityQueue<Character>**
   - Розмір: 176 елементів
   - Пошук та мін/макс
   - Операції peek() та poll()
   - Демонстрація зміни розміру черги

3. **Таблиця операцій LinkedHashMap та TreeMap**
   - Пошук за ключем та значенням
   - Додавання та видалення записів
   - Сортування LinkedHashMap
   - Виведення пар ключ-значення

4. **НОВИЙ: Аналіз швидкості обробки даних**
   - Порівняння HashMap, LinkedHashMap та TreeMap
   - Операції додавання та пошуку на 10,000 елементах
   - Табличне представлення результатів
   - Аналіз складності алгоритмів

## Файли для збереження результатів

- `list/Character.data.sorted` - Відсортований масив символів

## Ключові особливості реалізації

- ✅ Детальне логування всіх операцій
- ✅ Вимірювання продуктивності в наносекундах та мілісекундах
- ✅ Демонстрація різниці у часі виконання операцій на сортованих/несортованих даних
- ✅ Демонстрація операцій queue (peek, poll)
- ✅ Новий module для роботи з Map структурами
- ✅ Java record-подібна реалізація класу Pet (сумісна з Java 11)
- ✅ Користувацький Comparator для сортування Pet
- ✅ Комплексний аналіз продуктивності різних Map типів
- ✅ Форматований вивід з використанням emoji та блоків
- ✅ Обробка помилок та граничних випадків

## Архітектура проекту

```
training-data-25/
├── BasicDataOperation.java                    (основний клас для List/Queue/Set операцій)
├── BasicDataOperationUsingList.java           (операції з Vector<Character>)
├── BasicDataOperationUsingQueue.java          (операції з PriorityQueue<Character>)
├── BasicDataOperationUsingSet.java            (операції з TreeSet<Character>)
├── BasicDataOperationUsingMap.java            ⭐ (операції з LinkedHashMap/TreeMap + аналіз)
├── DataFileHandler.java                       (завантаження/збереження даних)
├── PerformanceTracker.java                    (відстеження продуктивності)
└── list/
    ├── Character.data                         (вихідні дані)
    └── Character.data.sorted                  (відсортовані дані)
```

## Компіляція і запуск

```bash
# Компіляція
javac *.java

# Запуск
java BasicDataOperation "&"
```
