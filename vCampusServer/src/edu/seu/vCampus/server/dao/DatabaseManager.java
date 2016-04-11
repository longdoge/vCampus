/**
 * ���ݿ����ӻ���������
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
     * ���������ݿ������
     * 
     * @throws SQLException,
     *             ClassNotFoundException
     */
    public static void connectDatabase() throws SQLException, ClassNotFoundException {
        File fDb = new File(strDbPath);
        Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
        String dburl;
        try {
            // �ȳ���ʹ��32λ�����ִ�
            dburl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=" + fDb.getAbsolutePath(); // 32λ
            connDatabase = DriverManager.getConnection(dburl);
        } catch (Exception e) {
            // ʧ���ˣ��ٳ���ʹ��64λ�����ִ�
            dburl = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ=" + fDb.getAbsolutePath(); // 64λ
            connDatabase = DriverManager.getConnection(dburl);
            // �����ʧ���ˣ��Ǿ�ȥ���ɣ�ֱ���׳��쳣
        }
    }

    /**
     * �ر������ݿ������
     * 
     * @throws SQLException
     */
    public static void closeDatabase() throws SQLException {
        if (connDatabase != null)
            connDatabase.close();
    }

    /**
     * ִ�в�ѯ�����������ؽ��
     * 
     * @param sql
     *            SQL���
     * 
     * @return ���VectorΪ��¼���ϣ��ڲ�HashMapΪ�ֶμ��ϣ���������ȫ��ΪString
     * @throws SQLException
     */
    public static Vector<HashMap<String, String>> queryWithResult(String sql) throws SQLException {
        Vector<HashMap<String, String>> rowData = new Vector<HashMap<String, String>>();
        Statement stt = connDatabase.createStatement();
        ResultSet result = stt.executeQuery(sql); // ��ȡ��¼
        ResultSetMetaData rsmd = result.getMetaData(); // ��ȡ�ֶ�����

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
     * ִ�в�ѯ�����������ؽ��
     * 
     * @param sql
     *            SQL���
     * 
     * @throws SQLException
     */
    public static void queryWithoutResult(String sql) throws SQLException {
        Statement stt = connDatabase.createStatement();
        stt.executeUpdate(sql);
    }

    /**
     * ִ�в�ѯ�����������¼�¼��ID
     * 
     * @param sql
     *            SQL���
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
