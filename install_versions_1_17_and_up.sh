#!/usr/bin/env bash
versions=( "1.17.1" "1.18.1" "1.18.2" "1.19.2" "1.19.3" "1.19.4" "1.20.1" )

for version in "${versions[@]}"
do
  java -jar BuildTools.jar --rev "${version}" --remapped
  java -jar BuildTools.jar --rev "${version}" --compile craftbukkit
done
