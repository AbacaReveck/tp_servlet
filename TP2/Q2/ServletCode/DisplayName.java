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
        this.html.addln("get");
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
        this.html.initWithTitle("Display name");

        addLinkBack();

        setName(request);

        printName();

        printServletName();

        addLinkRefresh(request);

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
     * sets name in servlet attributes
     * @param request contains the name
     */
    private void setName(HttpServletRequest request)
    {
        // if name exists in request
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
     * adds a link offering to refresh the current page
     * NOTE: using a form is not mandatory here, since we already stored the name
     * in the servlet's context. This implementation is purely for the sake of the
     * exercise.
     * @param request used to get the input parameter (i.e the name)
     */
    private void addLinkRefresh(HttpServletRequest request)
    {
        // get name from request
        String name = request.getParameter("inputName");

        this.html.add(String.format(
            // on submit, form will redirect to displayname servlet
            "<form action=\"./displayname\" method=\"post\">" +
                // inputName: name of the field containing the user input
                "<input type=\"hidden\" name=\"inputName\" value=\"%s\">" +
                "<input type=\"submit\" value=\"Refresh\">" +
            "</form>",
            // if name is not set, empty string; else, name
            name == null ? "" : name ));
    }


    /**
     * prints the HTML code in the writer
     */
    private void sendAnswer()
    {
        out.println(this.html.getHtmlAnswer());
    }
}