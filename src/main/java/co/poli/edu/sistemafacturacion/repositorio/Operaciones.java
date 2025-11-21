package co.poli.edu.sistemafacturacion.repositorio;

import java.util.ArrayList;

public interface Operaciones<T> {

    String insertar(T entidad);

    String eliminar(String id);

    String actualizar(T entidad);

    Object selectId(String id);

    Object selectAll();

}
