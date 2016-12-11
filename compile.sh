#!/bin/bash

OUT_DIRECTORY="bin"


mkdir -p $OUT_DIRECTORY

LIBS="lib/jade.jar:lib/commons-lang3-3.3.2.jar:lib/forms_rt-7.0.3.jar"
COMPILE="javac -sourcepath src -d $OUT_DIRECTORY -encoding UTF-8 -classpath $LIBS"


$COMPILE $(find ./src -name "*.java")
