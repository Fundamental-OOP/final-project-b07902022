run:
	javac -cp . -sourcepath src -d ./out/ ./src/*.java
	java -cp out/ dontTouchTheWhiteTile/DontTouchTheWhiteTile
clean:
	rm ./out/DontTouchTheWhiteTile/*.class
