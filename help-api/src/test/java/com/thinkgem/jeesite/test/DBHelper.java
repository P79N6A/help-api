package com.thinkgem.jeesite.test;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author mrhuang
 * @Date 2016/12/20 0020 15:44
 */
public class DBHelper {
    Connection _CONN = null;

    //取得连接
    private boolean GetConn(String sUser, String sPwd) {
        if (_CONN != null) return true;
        try {
            String sDriverName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
            String sDBUrl = "jdbc:sqlserver://192.168.1.103:1433;databaseName=1p1g";

            Class.forName(sDriverName);
            _CONN = DriverManager.getConnection(sDBUrl, sUser, sPwd);

        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
            return false;
        }
        return true;
    }

    private boolean GetConn() {
        return GetConn("wcf", "wcf123456");
    }

    //关闭连接
    private void CloseConn() {
        try {
            _CONN.close();
            _CONN = null;
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            _CONN = null;
        }
    }


    //测试连接
    public boolean TestConn() {
        if (!GetConn())
            return false;

        CloseConn();
        return true;
    }

    public ResultSet GetResultSet(String sSQL, Object[] objParams) {
        GetConn();
        ResultSet rs = null;
        try {
            PreparedStatement ps = _CONN.prepareStatement(sSQL);
            if (objParams != null) {
                for (int i = 0; i < objParams.length; i++) {
                    ps.setObject(i + 1, objParams[i]);
                }
            }
            rs = ps.executeQuery();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            CloseConn();
        } finally {
            //CloseConn();
        }
        return rs;
    }

    public Object GetSingle(String sSQL, Object... objParams) {
        GetConn();
        try {
            PreparedStatement ps = _CONN.prepareStatement(sSQL);
            if (objParams != null) {
                for (int i = 0; i < objParams.length; i++) {
                    ps.setObject(i + 1, objParams[i]);
                }
            }
            ResultSet rs = ps.executeQuery();
            if (rs.next())
                return rs.getString(1);//索引从1开始
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            CloseConn();
        }
        return null;
    }

    public DataTable GetDataTable(String sSQL, Object... objParams) {
        GetConn();
        DataTable dt = null;
        try {
            PreparedStatement ps = _CONN.prepareStatement(sSQL);
            if (objParams != null) {
                for (int i = 0; i < objParams.length; i++) {
                    ps.setObject(i + 1, objParams[i]);
                }
            }
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();

            List<DataRow> row = new ArrayList<DataRow>(); //表所有行集合
            List<DataColumn> col = null; //行所有列集合
            DataRow r = null;// 单独一行
            DataColumn c = null;//单独一列

            String columnName;
            Object value;
            int iRowCount = 0;
            while (rs.next())//开始循环读取，每次往表中插入一行记录
            {
                iRowCount++;
                col = new ArrayList<DataColumn>();//初始化列集合
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    columnName = rsmd.getColumnName(i);
                    value = rs.getObject(columnName);
                    c = new DataColumn(columnName, value);//初始化单元列
                    col.add(c); //将列信息加入到列集合
                }
                r = new DataRow(col);//初始化单元行
                row.add(r);//将行信息加入到行集合
            }
            dt = new DataTable(row);
            dt.RowCount = iRowCount;
            dt.ColumnCount = rsmd.getColumnCount();
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println(ex.getMessage());
        } finally {
            CloseConn();
        }
        return dt;
    }

    public int UpdateData(String sSQL, Object[] objParams) {
        GetConn();
        int iResult = 0;

        try {
            PreparedStatement ps = _CONN.prepareStatement(sSQL);
            if (objParams != null) {
                for (int i = 0; i < objParams.length; i++) {
                    ps.setObject(i + 1, objParams[i]);
                }
            }
            iResult = ps.executeUpdate(sSQL);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return -1;
        } finally {
            CloseConn();
        }
        return iResult;
    }


}
