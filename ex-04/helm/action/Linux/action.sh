#!/bin/bash

base_url="http://arch.homework/otusapp/zsalamandra/user"
error_url="http://arch.homework/otusapp/zsalamandra/error"

# Общее количество запросов
# Запрос количества итераций у пользователя
echo "Введите количество итераций:"
read -r request_count

i=1
while [ $i -le $request_count ]
do
    # Выбор случайного действия
    action=$(($RANDOM % 4))

    case $action in
        0)  # Создание пользователя
            name="name_$i"
            phone="+79$(shuf -i 100000000-999999999 -n 1)"
            json_data="{\"name\": \"$name\", \"phone\": \"$phone\"}"
            curl -X POST "$base_url" -H "Content-Type: application/json" -d "$json_data"
            echo " - Created user"
            ;;
        1)  # Удаление пользователя
            curl -s -X DELETE "${base_url}${i}"
            echo " - Deleted user ${i}"
            ;;
        2)  # Получение пользователя
            curl -s -X GET "${base_url}${i}"
            echo " - Retrieved user ${i}"
            ;;
        3)  # Генерация ошибки
            curl -s -X GET "$error_url"
            echo " - Generated error"
            ;;
    esac
    echo ""
    i=$((i + 1))
done
