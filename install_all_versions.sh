#!/usr/bin/env bash
versions=( "1.8" "1.8.3" "1.8.8" "1.9.2" "1.9.4" "1.10.2" "1.11.2" "1.12.2" "1.13" "1.13.1" "1.14.1" "1.15" "1.16" )

for version in ${versions[@]}
do
  java -jar BuildTools.jar --rev ${version}
done
