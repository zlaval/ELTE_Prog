dir /s /b *.java > sources.txt
javac @sources.txt
java GeometryMain input.txt

pause