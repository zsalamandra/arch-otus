#!/bin/bash

url="http://arch.homework/otusapp/zsalamandra/user"

request_count=5

i=1
while [ $i -le $request_count ]
do
    # Генерация случайного имени
    name="name_$i"

    # Генерация случайного номера телефона
    phone="+79$(shuf -i 100000000-999999999 -n 1)"

    # Формирование JSON
    json_data="{\"name\": \"$name\", \"phone\": \"$phone\"}"

    # Отправка POST запроса с JSON
    curl -X POST "$url" -H "Content-Type: application/json" -d "$json_data"

    i=$((i + 1))
done
