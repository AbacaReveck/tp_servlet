javac ServletCode/* -d WEB-INF/classes
jar cvf HelloWorld.war WEB-INF/*
asadmin deploy --force HelloWorld.war
