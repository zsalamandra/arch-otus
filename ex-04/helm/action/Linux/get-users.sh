#!/bin/bash

url="http://arch.homework/otusapp/zsalamandra/user/"

request_count=10

i=1
while [ $i -le $request_count ]
do
  curl -s -X GET "${url}${i}"  # Отправка GET запроса
    echo ""
    i=$((i+1))  # Инкремент индекса
done
