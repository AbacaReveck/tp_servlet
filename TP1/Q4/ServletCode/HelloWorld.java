package ServletCode;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


public class HelloWorld extends HttpServlet
{
    private PrintWriter out;

    /**
     * called at servlet initialization; can be deleted
     */
    public void init()
    {
    }


    /**
     * accessible through http://localhost:8080/HelloWorld/helloworld?name=test
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        out = response.getWriter();
        
        if (request.getParameterMap().containsKey("name"))
            out.println("Hello " + request.getParameter("name") + " (get)");
        else
            out.println("Hello world (get)");
    }

    /**
     * called at servlet destruction
     */
    public void destroy()
    {
        out.close();
    }
}