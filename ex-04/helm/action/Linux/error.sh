#!/bin/sh

url="http://arch.homework/otusapp/zsalamandra/error"

request_count=1000

i=1
while [ $i -le $request_count ]
do
  curl -s -X GET "$url"
  i=$((i+1))  # Инкремент индекса
done
