url, по которому можно будет получить ответ от сервиса (либо тест в postmanе).
Задание со звездой (+5 баллов):*
В Ingress-е должно быть правило, которое форвардит все запросы с /otusapp/{student name}/* на сервис с rewrite-ом пути. Где {student name} - это имя студента.
Например: curl arch.homework/otusapp/aeugene/health -> рерайт пути на arch.homework/health


#### Установки:

Командой `minikube ip` узнать ip адрес кубика, полученный адрес вбить в etc/hosts
пример записи в файле hosts - `192.168.59.100 arch.homework`

Устанавливаем nginx ingress controller (встроенный в minikube не используем):
- `kubectl create namespace otus-arch` 
- `kubectl config set-context --current --namespace=otus-arch`
- `helm repo add ingress-nginx https://kubernetes.github.io/ingress-nginx/` 
- `helm repo update` 
- `helm install nginx ingress-nginx/ingress-nginx --namespace otus-arch -f nginx-ingress.yaml`

Разворачивание сервиса (команду выполнить из папки templates):
- `kubectl apply -f deployment.yaml -f service.yaml -f ingress.yaml`

Очистки:
- `kubectl delete -f deployment.yaml -f service.yaml -f ingress.yaml`
- `kubectl delete namespace otus-arch`

Проверка:
сходить: 
- `curl http://arch.homework/health`
- `curl http://arch.homework/otusapp/zsalamandra/health`