# Introduction
windows要先安裝Docker-toolbox <br/>
https://download.docker.com/win/stable/DockerToolbox.exe<br/>
org.jolokia是io.fabric8的其中一個分支，已經沒有在維護，使用方法大致相同
mvn docker:help <br/>
docker-machine rm default <br/>
docker-machine create  --virtualbox-memory 4096 default <br/>
eval $( docker-machine env  default ) <br/>
mvn clean package docker:build <br/>
mvn docker:start <br/>