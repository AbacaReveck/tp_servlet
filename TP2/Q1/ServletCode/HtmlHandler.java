package ServletCode;

/**
 * class containing methods used to generate basic HTML code server-side
 */
class HtmlHandler
{
    /** the string containing the HTML code */
    private String htmlAnswer;
    /** true when ending tags have been added */
    private boolean isClosed = false;


    /**
     * default constructor
     */
    HtmlHandler()
    {
    }


    /**
     * prepares the HTML answer with initial tags and header
     * @param title the title of the page
     */
    void init(String title)
    {
        // init HTML answer
        this.htmlAnswer = "<!DOCTYPE html><html>";
        // add header with title
        addHtmlHeader(title);
        // open HTML body
        this.htmlAnswer += "<body>";
    }


    /**
     * adds the <head> tag with the given title
     * @param title the title of the page
     */
    void addHtmlHeader(String title)
    {
        // open the head tag
        this.htmlAnswer += "<head>";
        // add title
        this.htmlAnswer += String.format("<title>%s</title>", title);
        // close header
        this.htmlAnswer += "</head>";
    }


    /**
     * adds given string to current HTML
     * @param stringToAdd the string to add
     */
    void add(String stringToAdd)
    {
        this.htmlAnswer += stringToAdd;
    }


    /**
     * adds given string and a line break tag to current HTML
     * @param stringToAdd the string to add
     */
    void addln(String stringToAdd)
    {
        this.htmlAnswer += stringToAdd + "<br>";
    }


    /**
     * adds the closing HTML tags
     */
    private void closeHtml()
    {
        // end HTML code
        this.add("</body></html>");
        // set isClosed to true so we know the code has been closed
        this.isClosed = true;
    }


    /**
     * adds the closing HTML tags if needed, then return the code
     * @return the HTML code
     */
    String getHtmlAnswer()
    {
        if (!isClosed)
            closeHtml();

        return this.htmlAnswer;
    }


    boolean isClosed()
    {
        return this.isClosed;
    }
}
