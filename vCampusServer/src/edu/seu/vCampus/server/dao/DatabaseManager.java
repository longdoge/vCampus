/**
 * 数据库连接基础管理类
 * @author Wenyu
 * @version 3.0
 */

package edu.seu.vCampus.server.dao;

import java.io.File;
import java.sql.*;
import java.util.Vector;
import java.util.HashMap;

public class DatabaseManager {
    public static String strDbPath = "vCampus.mdb";
    /**
     * @return the strDbPath
     */
    public static String getStrDbPath() {
        return strDbPath;
    }

    /**
     * @param strDbPath the strDbPath to set
     */
    public static void setStrDbPath(String strDbPath) {
        DatabaseManager.strDbPath = strDbPath;
    }

    public static Connection connDatabase = null;

    /**
     * 建立与数据库的连接
     * 
     * @throws SQLException,
     *             ClassNotFoundException
     */
    public static void connectDatabase() throws SQLException, ClassNotFoundException {
        File fDb = new File(strDbPath);
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        String dburl;
        try {
            // 先尝试使用32位连接字串
            dburl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + fDb.getAbsolutePath(); // 32位
            connDatabase = DriverManager.getConnection(dburl);
        } catch (Exception e) {
            // 失败了，再尝试使用64位连接字串
            dburl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + fDb.getAbsolutePath(); // 64位
            connDatabase = DriverManager.getConnection(dburl);
            // 如果又失败了，那就去死吧，直接抛出异常
        }
    }

    /**
     * 关闭与数据库的连接
     * 
     * @throws SQLException
     */
    public static void closeDatabase() throws SQLException {
        if (connDatabase != null)
            connDatabase.close();
    }

    /**
     * 执行查询操作，并返回结果
     * 
     * @param sql
     *            SQL语句
     * 
     * @return 外层Vector为记录集合，内层HashMap为字段集合，最终数据全部为String
     * @throws SQLException
     */
    public static Vector<HashMap<String, String>> queryWithResult(String sql) throws SQLException {
        Vector<HashMap<String, String>> rowData = new Vector<HashMap<String, String>>();
        Statement stt = connDatabase.createStatement();
        ResultSet result = stt.executeQuery(sql); // 获取记录
        ResultSetMetaData rsmd = result.getMetaData(); // 获取字段名称

        while (result.next()) {
            HashMap<String, String> record = new HashMap<String, String>();
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                record.put(rsmd.getColumnName(i), result.getString(i));
            }
            rowData.add(record);
        }

        result.close();
        stt.close();
        return rowData;
    }

    /**
     * 执行查询操作，不返回结果
     * 
     * @param sql
     *            SQL语句
     * 
     * @throws SQLException
     */
    public static void queryWithoutResult(String sql) throws SQLException {
        Statement stt = connDatabase.createStatement();
        stt.executeUpdate(sql);
    }

    /**
     * 执行查询操作，返回新记录的ID
     * 
     * @param sql
     *            SQL语句
     * 
     * @throws SQLException
     */
    public static int queryForId(String sql) throws SQLException {
        Statement stmt = connDatabase.createStatement();
        stmt.execute(sql);
        ResultSet rs = stmt.executeQuery("SELECT @@Identity");
        if (rs.next())
            return rs.getInt(1);
        return 0;
    }
}
