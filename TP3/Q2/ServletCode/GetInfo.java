package ServletCode;

import java.io.PrintWriter;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;


/**
 * servlet used to get user's info through a HTML form
 */
public class GetInfo extends HttpServlet
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
        
        // init session with name; if init failed
        if (!initSession(request))
        {
            html.addln("no name found in request; aborting");
            printHtml();
            // stop the execution
            return;
        }

        addHtmlForm(response);

        printHtml();
    }


    /**
     * creates a new session, or gets the current one
     * @param request the HTTP request to get the name from
     * @return false if the name isn't set in the request, true otherwise
     */
    private boolean initSession(HttpServletRequest request)
    {
        // create a new session or get the current one
        this.session = request.getSession();

        // if name is already set in session
        if (this.session.getAttribute("name") != null)
            // success
            return true;
        
        // get name from request
        String inputName = request.getParameter("inputName");
        // if name exists in request and is not an empty string
        if (inputName != null && !inputName.equals(""))
        {
            // store it in session
            this.session.setAttribute("name", inputName);
            // success
            return true;
        }

        // failed to init session
        return false;
    }


    /**
     * adds form to get user's name
     * if a name has already been set, pre-fill the form with it
     * @param response used to encode URL with session's ID
     */
    private void addHtmlForm(HttpServletResponse response)
    {
        // encode URL by including the session ID; useful if browser doesn't support cookies
        String url = response.encodeURL("./displayinfo");

        // get name from session (null if not set)
        String nameFromSession = (String)this.session.getAttribute("name");
        // get age (null if not set)
        String age = (String)this.session.getAttribute("age");
        // get postal code (null if not set)
        String postalCode = (String)this.session.getAttribute("postalCode");

        html.addln("name: " + nameFromSession);

        this.html.add(String.format(
            // on submit, form will redirect to DisplayInfo servlet
            "<form action=\"%s\" method=\"post\">" +
                "Age:<br>" +
                // inputAge: name of the field containing the user's input
                "<input type=\"text\" name=\"inputAge\" value=\"%s\">" +
                "<br><br>" +
                "Postal code:<br>" +
                // inputPostalCode: name of the field containing the user's input
                "<input type=\"text\" name=\"inputPostalCode\" value=\"%s\">" +
                "<br><br>" +
                "<input type=\"submit\" value=\"Submit\">" +
            "</form>",
            // use encoded URL
            url,
            // if info is set, use it; else, put an empty string
            age != null ? age : "",
            postalCode != null ? postalCode : ""));
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