package ServletCode;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * test it by setting a name at http://localhost:8080/HelloWorld/setDefaultName.html,
 * then go to http://localhost:8080/HelloWorld/helloworld to see the result
 */
public class SetDefaultName extends HttpServlet
{
    private PrintWriter out;


    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        out = response.getWriter();

        out.println("Get");

        setDefaultName(request);

        printNames();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        out = response.getWriter();

        out.println("Post");

        setDefaultName(request);

        printNames();
    }


    private void setDefaultName(HttpServletRequest request)
    {
        if (request.getParameterMap().containsKey("defaultNameUser"))
        {
            getServletContext().setAttribute(
                "defaultNameUser", request.getParameter("defaultNameUser"));
        }
    }


    /**
     * prints the user-defined default name and the servlet's name
     */
    private void printNames()
    {
        out.println(String.format("Default name (set by user): %s",
                                    getServletContext().getAttribute("defaultNameUser")));

        // print servlet name
        out.println(String.format("Servlet name: %s\n", getServletName()));
    }
}