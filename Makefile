run:
	javac -cp . -sourcepath src -d ./out/ ./dontTouchTheWhiteTile/*.java
	java -cp out/ dontTouchTheWhiteTile/DontTouchTheWhiteTile
clean:
	rm ./out/DontTouchTheWhiteTile/*.class
