<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" import="ServletCode.*"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>

<head>
	<title>Dummy page</title>
</head>

<body>
	<%
		// get currentSession without creating it if it doesn't exist
		HttpSession currentSession = request.getSession(false);
		// if session doesn't exist or name is not defined
        if (currentSession == null || currentSession.getAttribute("name") == null)
        {
            // redirect to login page; no need to encode URL since there's no session
            response.sendRedirect(request.getContextPath() + "/getusername");
        }
		
		String url = response.encodeURL("./displayinfo");
	%>


	This page definitely is useless, but at least it lets you go back to <a href="<% out.print(url); %>">the page displaying the information you entered</a>.
</body>

</html>