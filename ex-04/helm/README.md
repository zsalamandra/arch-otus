## Домашнее задание №4

### Запуск
Из папки ex-04/helm применить команды

Устанавливаем nginx ingress controller (встроенный в minikube не используем):
1) Установить в кластере prometheus:
```bash
kubectl create namespace prometheus &&
helm repo add prometheus-community https://prometheus-community.github.io/helm-charts &&
helm repo update &&
helm install prometheus prometheus-community/kube-prometheus-stack -n prometheus -f prometheus/values.yaml &&
kubectl apply -f prometheus/monitoring-nodeport.yaml
```

2) Если не установлен Ingress-контроллер его можно поставить по инструкции ниже поставить
```bash
helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx/ &&
helm repo update &&
helm install nginx ingress-nginx/ingress-nginx -f ingress-controller.yaml
```

Для проверки приложения проделать следующие шаги:
3) Выполнить команду установки:
```bash
kubectl create namespace dadaev-arch-otus &&
helm repo add bitnami https://charts.bitnami.com/bitnami &&
helm install ex04-postgresql bitnami/postgresql -n dadaev-arch-otus \
--set auth.username=root \
--set auth.password=root \
--set auth.database=ex04-db &&
helm install ex04-dadaev -n dadaev-arch-otus .
```

4) Открыть web интерфейс графаны
```bash
    minikube service -n monitoring prometheus-grafana-nodeport
```

5) Импортировать dashboard (файл - dadaev-dashboard.json)


6) Выполнить команду, имитирующую бурную деятельность:

для Linux:   
```bash
    bash action/Linux/action.sh
```
для MacOS:
```bash
    bash action/MacOS/action.sh
```
для Windows:
```bash
    bash action/Windows/action.sh
```

7) Выполнить удаления:
```bash
helm uninstall ex04-dadaev -n dadaev-arch-otus &&
helm uninstall ex04-postgresql -n dadaev-arch-otus &&
kubectl delete namespace dadaev-arch-otus &&
helm uninstall prometheus -n prometheus &&
kubectl delete -f prometheus/monitoring-nodeport.yaml &&
kubectl delete namespace prometheus
```