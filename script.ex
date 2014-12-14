#!/bin/sh

jar -xf Demo.jar

mv Demo.jar DemoOld.jar

rm -r code
rm -r docs

javac -d . source/*.java

javadoc -d docs -version -author -private -quiet source/*.java

jar -cvfm Demo.jar m.txt *

java -jar Demo.jar

appletviewer Demoj.html