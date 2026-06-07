IMAGE_NAME=k8s-cgroup
CONTAINER_NAME=k8s-cgroup-run
PORT=8080

.PHONY: all build run clean

all: build

build:
	docker build -t $(IMAGE_NAME) .

run: build
	@docker rm -f $(CONTAINER_NAME) >/dev/null 2>&1 || true
	docker run --rm --name $(CONTAINER_NAME) -p $(PORT):$(PORT) $(IMAGE_NAME)

clean:
	docker rmi -f $(IMAGE_NAME) >/dev/null 2>&1 || true
