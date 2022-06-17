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
import java.time.LocalDateTime;
import java.util.Objects;
import javax.sql.DataSource;

public class Ticket {

    public static Ticket getTicket(DataSource ds, Integer id) throws SQLException {
        String sql = "select * from ticket where id=?";
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                return rs.next() ? new Ticket(rs) : null;
            }
        }
    }

    public static int saveTicket(DataSource ds, Ticket e) throws SQLException {
        String sql = "insert into ticket(fzoneid,fstationname,fgateno,machineid,"
                + "tzoneid,tstationname,"
                + "dt,tickettype,zonecount,validdt,price)values"
                + "(?,?,?,?,?, ?,?,?,?,?, ?)returning id";
        int id = 0;
        try (Connection con = ds.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            con.setAutoCommit(false);
            int i = 1;
            pstmt.setInt(i++, e.fzoneid);
            pstmt.setString(i++, e.fstationname);
            pstmt.setString(i++, e.fgateno);
            pstmt.setString(i++, e.machineid);
            pstmt.setInt(i++, e.tzoneid);
            pstmt.setString(i++, e.tstationname);
            pstmt.setObject(i++, e.dt);
            pstmt.setString(i++, e.tickettype);
            pstmt.setInt(i++, e.zonecount);
            pstmt.setObject(i++, e.validdt);
            pstmt.setDouble(i, e.price);
            System.out.println(pstmt.toString());
            try (ResultSet rs = pstmt.executeQuery()) {
                rs.next();
                id = rs.getInt(1);
            }
            con.commit();
            return id;
        }
    }

    Integer id;
    Integer fzoneid;
    String fstationname;
    String fgateno;
    String machineid;
    Integer tzoneid;
    String tstationname;
    LocalDateTime dt;
    String tickettype;
    Integer zonecount;
    LocalDateTime validdt;
    Double price;

    public Ticket(ResultSet rs) throws SQLException {
        this.id = rs.getInt("id");
        this.fzoneid = rs.getInt("fzoneid");
        this.fstationname=rs.getString("fstationname");
        this.fgateno=rs.getString("fgateno");
        this.machineid = rs.getString("machineid");
        this.tzoneid = rs.getInt("tzoneid");
        this.tstationname=rs.getString("tstationname");
        this.dt = rs.getTimestamp("dt").toLocalDateTime();
        this.tickettype = rs.getString("tickettype");
        this.zonecount = rs.getInt("zonecount");
        this.validdt = rs.getTimestamp("validdt").toLocalDateTime();
        this.price = rs.getDouble("price");
    }

    public Ticket() {
    }

    public Ticket(Integer fzoneid, String fstationname, String fgateno, String machineid, Integer tzoneid, String tstationname, LocalDateTime dt, String tickettype, Integer zonecount, LocalDateTime validdt, Double price) {
        this.fzoneid = fzoneid;
        this.fstationname = fstationname;
        this.fgateno = fgateno;
        this.machineid = machineid;
        this.tzoneid = tzoneid;
        this.tstationname = tstationname;
        this.dt = dt;
        this.tickettype = tickettype;
        this.zonecount = zonecount;
        this.validdt = validdt;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getFzoneid() {
        return fzoneid;
    }

    public void setFzoneid(Integer fzoneid) {
        this.fzoneid = fzoneid;
    }

    public String getFstationname() {
        return fstationname;
    }

    public void setFstationname(String fstationname) {
        this.fstationname = fstationname;
    }

    public String getFgateno() {
        return fgateno;
    }

    public void setFgateno(String fgateno) {
        this.fgateno = fgateno;
    }

    public String getMachineid() {
        return machineid;
    }

    public void setMachineid(String machineid) {
        this.machineid = machineid;
    }

    public Integer getTzoneid() {
        return tzoneid;
    }

    public void setTzoneid(Integer tzoneid) {
        this.tzoneid = tzoneid;
    }

    public String getTstationname() {
        return tstationname;
    }

    public void setTstationname(String tstationname) {
        this.tstationname = tstationname;
    }

    public LocalDateTime getDt() {
        return dt;
    }

    public void setDt(LocalDateTime dt) {
        this.dt = dt;
    }

    public String getTickettype() {
        return tickettype;
    }

    public void setTickettype(String tickettype) {
        this.tickettype = tickettype;
    }

    public Integer getZonecount() {
        return zonecount;
    }

    public void setZonecount(Integer zonecount) {
        this.zonecount = zonecount;
    }

    public LocalDateTime getValiddt() {
        return validdt;
    }

    public void setValiddt(LocalDateTime validdt) {
        this.validdt = validdt;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final Ticket other = (Ticket) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ticket{" + "id=" + id + ", fzoneid=" + fzoneid + ", fstationname=" + fstationname + ", fgateno=" + fgateno + ", machineid=" + machineid + ", tzoneid=" + tzoneid + ", tstationname=" + tstationname + ", dt=" + dt + ", tickettype=" + tickettype + ", zonecount=" + zonecount + ", validdt=" + validdt + ", price=" + price + '}';
    }

    
}
