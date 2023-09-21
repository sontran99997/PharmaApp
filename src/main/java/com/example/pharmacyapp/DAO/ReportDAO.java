package com.example.pharmacyapp.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportDAO {
    private static Connection connection =ConnectDB.getInstance();
    public static ResultSet getDSReport(String date1 , String date2) throws SQLException {
        ResultSet rs;
        String sql = "exec dbo.Report_DoanhThu '"+date1+"', '"+date2+"'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getExportReport(String date1 , String date2) throws SQLException {
        ResultSet rs;
        String sql = "exec PhieuNhapHang_ChiTiet_TheoNgay '"+date1+"' , '"+date2+"'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getImportReport(String date1 , String date2) throws SQLException {
        ResultSet rs;
        String sql = "exec PhieuXuatHang_ChiTiet_TheoNgay '"+date1+"' , '"+date2+"'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }

    public static ResultSet getDailyExportReport(String date , int shift) throws SQLException {
        ResultSet rs;
        String sql = "exec PhieuNhapHang_ChiTiet_TrongNgay '"+date+"', "+shift+"";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
    public static ResultSet getDailyImportReport(String date , int shift) throws SQLException {
        ResultSet rs;
        String sql = "exec PhieuXuatHang_ChiTiet_TrongNgay '"+date+"', "+shift+"";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }

    public static ResultSet getAllDailyExportReport(String date) throws SQLException {
        ResultSet rs;
        String sql = "exec PhieuNhapHang_ChiTiet_CaNgay '"+date+"'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }

    public static ResultSet getAllDailyImportReport(String date) throws SQLException {
        ResultSet rs;
        String sql = "exec PhieuXuatHang_ChiTiet_CaNgay '"+date+"'";
        Statement statement =connection.createStatement();
        rs = statement.executeQuery(sql);
        return rs;
    }
}
