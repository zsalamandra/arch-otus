@echo off
set /p request_count="Enter the number of requests: "

set "url=http://arch.homework/otusapp/zsalamandra/user"

for /l %%i in (1, 1, %request_count%) do (
    set /a "action=%random% %% 4"
    
    if !action! == 0 (
        set "name=name_%%i"
        set /a "phone=+79%random:~-9%"
        curl -X POST "!url!" -H "Content-Type: application/json" -d "{\"name\": \"!name!\", \"phone\": \"!phone!\"}"
    )
    if !action! == 1 (
        curl -X DELETE "!url!/%%i"
    )
    if !action! == 2 (
        curl -X GET "!url!/%%i"
    )
    if !action! == 3 (
        curl -X GET "http://arch.homework/otusapp/zsalamandra/error"
    )
    echo.
)

pause
