package ServletCode;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 * servlet used to get user's name through a HTML form;
 * invalidates any existing session
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

        // invalidate session if there's one
        request.getSession().invalidate();

        addHtmlForm();

        printHtml();
    }


    /**
     * adds form to get user's name
     */
    private void addHtmlForm()
    {
        this.html.add(
            // on submit, form will redirect to GetInfo servlet
            "<form action=\"./getinfo\" method=\"post\">" +
                "First name:<br>" +
                // inputName: name of the field containing the user input
                "<input type=\"text\" name=\"inputName\">" +
                "<br><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
            "</form>");
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