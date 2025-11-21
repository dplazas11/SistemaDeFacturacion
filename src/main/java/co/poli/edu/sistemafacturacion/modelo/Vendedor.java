/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.poli.edu.sistemafacturacion.modelo;

/**
 *
 * @author LENOVO
 */
public class Vendedor {
    
    String idVendedor;
    String nombre;
    String direccion;
    String correo;
    
    
    public Vendedor(){}
    public Vendedor(String idVendedor, String nombre, String direccion, String correo) {
        this.idVendedor = idVendedor;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
    }
    
    

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
    
    
    
}
