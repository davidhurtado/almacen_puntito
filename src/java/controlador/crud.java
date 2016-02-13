/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;
import da.medios.*;
import da.computacionales.*;
import da.oficina.*;
import hibernate.configuracion.Hibernate;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author PCA12
 */
@WebServlet(name = "crud", urlPatterns = {"/crud"})
public class crud extends HttpServlet {
    mediosManager currentMedios=new mediosManager();
    computacionalesManager currentComputacionales = new computacionalesManager();
    oficinaManager currentOficina = new oficinaManager();
PrintWriter out;
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
        out = response.getWriter();
        String accion = request.getParameter("accion");
        if (!request.equals("null")) {
            if(request.getSession(true).isNew()){
             //if (Hibernate.getSession().isConnected()) {
                  conexion();
             }
            
            //this.currentMedios.accesoDatos();
            if (accion.equals("codigo")) {                
                request.setAttribute("txtcodigo",this.currentMedios.generar_codigo());
            }
            
            if (accion.equals("agregar")) {                
                guardar(request, response, accion);
            }
            
            if (accion.equals("buscar")) {                
                busqueda(request, response, accion);
            }
        } 
        RequestDispatcher vista = request.getRequestDispatcher("index.jsp");
        vista.include(request, response);
    }
    
  
    
    private void guardar(HttpServletRequest request, HttpServletResponse response, String accion) {
        String med = request.getParameter("medios");
        int codigo = Integer.parseInt(request.getParameter("txtcodigo"));
        String material = request.getParameter("txtmaterial");
        String productor = request.getParameter("txtproductor");
        double costo = Double.parseDouble(request.getParameter("txtcosto"));
        String fecha = request.getParameter("txtfecha");

        if (med.equals("computancional")) {
            String marca = request.getParameter("txtmarca");
            String anio = request.getParameter("txtanio");
            computacionales elemento = new computacionales(codigo, material, productor, costo, fecha, marca, anio);
            this.currentComputacionales.guardar_computacional(elemento);
            System.out.println(elemento.toString());
        } else if (med.equals("oficina")) {
            String pais = request.getParameter("txtpais");
            oficina elemento = new oficina(codigo, material, productor, costo, fecha, pais);
            this.currentOficina.guardar_oficina(elemento);
            System.out.println(elemento.toString());
        }
    }
    
    private void busqueda(HttpServletRequest request, HttpServletResponse response, String accion) {
        String search = request.getParameter("consultas");
        int codigo = Integer.parseInt(request.getParameter("txtayuda"));

        if (search.equals("codigo")) {
            out.println("Codigo -> "+codigo+ this.currentMedios.generar_codigo());
        }
        
        if (search.equals("med")) {
            out.println("med -> "+codigo);
        }
       
        if (search.equals("productor")) {
             out.println("productor -> "+codigo);
        }
        
        if (search.equals("encargado")) {
             out.println("encargado -> "+codigo);
        }
    }
    
    protected void conexion() throws IOException {
        Hibernate.setProyectDirectory(new File (getServletContext().getRealPath("")));
        Hibernate.initialize();
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
        processRequest(request, response);
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
        processRequest(request, response);
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
