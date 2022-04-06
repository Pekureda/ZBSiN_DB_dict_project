package put.projdbdict.dbdictproj;

import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;
import java.util.Properties;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.System.exit;


public class DBUtil {
    public static DBUtil obj;

    private static Connection conn = null;
    private static String connectionString = "server address";
    private static Properties connectionProps = new Properties();

    public DBUtil() {
        connectionProps.put("user", "username");
        connectionProps.put("password", "passwd");
    }

    public DBUtil(String username, String password){
        connectionProps.put("user", username);
        connectionProps.put("password", password);
    }

    public static void connect() {
        try {
            conn = DriverManager.getConnection(connectionString, connectionProps);
            conn.setAutoCommit(true);
            System.out.println("Connected to the database");
        } catch (SQLException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.SEVERE, "Could not establish a connection to the database", e);
            exit(-1);
        }
    }

    public static void close(){
        try {
            conn.close();
            System.out.println("Connection closed");
        } catch (SQLException e) {
            Logger.getLogger(DBUtil.class.getName()).log(Level.INFO, "Could not disconnect from the database", e);
        }
    }

    public static CallableStatement prepareCallFunction(String functionName, int argnum) throws SQLException {
        String callStr = "{? = call " + functionName + "(";
        for (int i = 0; i < argnum; ++i) {
            callStr += "?";
            if (i < argnum-1) {
                callStr += ", ";
            }
        }
        callStr += ")}";

        CallableStatement stmt = conn.prepareCall(callStr);
        return stmt;
    }

    public static CallableStatement prepareCallProcedure(String procedureName, int argnum) {
        String callStr = "{call " + procedureName + "(";
        for (int i = 0; i < argnum; ++i) {
            callStr += "?";
            if (i < argnum-1) {
                callStr += ", ";
            }
        }
        callStr += ")}";
        CallableStatement stmt = null;
        try {
            stmt = conn.prepareCall(callStr);
        } catch (SQLException e) {
            System.out.println("Procedure does not exist.");
            e.printStackTrace();
            exit(-1);
        }
        //stmt.setEscapeProcessing(false);
        return stmt;
    }

    public static Connection getConn(){
        return conn;
    }

    public static ResultSet executeQuery(String query) throws SQLException {
        Statement stmt = null;
        ResultSet rs = null;
        CachedRowSet crs = null;

        System.out.println("Select: " + query);

        stmt = conn.createStatement();
        rs = stmt.executeQuery(query);

        crs = RowSetProvider.newFactory().createCachedRowSet();
        crs.populate(rs);

        if (rs != null){
            rs.close();
        }
        if (stmt != null) {
            stmt.close();
        }
        return crs;
    }
}
