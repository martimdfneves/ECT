# ES_Proj_BOMBardEIRO
https://rubenmenino.github.io/rubenmenino.github.io_ES-projeto-BOMBardEIRO/

## **docker-compose**
1. cd esp11
2. docker compose up -d

## **Start python script**
1. cd Data
2. docker build -t pythonscript .
3. docker image
4. docker run "image id" que lá aparece

## **Run Spring boot app**
1. cd esp11
2. mvn spring-boot:run

## **Start docker**
1. cd react-admin-dashboard
2. docker build -t react-image .
3. docker run -d -p 3001:3000 --name react-app react-image
4. Open http://localhost:3001/
