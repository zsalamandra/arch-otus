#!/bin/bash

#Установка yg для чтения yaml файлов
if ! which yq > /dev/null; then
    echo "Установка yq..."
    sudo wget -O /usr/local/bin/yq "https://github.com/mikefarah/yq/releases/latest/download/yq_linux_amd64"
    sudo chmod +x /usr/local/bin/yq
fi

# Определение пути к файлу values.yaml
VALUES_YAML="values.yaml"

# Чтение namespace из values.yaml
NAMESPACE=$(yq e '.config.namespace' $VALUES_YAML)

# Настройка переменных
APP_RELEASE_NAME=$(yq e '.service.name' $VALUES_YAML)
PG_RELEASE_NAME=$(yq e '.postgresql.postgres-name' $VALUES_YAML)
DATABASE_NAME=$(yq e '.postgresql.database_name' $VALUES_YAML)
DATABASE_USER=$(yq e '.postgresql.database_user' $VALUES_YAML)
DATABASE_PWD=$(yq e '.postgresql.database_password' $VALUES_YAML)

# Создание namespace
echo "Создание namespace $NAMESPACE..."
kubectl create namespace "$NAMESPACE"

# Установка namespace как текущего для kubectl в этой сессии
kubectl config set-context --current --namespace="$NAMESPACE"

# Подготовка
echo "Добавление репозитория чартов bitnami и ingress-nginx..."
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx
helm repo update

# Установка PostgreSQL
echo "Установка PostgreSQL..."
helm upgrade --install "$PG_RELEASE_NAME" \
--namespace "$NAMESPACE" \
bitnami/postgresql \
--set auth.username="$DATABASE_USER" \
--set auth.password="$DATABASE_PWD" \
--set auth.database="$DATABASE_NAME"



# Проверка готовности PostgreSQL
echo "Ожидание готовности PostgreSQL..."
kubectl wait --namespace "$NAMESPACE" --for=condition=ready pod -l app.kubernetes.io/instance="$PG_RELEASE_NAME" --timeout=300s &
PID=$!

# Ожидание завершения команды kubectl wait
while kill -0 $PID 2> /dev/null; do
  echo -n "*"
  sleep 5
done
printf "\n"
echo "PostgreSQL установлен"
sleep 1



# Установка Ingress контроллера
echo "Установка Ingress контроллера..."
helm install nginx ingress-nginx/ingress-nginx --namespace "$NAMESPACE" -f ingress-controller.yaml

# Ожидание готовности ингресс контроллера
echo "Ожидание готовности ингресс контроллера..."
# Запуск команды kubectl wait в фоновом режиме
kubectl wait --namespace "$NAMESPACE" --for=condition=ready pod -l app.kubernetes.io/name=ingress-nginx --timeout=30s &
PID=$!

# Ожидание завершения команды kubectl wait
while kill -0 $PID 2> /dev/null; do
  echo -n "*"
  sleep 1
done

printf "\n"
echo "Ingress контроллер готов."
printf "\n\n"
sleep 1

# Развертывание приложения
echo "Разворачивание приложения..."
helm upgrade --install "$APP_RELEASE_NAME" --namespace "$NAMESPACE" . -f $VALUES_YAML


echo "Процесс развертывания завершен."
