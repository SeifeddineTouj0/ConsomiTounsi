To run prometheus:

docker run -d -p 9090:9090 prom/prometheus

docker exec -it <container-id> /bin/sh

cd /etc/prometheus

echo "content-of-prom.yml">prometheus.yml

Then restart the prometheus container
