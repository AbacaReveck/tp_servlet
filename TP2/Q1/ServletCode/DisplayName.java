package ServletCode;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;


/**
 * servlet used to display the name input by user (or a default name if user didn't input any)
 */
public class DisplayName extends HttpServlet
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
        // init HTML answer
        this.html = new HtmlHandler();
    }


    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        this.process(request, response);
    }


    public void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException
    {
        this.process(request, response);
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
        this.html.init("Display name");

        addLinkBack();

        setName(request);

        printName();

        printServletName();

        sendAnswer();
    }


    /**
     * adds a link to the GetUserName servlet
     */
    private void addLinkBack()
    {
        this.html.addln("<a href=\"./getusername\">Go back to form</a>");
    }


    /**
     * set name in servlet attributes
     * @param request contains the name
     */
    private void setName(HttpServletRequest request)
    {
        if (request.getParameterMap().containsKey("inputName"))
        {
            // get user's input
            String inputName = request.getParameter("inputName");

            // if user input a name
            if (!inputName.equals(""))
                // set name in servlet
                getServletContext().setAttribute("userName", inputName);
            else
                // remove a previously input name
                getServletContext().removeAttribute("userName");
        }
    }


    /**
     * prints the user-defined name if there's one, or the default one otherwise
     */
    private void printName()
    {
        // get name to print
        String name = (String)getServletContext().getAttribute("userName");

        html.add("Name: ");

        // if name has been set
        if (name != null)
            // print it
            html.addln(String.format("%s (set by user)", name));
        else
            // print default name (set in XML)
            html.addln(getServletConfig().getInitParameter("defaultName") + " (default name)");
    }


    private void printServletName()
    {
        html.addln(String.format("Servlet name: %s\n", getServletName()));
    }


    /**
     * prints the HTML code in the writer
     */
    private void sendAnswer()
    {
        out.println(this.html.getHtmlAnswer());
    }
}