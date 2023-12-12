Для отправки запросов используется Postman.

Endpoints:
GET
/new-game - создание новой игры
  @param int initiator - 1 = пользователь, ходит крестиком. 2 = машина, ноликом

GET
/boards/{id} - получение доски по id в виде UUID значения

POST
/moves - сделать ход
  @param - номер ячейки (0 - 8), id доски

DELETE
/moves - отмена последнего хода пользователя и машины
  @param - id доски


DATABASE:
Использовалась БД H2
Некоторые параметры были добвлены в Env переменные:
spring.datasource.url,
jpa.database-platform  (org.hibernate.dialect.H2Dialect)

# URL Console: http://localhost:8080/h2-console
# JDBC - jdbc:h2:mem:game
# Driver Class: org.h2.Driver
# Username: sa  (По умолчанию)
# Password:     (По умолчанию пусто)
