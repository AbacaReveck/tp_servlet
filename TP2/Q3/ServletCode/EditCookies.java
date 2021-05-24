package ServletCode;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

public class EditCookies extends HttpServlet
{
    public void init()
    {
    }


    /**
     * this servlet shouldn't be accessed through get, so we're printing a simple error page
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        HtmlHandler html = new HtmlHandler();
        PrintWriter out = response.getWriter();

        html.initWithTitle("error");
        html.addln("shouldn't be able to access this servlet through get request");
        out.println(html.getHtmlAnswer());
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        // set input name in the cookies (if there's one)
        setCookieName(request, response);

        // add a redirection to the servlet dedicated to the display
        response.sendRedirect(request.getContextPath() + "/displayname");
    }


    /**
     * sets name in a cookie; destroys the cookie if no name has been submitted
     * @param request contains the name
     * @param response contains the cookie
     */
    private void setCookieName(HttpServletRequest request, HttpServletResponse response)
    {
        // if name exists in request
        if (request.getParameterMap().containsKey("inputName"))
        {
            // get user's input
            String inputName = request.getParameter("inputName");

            // if user input a name
            if (!inputName.equals(""))
                // store it in a cookie
                response.addCookie(new Cookie("userName", inputName));

            else
                // remove cookies
                resetCookie(response);
        }

        /* 
         * if name doesn't exist in request, then the user didn't access this
         * servlet through GetUserName, thus couldn't explicitly ask for no name;
         * that's why we don't destroy the cookies here
         */
    }


    /**
     * resets the cookie containing the user's name by adding a new cookie
     * with the same name and a lifetime of 0 seconds, which will replace the
     * old cookie
     * @param response the response containing the new cookie to send to the client
     */
    private void resetCookie(HttpServletResponse response)
    {
        // create a cookie with the same name and an empty string as value
        Cookie c = new Cookie("userName", "");
        // set its duration to 0 seconds
        c.setMaxAge(0);
        // add it to the response to send to the client
        response.addCookie(c);
    }
    
    public void destroy()
    {
    }
}