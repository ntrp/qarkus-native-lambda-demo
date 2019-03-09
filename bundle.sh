#!/usr/bin/env bash

mvn clean package -Pnative

mkdir -p target/bundle

cp target/runtime-runner target/bundle/
cp package/bootstrap target/bundle/

zip -j target/bundle/bundle.zip target/bundle/bootstrap target/bundle/runtime-runner
