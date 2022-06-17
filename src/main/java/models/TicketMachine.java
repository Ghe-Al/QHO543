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

public class TicketMachine {

    public static ArrayList<TicketMachine> getTktMachinesDetails(DataSource ds) throws SQLException {
        String sql = "select * from ticketmachine order by machineid";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {
            ArrayList<TicketMachine> list = new ArrayList();
            while (rs.next()) {
                list.add(new TicketMachine(rs));
            }
            return list;
        }
    }

    public static ArrayList<TicketMachine> findAllTktMachineByGate(DataSource datasource, GateModel gate) throws SQLException {
        String sql = "select * from ticketmachine where zoneid=? and stationname=? and gateno=? order by machineid";
        try (Connection con = datasource.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, gate.getZoneid());
            pstmt.setString(2, gate.getStationname());
            pstmt.setString(3, gate.getGateno());
            try (ResultSet rs = pstmt.executeQuery()) {
                ArrayList<TicketMachine> list = new ArrayList<>();
                while (rs.next()) {
                    list.add(new TicketMachine(rs));
                }
                return list;
            }
        }
    }

    public static TicketMachine getTicketMachineDetails(DataSource ds, String machineid) throws SQLException {
        String sql = "select * from ticketmachine where machineid=?";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, machineid);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? new TicketMachine(rs) : null;
            }
        }
    }

    public static int insertTktMachine(DataSource ds, TicketMachine e) throws SQLException {
        String sql = "insert into ticketmachine(machineid,zoneid,stationname,gateno)values(?,?,?,?)";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setString(1, e.machineid);
            pstmt.setInt(2, e.zoneId);
            pstmt.setString(3, e.stationName);
            pstmt.setString(4, e.gateNo);
            System.out.println(pstmt.toString());
            int res = pstmt.executeUpdate();
            con.commit();
            return res;
        }
    }

    public static int updateTktMachine(DataSource ds, TicketMachine e) throws SQLException {
        String sql = "update ticketmachine set machineid=?,zoneid=?,stationname=?,gateno=? where id=?";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setString(1, e.machineid);
            pstmt.setInt(2, e.zoneId);
            pstmt.setString(3, e.stationName);
            pstmt.setString(4, e.gateNo);
            pstmt.setInt(5, e.id);
            int res = pstmt.executeUpdate();
            con.commit();
            return res;
        }
    }

    public static int deleteTktMachine(DataSource ds, TicketMachine e) throws SQLException {
        String sql = "delete from ticketmachine where machineid=?";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            pstmt.setString(1, e.machineid);
            int res = pstmt.executeUpdate();
            con.commit();
            return res;
        }
    }

    Integer id;
    String machineid;
    Integer zoneId;
    String stationName;
    String gateNo;

    public TicketMachine(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.machineid = rs.getString("machineid");
        this.zoneId = rs.getInt("zoneid");
        this.stationName = rs.getString("stationname");
        this.gateNo = rs.getString("gateno");
    }

    public TicketMachine() {
    }

    public TicketMachine(String machineid, Integer zoneid, String stationname, String gateno) {
        this.machineid = machineid;
        this.zoneId = zoneid;
        this.stationName = stationname;
        this.gateNo = gateno;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public Integer getZoneid() {
        return zoneId;
    }

    public void setZoneid(Integer zoneid) {
        this.zoneId = zoneid;
    }

    public String getStationname() {
        return stationName;
    }

    public void setStationname(String stationname) {
        this.stationName = stationname;
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
        hash = 43 * hash + Objects.hashCode(this.machineid);
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
        final TicketMachine other = (TicketMachine) obj;
        if (!Objects.equals(this.machineid, other.machineid)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TicketMachine{" + "id=" + id + ", machineid=" + machineid + ", zoneid=" + zoneId + ", stationname=" + stationName + ", gateno=" + gateNo + '}';
    }

    
}
