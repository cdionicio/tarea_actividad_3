package modelo;

import java.awt.HeadlessException;
import java.sql.PreparedStatement;
import javax.swing.table.DefaultTableModel;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;


/**
 *
 * @author carlosd
 */
public class Docente extends Persona{
    private int id;
    private String codigo_docente,fecha_ingreso_laborar,fecha_ingreso_registro;
    private double salario;
    
    Conexion cn;
    
    public Docente(){}

    public Docente(int id, String codigo_docente,double salario, String fecha_ingreso_laborar, String fecha_ingreso_registro) {
        this.id = id;
        this.codigo_docente = codigo_docente;
        this.fecha_ingreso_laborar = fecha_ingreso_laborar;
        this.fecha_ingreso_registro = fecha_ingreso_registro;
        this.salario = salario;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCodigo_docente() {
        return codigo_docente;
    }

    public void setCodigo_docente(String codigo_docente) {
        this.codigo_docente = codigo_docente;
    }

    public String getFecha_ingreso_laborar() {
        return fecha_ingreso_laborar;
    }

    public void setFecha_ingreso_laborar(String fecha_ingreso_laborar) {
        this.fecha_ingreso_laborar = fecha_ingreso_laborar;
    }

    public String getFecha_ingreso_registro() {
        return fecha_ingreso_registro;
    }

    public void setFecha_ingreso_registro(String fecha_ingreso_registro) {
        this.fecha_ingreso_registro = fecha_ingreso_registro;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public DefaultTableModel leer(){
        DefaultTableModel table = new DefaultTableModel();
        
        try {
            cn = new Conexion();
            cn.abrirConexion();
            String query;
            query = "Select id_docente as id,codigo_docente,salario,fecha_ingreso_laborar,fecha_ingreso_registro from docentes;";
            ResultSet consulta = cn.conexionBD.createStatement().executeQuery(query);
        
            String encabezado[] = {"id","codigo_docente","salario","fecha_ingreso_laborar","fecha_ingreso_registro"};
            table.setColumnIdentifiers(encabezado);
            
            String datos[] = new String[5];
            
            while(consulta.next()) {
                datos[0] = consulta.getString("id");
                datos[1] = consulta.getString("codigo_docente");
                datos[2] = consulta.getString("salario");
                datos[3] = consulta.getString("fecha_ingreso_laborar");
                datos[4] = consulta.getString("fecha_ingreso_registro");
                table.addRow(datos);
            }
            cn.cerrarConexion();
            
        } catch(SQLException ex) {
            cn.cerrarConexion();
            System.out.println("Error" + ex.getMessage());
        }
        return table;
    }
    
    @Override
    public void crear() {
        try {
          PreparedStatement parametro;
          cn = new Conexion();
          cn.abrirConexion();
          String query;
          query = "insert into docentes(codigo_docente,salario,fecha_ingreso_laborar,fecha_ingreso_registro) " + 
                  "values(?,?,?,?);";
          parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
          parametro.setString(1, getCodigo_docente());
          parametro.setDouble(2, getSalario());
          parametro.setString(3, getFecha_ingreso_laborar());
          parametro.setString(4, getFecha_ingreso_registro());
          
          int executar = parametro.executeUpdate();
          cn.cerrarConexion();
          
          JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Ingresado",
                  "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    public void editar() {
        try {
          PreparedStatement parametro;
          cn = new Conexion();
          cn.abrirConexion();
          String query;
          query = "update docentes set codigo_docente = ?, salario = ?, fecha_ingreso_laborar = ?, fecha_ingreso_registro = ? where id_docente = ?";
          parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
          parametro.setString(1, getCodigo_docente());
          parametro.setDouble(2, getSalario());
          parametro.setString(3, getFecha_ingreso_laborar());
          parametro.setString(4, getFecha_ingreso_registro());
          parametro.setInt(5, getId());
          
          int executar = parametro.executeUpdate();
          cn.cerrarConexion();
          
          JOptionPane.showMessageDialog(null, Integer.toString(executar) + " Registro Actualizado",
                  "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
    @Override
        public void borrar(){
        try {
            PreparedStatement parametro;
            cn = new Conexion();
            cn.abrirConexion();
            String query;
            query = "delete from docentes where id_docente = ?";
            
            parametro = (PreparedStatement) cn.conexionBD.prepareStatement(query);
            parametro.setInt(1, getId());
            
            int executar =  parametro.executeUpdate();
            cn.cerrarConexion();
            
            JOptionPane.showMessageDialog(null,Integer.toString(executar) + " Registro Eliminado",
                "Mensaje", JOptionPane.INFORMATION_MESSAGE);
        } catch(HeadlessException | SQLException ex) {
            System.out.println("Error: " + ex.getMessage());
        }
    }
    
}
