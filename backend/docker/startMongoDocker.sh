#!/bin/sh
sudo docker-compose down
sudo docker-compose up -d
sudo docker-compose stop
sudo docker start mongoSecondLife
