#!/bin/sh

url="http://arch.homework/otusapp/zsalamandra/user"

request_count=10

i=1
while [ $i -le $request_count ]
do
  curl -s -X DELETE "${url}${i}"  # Отправка DELETE запроса
  i=$((i+1))  # Инкремент индекса
done
