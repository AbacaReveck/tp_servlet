package ServletCode;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 * servlet used to get user's name through a HTML form
 */
public class GetUserName extends HttpServlet
{
    /** writer to output HTML */
    private PrintWriter out;
    /** class handling HTML code to output */
    private HtmlHandler html;

    /**
     * called at servlet initialization
     * initializes the HTML answer
     */
    public void init()
    {
        // init HTML answer with page's title
        this.html = new HtmlHandler();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        process(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        process(request, response);
    }


    /**
     * called by doGet and doPost; contains all the method calls used by the servlet
     * @param request contains request information
     * @param response interface to provide HTTP-specific functionality in sending a response
     * @throws IOException if the writer cannot be accessed
     */
    private void process(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        out = response.getWriter();

        // prepare HTML answer
        this.html.initWithTitle("Get user's name");

        addHtmlForm(request);

        printHtml();
    }


    /**
     * adds form to get user's name
     * if a name has already been set, pre-fill the form with it
     * @param request used to get cookies and check if a name has already been set
     */
    private void addHtmlForm(HttpServletRequest request)
    {
        // get name if set in cookies (null if not set)
        String nameFromCookie = getNameFromCookie(request);

        html.addln("name from cookie: " + nameFromCookie);

        this.html.add(String.format(
            // on submit, form will redirect to EditCookies servlet
            "<form action=\"./editcookies\" method=\"post\">" +
                "First name:<br>" +
                // inputName: name of the field containing the user input
                "<input type=\"text\" name=\"inputName\" value=\"%s\">" +
                "<br><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
            "</form>",
            // if there's a name set in cookies, use it; else, put an empty string
            nameFromCookie != null ? nameFromCookie : ""));
    }


    /**
     * gets user's name from cookies if set; returns null if not
     * @param request the container to get the cookies from
     * @return the user's name if set, null otherwise
     */
    private String getNameFromCookie(HttpServletRequest request)
    {
        // get cookies
        Cookie[] cookies = request.getCookies();

        // if there's at least one cookie set
        if (cookies != null)
            // for each cookie
            for (Cookie c : cookies)
                // if c contains the user's name
                if (c.getName().equals("userName"))
                    // return the name
                    return c.getValue();
        
        return null;
    }


    /**
     * prints the HTML code in the writer
     */
    private void printHtml()
    {
        out.println(this.html.getHtmlAnswer());
    }


    /**
     * called at servlet destruction
     */
    public void destroy()
    {
        out.close();
    }
}