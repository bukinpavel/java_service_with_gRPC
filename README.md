# Java service with gRPC

Пример состоит из двух контейнеров:

- `demo-service` принимает HTTP-запрос на `localhost:8080`;
- `orderService` принимает gRPC-запросы на порту `9090`;
- `demo-service` вызывает `orderService` по gRPC и возвращает его ответ как JSON.

## Запуск

```bash
docker compose up --build
```

Проверка через localhost:

```bash
curl http://localhost:8080/api/orders/order-42
```

Ожидаемый ответ:

```json
{"orderId":"order-42","status":"CONFIRMED","message":"Ответ получен от orderService"}
```

gRPC-порт `orderService` также опубликован как `localhost:9090`. Server reflection включён, поэтому контракт можно посмотреть через `grpcurl`:

```bash
grpcurl -plaintext localhost:9090 list
```

Остановка:

```bash
docker compose down
```

## Тесты

```bash
./mvnw test
```
