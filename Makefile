run:
	javac -cp . -sourcepath src -d ./out/ ./src/*.java
	java -cp out/ src/DontTouchTheWhiteTile
clean:
	rm ./out/src/*.class
