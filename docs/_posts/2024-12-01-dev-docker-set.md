
# Docker Setting

## Postgres 

``` bash

docker run -p 5432:5432 --name postgres-db \
-e POSTGRES_PASSWORD=password \
-e TZ=Asia/Seoul \
-v /Users/code/Docker/postgresql/pgdata:/var/lib/postgresql/data -d \
postgres:latest
```

## Redis
    
```bash

docker run -p 6379:6379 --name redis-db -d redis
```
