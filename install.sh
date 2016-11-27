#!/bin/bash

mkdir -p lib
cd lib

LIB_REPOSITORY="http://www.stud.fit.vutbr.cz/~xmlyna06/SIN"

wget -N $LIB_REPOSITORY/jade.jar
wget -N $LIB_REPOSITORY/forms_rt-7.0.3.jar
wget -N $LIB_REPOSITORY/commons-lang3-3.3.2.jar

cd ..
