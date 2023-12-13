## Docker创建Redis集群以及测试

### 前置条件

1. 安装 docker-component
2. 下载 redis 镜像 `docker pull redis:latest`

### 初始化配置

#### 1. 编写redis-cluster.template模板

```text
    port ${PORT}
    protected-mode no
    cluster-enabled yes
    cluster-config-file nodes.conf
    cluster-node-timeout 5000
    cluster-announce-ip 192.168.124.5
    cluster-announce-port ${PORT}
    cluster-announce-bus-port 1${PORT}
    appendonly yes
    appendfsync everysec
    no-appendfsync-on-rewrite no
    auto-aof-rewrite-percentage 100
    auto-aof-rewrite-min-size 64mb
```

#### 2. 创建本地conf文件

```jshelllanguage
    for port in `seq 7001 7006` ; do \
        mkdir -p . redis-cluster/${port}/conf \
        && PORT=${port} envsubst < redis-cluster.template > redis-cluster/${port}/conf/redis.conf \
        && mkdir -p ./redis-cluster/${port}/data; \
    done
```

#### 3. 编写redis-cluster.yml

```yaml
version : '3.7'

services:
  redis7001:
    image: 'redis'
    container_name: redis7001
    command:
      ["redis-server", "/usr/local/etc/redis/redis.conf"]
    volumes:
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7001/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7001/data:/data
    ports:
      - "7001:7001"
      - "17001:17001"
    environment:
      - TZ=Asia/Shanghai


  redis7002:
    image: 'redis'
    container_name: redis7002
    command:
      ["redis-server", "/usr/local/etc/redis/redis.conf"]
    volumes:
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7002/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7002/data:/data
    ports:
      - "7002:7002"
      - "17002:17002"
    environment:
      - TZ=Asia/Shanghai


  redis7003:
    image: 'redis'
    container_name: redis7003
    command:
      ["redis-server", "/usr/local/etc/redis/redis.conf"]
    volumes:
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7003/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7003/data:/data
    ports:
      - "7003:7003"
      - "17003:17003"
    environment:
      - TZ=Asia/Shanghai


  redis7004:
    image: 'redis'
    container_name: redis7004
    command:
      ["redis-server", "/usr/local/etc/redis/redis.conf"]
    volumes:
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7004/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7004/data:/data
    ports:
      - "7004:7004"
      - "17004:17004"
    environment:
      - TZ=Asia/Shanghai


  redis7005:
    image: 'redis'
    container_name: redis7005
    command:
      ["redis-server", "/usr/local/etc/redis/redis.conf"]
    volumes:
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7005/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7005/data:/data
    ports:
      - "7005:7005"
      - "17005:17005"
    environment:
      - TZ=Asia/Shanghai


  redis7006:
    image: 'redis'
    container_name: redis7006
    command:
      ["redis-server", "/usr/local/etc/redis/redis.conf"]
    volumes:
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7006/conf/redis.conf:/usr/local/etc/redis/redis.conf
      - /F/ideaProject/metaapp-materialplatform/redis-cluster/redis-cluster/7006/data:/data
    ports:
      - "7006:7006"
      /**总线端口*/
      - "17006:17006" 
    environment:
      /**设置时区*/
      - TZ=Asia/Shanghai
```

#### 4. docker-compose 执行

```jshelllanguage
docker-compose -f redis-cluster.yml up -d
```

#### 5. 启动redis集群
```jshelllanguage
 docker exec -it redis7001 redis-cli -p 7001 --cluster create 192.168.28.1:7001 192.168.28.1:7002 192.168.28.1:7003 192.168.28.1:7004 192.168.28.1:7005 192.168.28.1:7006 --cluster-replicas 1
```

### 测试
```jshelllanguage
    docker exec -it redis7001 redis-cli -h 192.168.28.1 -p 7005 ping
    docker exec -it redis7001 redis-cli -h 192.168.28.1 -p 7005 -c 集群启动需要添加-c
```
