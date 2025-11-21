/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package co.poli.edu.sistemafacturacion.modelo;

/**
 *
 * @author LENOVO
 */
public class Producto {
    
    String idProducto;
    String Descripcion;
    int cantidad;
    double precioUnitario;

    public Producto(){}
    public Producto(String codProducto, String Descripcion, int cantidad, double precioUnitario) {
        this.idProducto = codProducto;
        this.Descripcion = Descripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
    }

    public String getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(String codProducto) {
        this.idProducto = codProducto;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(double precioUnitario) {
        this.precioUnitario = precioUnitario;
    }
    
    
    
}
