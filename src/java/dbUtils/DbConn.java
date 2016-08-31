package dbUtils;

import java.sql.DriverManager;
import java.sql.Connection;

/**
 * Wrapper class for database connection. Constructor opens connection. Close
 * method closes connection.
 */
public class DbConn {

    private String errMsg = ""; // will remain "" unless error getting connection
    private java.sql.Connection conn = null;

    /**
     * Constructor - opens database connection to database, This version
     * determines if the app is running locally or not (by checking if
     * "temple.edu" is at the end of the hostname of the machine on which you
     * are running your app).
     */
    public DbConn() {
        this.connect(this.isTemple());
    } // method

    /**
     * Constructor - opens database connection to database, This version uses
     * boolean input parameter to determine if the app is running locally or not
     */
    public DbConn(boolean isTemple) {
        this.connect(isTemple);
    }

    /**
     * Open a connection to your database either using the Temple connection
     * string or the local connection string.
     *
     * @param isTemple: if this is true, it will use the Temple connection
     * string (else it will use the local connection string).
     */
    private void connect(boolean isTemple) {

        try {
            String DRIVER = "com.mysql.jdbc.Driver";
            Class.forName(DRIVER).newInstance();
            try {
                // Assume you are running from home using tunneling...
                String url = "jdbc:mysql://localhost:3307/SP16_2308_tug25013?user=tug25013&password=eingutha";

                // unless you are working from temple (wachman hall)
                if (isTemple) {
                    url = "jdbc:mysql://cis-linux2.temple.edu:3306/SP16_2308_tug25013?user=tug25013&password=eingutha";
                }
                this.conn = DriverManager.getConnection(url);

            } catch (Exception e) { // cant get the connection
                this.errMsg = "problem getting connection:" + e.getMessage();
            }
        } catch (Exception e) { // cant get the driver...
            this.errMsg = "problem getting driver:" + e.getMessage();
        }
    } // end connect() 

    /* Returns database connection for use in SQL classes.  */
    public Connection getConn() {
        return this.conn;
    }

    /* Returns database connection error message or "" if there is none.  */
    public String getErr() {
        return this.errMsg;
    }

    /**
     * Close database connection.
     */
    public void close() {

        if (conn != null) {
            try {
                conn.close();
            } // try
            catch (Exception e) {
                // Don't care if connection was already closed. Do nothing.
            } // catch
        } // if
    } // method

    /**
     * Checks the hostname to see if app is running at Temple or not.
     */
    private boolean isTemple() {
        boolean temple = false;
        try {
            String hostName = java.net.InetAddress.getLocalHost().getCanonicalHostName();
            hostName = hostName.toLowerCase();
            if (hostName.endsWith("temple.edu")) {
                temple = true;
                System.out.println("************* Running from Temple, so using cis-linux2 for db connection");
            } else {
                System.out.println("************* Not running from Temple, so using local for db connection");
            }
        } catch (Exception e) {
            System.out.println("************* Unable to get hostname. " + e.getMessage());
        }
        return temple;
    }
} // class
