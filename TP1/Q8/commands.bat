javac ServletCode/* -d WEB-INF/classes
jar cvf HelloWorld.war WEB-INF/* index.html setDefaultName.html
asadmin deploy --force HelloWorld.war
