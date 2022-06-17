/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import models.GateModel;
import models.ZoneModel;


@WebServlet(urlPatterns = {"/listGate", "/addGate", "/editGate",
    "/insGate", "/updGate", "/delGate"})
public class GateServlet extends HttpServlet {

    @Resource(name = "jdbc/postgresUpwork1Conn")
    private DataSource ds;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet StationGateServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet StationGateServlet at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        System.out.println(userPath);
        String url = "";
        switch (userPath) {
            case "/listGate":
                url = "/pages/listGate.jsp";
                try {
                    ArrayList<GateModel> gates = GateModel.getGateDetails(ds);
                    request.setAttribute("gates", gates);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                request.getRequestDispatcher(url).forward(request, response);
                break;
            case "/addGate":
                url = "/pages/addEditGate.jsp";
                request.setAttribute("action", "add");
                try {
                    request.setAttribute("zones", ZoneModel.getZoneList(ds));
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                request.getRequestDispatcher(url).forward(request, response);
                break;
            default:
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userPath = request.getServletPath();
        System.out.println(userPath);
        String url = "";
        switch (userPath) {
            case "/editGate":
                url = "/pages/addEditGate.jsp";
                try {
                    request.setAttribute("action", "edit");
                    GateModel gate = GateModel.gateDetails(ds,
                            Integer.valueOf(request.getParameter("zoneid")),
                            request.getParameter("stationname"),
                            request.getParameter("gateno"));
                    request.setAttribute("gate", gate);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                request.getRequestDispatcher(url).forward(request, response);
                break;
            case "/insGate":
                try {
                url = request.getContextPath() + "/listGate";
                GateModel gate = new GateModel(
                        Integer.valueOf(request.getParameter("zoneid")),
                        request.getParameter("stationname"),
                        request.getParameter("gateno"));
                GateModel.insertGateData(ds, gate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect(url);
            break;
            case "/updGate":
                try {
                url = request.getContextPath() + "/listGate";
                GateModel gate = new GateModel(
                        Integer.valueOf(request.getParameter("zoneid")),
                        request.getParameter("stationname"),
                        request.getParameter("gateno"));
                gate.setId(Integer.valueOf(request.getParameter("id")));
                GateModel.updateGateData(ds, gate);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            response.sendRedirect(url);
            break;
            case "/delGate":
                url = request.getContextPath() + "/listGate";
                try {
                    GateModel gate = GateModel.gateDetails(ds,
                        Integer.valueOf(request.getParameter("zoneid")),
                        request.getParameter("stationname"),
                        request.getParameter("gateno"));
                    GateModel.deleteGate(ds, gate);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                response.sendRedirect(url);
                break;
            default:
                break;
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
