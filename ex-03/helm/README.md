## Домашнее задание №3

### Запуск
Из папки ex-03/helm применить команды

Для проверки приложения проделать следующие шаги:
1. Выполнить команду установки:
```
kubectl create namespace dadaev-arch-otus &&
helm repo add bitnami https://charts.bitnami.com/bitnami &&
helm install ex03-postgresql bitnami/postgresql -n dadaev-arch-otus \
--set auth.username=root \
--set auth.password=root \
--set auth.database=ex03-db &&
helm install ex03-dadaev -n dadaev-arch-otus .
```
2. Выполнить команду проверки:
```
    newman run otus-ex-03.postman_collection.json
```
3. Выполнить удаления:
```
helm uninstall ex03-dadaev -n dadaev-arch-otus &&
helm uninstall ex03-postgresql -n dadaev-arch-otus &&
kubectl delete namespace dadaev-arch-otus
```

В этом ДЗ вы создадите простейший RESTful CRUD.


Описание/Пошаговая инструкция выполнения домашнего задания:
Сделать простейший RESTful CRUD по созданию, удалению, просмотру и обновлению пользователей.
Пример API - https://app.swaggerhub.com/apis/otus55/users/1.0.0
Добавить базу данных для приложения.
Конфигурация приложения должна хранится в Configmaps.
Доступы к БД должны храниться в Secrets.
Первоначальные миграции должны быть оформлены в качестве Job-ы, если это требуется.
Ingress-ы должны также вести на url arch.homework/ (как и в прошлом задании)
На выходе должны быть предоставлена

ссылка на директорию в github, где находится директория с манифестами кубернетеса
инструкция по запуску приложения.
команда установки БД из helm, вместе с файлом values.yaml.
команда применения первоначальных миграций
команда kubectl apply -f, которая запускает в правильном порядке манифесты кубернетеса
Postman коллекция, в которой будут представлены примеры запросов к сервису на создание, получение, изменение и удаление пользователя. Важно: в postman коллекции использовать базовый url - arch.homework.
Проверить корректность работы приложения используя созданную коллекцию newman run коллекция_постман и приложить скриншот/вывод исполнения корректной работы
Задание со звездочкой:
Добавить шаблонизацию приложения в helm чартах
