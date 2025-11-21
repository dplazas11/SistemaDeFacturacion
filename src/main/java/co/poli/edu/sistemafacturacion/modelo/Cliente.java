/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.poli.edu.sistemafacturacion.modelo;

/**
 *
 * @author LENOVO
 */
public class Cliente {
    
    String idCliente;
    String nombre;
    String direccion;
    String correo;

    public Cliente (){}
    public Cliente(String idCliente, String nombre, String direccion, String correo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.direccion = direccion;
        this.correo = correo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
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
