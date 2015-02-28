#!/bin/bash

# build windows installer
izpack/install/bin/compile windows/install.xml -b . -o windows/BrigePoint_Windows.jar

# build linux installer
izpack/install/bin/compile linux/install.xml -b . -o linux/BrigePoint_Linux.jar
