javac ServletCode/* -d WEB-INF/classes
jar cvf servlet_tp2.war WEB-INF/* index.html
asadmin deploy --force servlet_tp2.war
