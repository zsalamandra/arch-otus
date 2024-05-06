#!/bin/bash

echo "Введите количество итераций:"
read -r request_count

url_base="http://arch.homework/otusapp/zsalamandra/user"
url_error="http://arch.homework/otusapp/zsalamandra/error"

for (( i=1; i<=request_count; i++ ))
do
    action=$(( RANDOM % 4 ))

    case $action in
        0)
            # Создание пользователя
            name="name_$i"
            phone="+79$(jot -r 1 100000000 999999999)"  # jot для генерации случайных чисел в macOS
            json_data="{\"name\": \"$name\", \"phone\": \"$phone\"}"
            echo "Создание пользователя: $json_data"
            curl -s -X POST "$url_base" -H "Content-Type: application/json" -d "$json_data"
            ;;
        1)
            # Удаление пользователя
            echo "Удаление пользователя с ID $i"
            curl -s -X DELETE "${url_base}/${i}"
            ;;
        2)
            # Получение пользователя
            echo "Получение информации о пользователе с ID $i"
            curl -s -X GET "${url_base}/${i}"
            ;;
        3)
            # Вызов ошибки
            echo "Вызов ошибки"
            curl -s -X GET "$url_error"
            ;;
    esac
    echo ""  # Добавляем пустую строку для читаемости
done