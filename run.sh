#!/bin/bash

canvasSize="$1"
java -cp lib/*:bin jade.Boot -agents "world:Agents.WorldAgent($canvasSize)"

