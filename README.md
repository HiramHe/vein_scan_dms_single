### docker mysql
```docker
docker run -d -p 3306:3306 --name mysql3306 -e MYSQL_ROOT_PASSWORD=xxx -v /home/HiramHe/docker/mysql/conf.d/my.cnf:/etc/mysql/conf.d/my.cnf -v /home/HiramHe/docker/mysql/log:/var/log/mysql -v /home/HiramHe/docker/mysql/data:/var/lib/mysql -v /home/HiramHe/docker/mysql/mysql-files:/var/lib/mysql-files mysql:latest
```

### docker redis
```docker
docker run -p 6379:6379 --name redis6379 -v /home/HiramHe/docker/redis/conf/redis.conf:/etc/redis/redis.conf -v /home/HiramHe/docker/redis/data:/data -d redis redis-server /etc/redis/redis.conf
```

pojo设计
db ---> mapper：dao(po+po+...)、po
mapper ---> db：po



