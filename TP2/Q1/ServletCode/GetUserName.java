package ServletCode;

import java.io.*;
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
        out = response.getWriter();

        // prepare HTML answer
        this.html.init("Get user's name");

        addHtmlForm();

        sendAnswer();
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        out = response.getWriter();

        // prepare HTML answer
        this.html.init("Get user's name");

        addHtmlForm();

        sendAnswer();
    }


    /**
     * adds form to get user's name
     */
    private void addHtmlForm()
    {
        this.html.add(
            // on submit, form will redirect to displayname servlet
            "<form action=\"./displayname\" method=\"post\">" +
                "First name:<br>" +
                // inputName: name of the field containing the user input
                "<input type=\"text\" name=\"inputName\" value=\"test\">" +
                "<br><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
            "</form>");
    }


    /**
     * prints the HTML code in the writer
     */
    private void sendAnswer()
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