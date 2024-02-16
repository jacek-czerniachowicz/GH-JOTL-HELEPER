# GH-JOTL-HELEPER
REST API application for cardboard game Gloomhaven Jaws Of The Lion. 
It's allow to create users, rooms and heroes, buying items and managing them, choosing perks and ability cards.

To see documentation run application and go to:
http://localhost:8080/swagger-ui/index.html#/

Before run this application you need to run docker image of MySQL database using this command:
docker run --name GH-JOTL-HELPER -e MYSQL_DATABASE=gh-helper -e MYSQL_ROOT_PASSWORD=toor -p 3306:3306 -d mysql:8.1

