for port in `seq 7001 7006` ; do \
	mkdir -p . redis-cluster/${port}/conf \
	&& PORT=${port} envsubst < redis-cluster.template > redis-cluster/${port}/conf/redis.conf \
	&& mkdir -p ./redis-cluster/${port}/data; \
done

