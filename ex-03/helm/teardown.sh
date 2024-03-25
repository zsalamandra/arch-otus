#!/bin/bash

# Настройка переменных
# Определение пути к файлу values.yaml
VALUES_YAML="values.yaml"

# Чтение namespace из values.yaml
NAMESPACE=$(yq e '.config.namespace' $VALUES_YAML)

# Удаление Helm релиза NGINX Ingress Controller, если он был установлен
echo "Удаляем Helm релиз NGINX Ingress Controller..."
helm uninstall nginx-ingress --namespace "$NAMESPACE"

# Удаление IngressClass, если он был создан
echo "Удаляем IngressClass..."
kubectl delete ingressclass nginx

# Удаление всех ресурсов в namespace
echo "Удаляем все ресурсы в namespace $NAMESPACE..."
kubectl delete all --all --namespace "$NAMESPACE"
kubectl delete namespace "$NAMESPACE"

# Смена текущего namespace на default
kubectl config set-context --current --namespace=default

echo "Очистка завершена."
