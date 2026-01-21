.PHONY: build kubedel run re

IMAGE_NAME ?= snowflakes
TAG ?= 1.5
JAR_FILE ?= target/app.jar

build:
	docker rmi $(IMAGE_NAME):$(TAG) || true
	docker build -t $(IMAGE_NAME):$(TAG) \
		--build-arg JAR_FILE=$(JAR_FILE) .

kubedel:
	kubectl delete -f postgres-configmap.yml --ignore-not-found
	kubectl delete -f postgres-pv.yml --ignore-not-found
	kubectl delete -f postgres-pvc.yml --ignore-not-found
	kubectl delete -f postgres-secrets.yml --ignore-not-found
	kubectl delete -f postgres-service.yml --ignore-not-found
	kubectl delete -f postgres-service-sts.yml --ignore-not-found
	kubectl delete -f postgres-sts.yml --ignore-not-found
	kubectl delete -f kuard-deployment.yml --ignore-not-found
	kubectl delete -f kuard-hpa.yml --ignore-not-found
	kubectl delete -f kuard-ingress.yml --ignore-not-found
	kubectl delete -f kuard-pod.yml --ignore-not-found
	kubectl delete -f kuard-service.yml --ignore-not-found
	kubectl delete -f kuard-tls-secret.yml --ignore-not-found
	kubectl delete -f sf-kuard-config.yml --ignore-not-found
	kubectl delete -f snowflakes-deployment.yml --ignore-not-found
	kubectl delete -f snowflakes-ingress.yml --ignore-not-found
	kubectl delete -f snowflakes-pod.yml --ignore-not-found
	kubectl delete -f snowflakes-service.yml --ignore-not-found
	kubectl delete -f snowflakes-tls-secret.yml --ignore-not-found
	kubectl delete -f kuard-service.yml --ignore-not-found
	kubectl delete -f fluentd-config.yml --ignore-not-found
	kubectl delete -f fluentd-ds.yml --ignore-not-found

run:
#kuard
	kubectl apply -f postgres-configmap.yml
	kubectl apply -f postgres-secrets.yml
	kubectl apply -f postgres-service-sts.yml
	kubectl apply -f postgres-sts.yml
	kubectl apply -f kuard-deployment.yml
	kubectl apply -f kuard-service.yml
#sf
	kubectl apply -f snowflakes-deployment.yml
	kubectl apply -f snowflakes-service.yml
	kubectl apply -f snowflakes-tls-secret.yml
	kubectl apply -f kuard-service.yml
#fluentd
	kubectl apply -f fluentd-config.yml
	kubectl apply -f fluentd-ds.yml

re: kubedel build run
