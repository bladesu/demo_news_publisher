
This project is a demonstration for common scalable framework in java-based backend project.

# Purpose

- Build a backend service with scalable feature to provide news broadcasting to client.

# How to reach the target

Here I implement two decoupled services to do that.

- Scheduled job with distributed lock (On redis) to fetching news content from third party provider, and publish the news content to message queue (kafka broker).
- Websocket service provider consume read news from message queue and sending the message to clients.

Above services can be easily scaled out.

# Run the project
- This project has a prepared docker compose script, which is partly leveraged from confluent official website. Please run docker compose to activate the needed dependency service: kafka, zookeeper, redis.

```
docker-compose up -d
```

If you want to close the services clearly

```
docker-compose down
```


Run the java instance: please use java 18, this project had run on the following jvm version:
```dtd
openjdk version "18.0.1" 2022-04-19
OpenJDK Runtime Environment Zulu18.30+11-CA (build 18.0.1+10)
OpenJDK 64-Bit Server VM Zulu18.30+11-CA (build 18.0.1+10, mixed mode, sharing)
```
Run by gradle wrapper
```dtd
./gradlew bootRun
```

After running this broadcaster successfully, you can easily check the function by open following website or other tool to connect websocket service.
```
http://coolaf.com/tool/chattest
```
Enter the websocket path served by local instance.
```
ws://localhost:8080/broadcasting
```
The news should be shown continuously in the console view.