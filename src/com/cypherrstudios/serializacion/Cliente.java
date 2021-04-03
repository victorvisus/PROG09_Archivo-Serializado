package com.cypherrstudios.serializacion;

import java.io.Serializable;

public class Cliente implements Serializable {

    private String NIF;
    private String nombre;
    private String telefono;
    private String direccion;
    private double deuda;

    public Cliente(String NIF, String nombre, String telefono, String direccion, double deuda) {
        this.NIF = NIF;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.deuda = deuda;
    }

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

    @Override
    public String toString() {
        return "Cliente{" + "NIF=" + NIF + ", nombre=" + nombre + ", telefono=" + telefono + ", direccion=" + direccion + ", deuda=" + deuda + '}';
    }
}
