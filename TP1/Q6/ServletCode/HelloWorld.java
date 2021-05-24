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
        
        printAllNames(request);
    }


    /**
     * accessible through the HTML form: http://localhost:8080/HelloWorld
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        out = response.getWriter();

        printAllNames(request);
    }


    /**
     * prints the given name and the servlet name.
     * @param request the container with all request information and parameters
     */
    private void printAllNames(HttpServletRequest request)
    {
        printName(request);
        printServletName();
    }


    /**
     * prints the given name (or "hello world" if there's no such parameter)
     * and the HTTP method
     * @param request the container with all request information and parameters
     */
    private void printName(HttpServletRequest request)
    {
        if (request.getParameterMap().containsKey("name"))
            out.println(String.format("Hello %s\n", request.getParameter("name")));
        else
            out.println("Hello world\n");
        
        out.println(String.format("HTTP method: %s\n", request.getMethod()));
    }


    /**
     * get the current servlet's name and print it
     */
    private void printServletName()
    {
        out.println(String.format("Servlet name: %s\n", getServletName()));
    }


    /**
     * called at servlet destruction
     */
    public void destroy()
    {
        out.close();
    }
}