javac ServletCode/* -d WEB-INF/classes
jar cvf servlet_tp3.war WEB-INF/* index.html dummy_page.jsp
asadmin deploy --force servlet_tp3.war
