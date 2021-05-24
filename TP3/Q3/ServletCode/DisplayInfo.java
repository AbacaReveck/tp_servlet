package ServletCode;

import java.io.PrintWriter;
import java.util.Enumeration;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 * servlet used to display user's information stored in session
 */
public class DisplayInfo extends HttpServlet
{
    /** writer to output HTML */
    private PrintWriter out;
    /** class handling HTML code to output */
    private HtmlHandler html;
    /** session used to store and retrieve information */
    private HttpSession session;


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

        // init session with name; if init failed
        if (!initSession(request, response))
        {
            printHtml();
            // stop the execution
            return;
        }

        addLinkBack(response);

        printInfo();

        printDummyPage(response);

        printEndSession(response);

        printHtml();
    }


    // TODO: vérifier si session contient nom plutôt que de check son existence ?

    /**
     * creates a new session, or gets the current one
     * @param request the HTTP request to get the name from
     * @return false if information is missing, true otherwise
     */
    private boolean initSession(HttpServletRequest request, HttpServletResponse response)
        throws IOException
    {
        // get the current session if it exists
        this.session = request.getSession(false);

        // if session doesn't exist or name is not defined
        if (this.session == null || this.session.getAttribute("name") == null)
        {
            // redirect to login page; no need to encode URL since there's no session
            response.sendRedirect(request.getContextPath() + "/getusername");
            // fail
            return false;
        }

        // if info is set in request
        if (request.getParameter("inputAge") != null &&
        request.getParameter("inputPostalCode") != null)
        {
            // update session's attributes
            this.session.setAttribute("age", request.getParameter("inputAge"));
            this.session.setAttribute("postalCode", request.getParameter("inputPostalCode"));
        }

        // success
        return true;
    }


    /**
     * adds a link to the GetInfo servlet
     */
    private void addLinkBack(HttpServletResponse response)
    {
        // encode URL by including the session ID; useful if browser doesn't support cookies
        String url = response.encodeURL("./getinfo");

        this.html.addln("<a href=\"" + url + "\">Edit information</a>");
    }


    /**
     * prints the user-defined name if there's one, or the default one otherwise
     */
    private void printInfo()
    {
        // for each attribute to display
        for (Enumeration<String> info = this.session.getAttributeNames(); info.hasMoreElements();)
        {
            // get the name of the attribute do display
            String attribute = info.nextElement();
            // display the attribute's name and its value
            html.addln(attribute + ": " + this.session.getAttribute(attribute));
        }
    }


    /**
     * prints the link to the dummy page
     */
    private void printDummyPage(HttpServletResponse response)
    {
        // encode URL by including the session ID; useful if browser doesn't support cookies
        String url = response.encodeURL("./dummy_page.jsp");

        this.html.addln("<a href=\"" + url + "\">Go to the dummy page</a>");
    }


    /**
     * prints button used to end the session;
     * this button redirects to the GetUserName servlet,
     * which invalidates any existing session
     */
    private void printEndSession(HttpServletResponse response)
    {
        this.html.addln(
            // on submit, form will redirect to DisplayInfo servlet
            "<form action=\"./getusername\" method=\"post\">" +
                "<input type=\"submit\" value=\"End session\">" +
            "</form>");
    }


    /**
     * prints the HTML code in the writer
     */
    private void printHtml()
    {
        out.println(this.html.getHtmlAnswer());
    }
}