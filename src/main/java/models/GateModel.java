/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;
import javax.sql.DataSource;

public class GateModel {

    public static ArrayList<GateModel> getGateDetails(DataSource ds) throws SQLException {
        String sql = "select * from gate order by zoneid,stationname,gateno";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            ArrayList<GateModel> list = new ArrayList<>();
            while (rs.next()) {
                list.add(new GateModel(rs));
            }
            return list;
        }
    }

    public static ArrayList<GateModel> findAllGateByStnName(DataSource ds, Station stn) throws SQLException {
        String sql = "select * from gate where zoneid=? and stationname=? order by gateno";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, stn.getZoneid());
            pstmt.setString(2, stn.getStationname());
            try (ResultSet rs = pstmt.executeQuery()) {
                ArrayList<GateModel> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new GateModel(rs));
                }
                return list;
            }
        }
    }

    public static GateModel gateDetails(DataSource ds, Integer zoneid,String stationname, String gateno) throws SQLException {
        String sql = "select * from gate where zoneid=? and stationname=? and gateno=?";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, zoneid);
            pstmt.setString(2, stationname);
            pstmt.setString(3, gateno);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? new GateModel(rs) : null;
            }
        }
    }

    public static int insertGateData(DataSource ds, GateModel e) throws SQLException {
        String sql = "insert into gate(zoneid,stationname,gateno)values(?,?,?)";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setInt(1, e.zoneId);
            pstmt.setString(2, e.stnName);
            pstmt.setString(3, e.gateNo);
            int res = pstmt.executeUpdate();
            con.commit();
            return res;
        }
    }

    public static int updateGateData(DataSource ds, GateModel e) throws SQLException {
        String sql = "update gate set zoneid=?,stationname=?,gateno=? where id=?";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setInt(1, e.zoneId);
            pstmt.setString(2, e.stnName);
            pstmt.setString(3, e.gateNo);
            pstmt.setInt(4, e.id);
            int res = pstmt.executeUpdate();
            con.commit();
            return res;
        }
    }

    public static int deleteGate(DataSource ds, GateModel e) throws SQLException {
        String sql = "delete from gate where id=?";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setInt(1, e.id);
            int res = pstmt.executeUpdate();
            con.commit();
            return res;
        }
    }
    
    Integer id;
    Integer zoneId;
    String stnName;
    String gateNo;

    public GateModel(ResultSet rs) throws SQLException {
        this.id=rs.getInt("id");
        this.zoneId = rs.getInt("zoneid");
        this.stnName = rs.getString("stationname");
        this.gateNo = rs.getString("gateno");
    }

    public GateModel() {
    }

    public GateModel(Integer zoneid, String stationname, String gateno) {
        this.zoneId = zoneid;
        this.stnName = stationname;
        this.gateNo = gateno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getZoneid() {
        return zoneId;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneId = zoneid;
    }

    public String getStationname() {
        return stnName;
    }

    public void setStationname(String stationname) {
        this.stnName = stationname;
    }

    public String getGateno() {
        return gateNo;
    }

    public void setGateno(String gateno) {
        this.gateNo = gateno;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.zoneId);
        hash = 97 * hash + Objects.hashCode(this.stnName);
        hash = 97 * hash + Objects.hashCode(this.gateNo);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GateModel other = (GateModel) obj;
        if (!Objects.equals(this.stnName, other.stnName)) {
            return false;
        }
        if (!Objects.equals(this.gateNo, other.gateNo)) {
            return false;
        }
        if (!Objects.equals(this.zoneId, other.zoneId)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Gate{" + "id=" + id + ", zoneid=" + zoneId + ", stationname=" + stnName + ", gateno=" + gateNo + '}';
    }

    
}
