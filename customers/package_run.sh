#!/bin/sh
echo "jlink"
mvn clean javafx:jlink
echo "jpackage"
jpackage \
  --input target \
  --name customers \
  --module com.owino/com.owino.App \
  --runtime-image target/image \
  --type app-image
  echo "done"
    #--main-jar customers.jar \

