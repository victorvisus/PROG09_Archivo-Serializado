package com.cypherrstudios.serializacion;

import java.io.Serializable;
import java.util.Objects;

public class Cliente implements Serializable {

    private String NIF;
    private String nombre;
    private String telefono;
    private String direccion;
    private double deuda;

    /**
     * Constructor estandar de la clase
     *
     * @param NIF
     * @param nombre
     * @param telefono
     * @param direccion
     * @param deuda
     */
    public Cliente(String NIF, String nombre, String telefono, String direccion, double deuda) {
        this.NIF = NIF;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.deuda = deuda;
    }

    /**
     * Este constructor es necesario para realizar búsquedas en el archivo.
     *
     * @param NIF : lo usaremos a modo de id para realizar las búsquedas.
     */
    public Cliente(String NIF) {
        this.NIF = NIF;
    }

    // Métodos get y set
    public double getDeuda() {
        return deuda;
    }

    public void setDeuda(double deuda) {
        this.deuda = deuda;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Método equals, para realizar las busquedas por coincidencia en el
     * archivo.
     *
     * @param obj : objeto de la misma clase
     * @return
     */
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
        final Cliente other = (Cliente) obj;
        if (!Objects.equals(this.NIF, other.NIF)) {
            return false;
        }
        return true;
    }

    //Método toString.
    @Override
    public String toString() {
        return "Cliente{" + "NIF=" + NIF + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion + ", deuda=" + deuda + '}';
    }

}
