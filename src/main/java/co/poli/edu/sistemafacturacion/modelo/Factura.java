
package co.poli.edu.sistemafacturacion.modelo;

/**
 *
 * @author LENOVO
 */
public class Factura {

    String idFactura;
    String fecha;
    String idCliente;
    String idVendedor;
    double total;

    
    public Factura() {}
    public Factura(String idFactura, String fecha, String idCliente, String idVendedor, double total) {
        this.idFactura = idFactura;
        this.fecha = fecha;
        this.idCliente = idCliente;
        this.idVendedor = idVendedor;
        this.total = total;
    }


    public String getIdFactura() {
        return idFactura;
    }

    public void setIdFactura(String idFactura) {
        this.idFactura = idFactura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdVendedor() {
        return idVendedor;
    }

    public void setIdVendedor(String idVendedor) {
        this.idVendedor = idVendedor;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Factura{" + "idFactura=" + idFactura + ", fecha=" + fecha + ", idCliente=" + idCliente + ", idVendedor=" + idVendedor + ", total=" + total + '}';
    }
    
    
           
            
    
}
