package com.cypherrstudios.serializacion;

import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * Tarea correspondiente al tema 9 de la asignatura de Programación.
 *
 * Aplicación que gestiona los clientes de una empresa. Esos datos, se
 * almacenarán en un fichero serializado, denominado clientes.dat.
 *
 * @author Victor Visús García
 * @version 1.5
 *
 */
public class AppClientes {

    private static Scanner teclado = new Scanner(System.in);

    //Objeto Cliente para crear los objetos que se añadiran al fichero
    private static Cliente c = new Cliente();
    //Este lo usaremos para realizar las busquedas
    private static Cliente auxC = new Cliente();
    /* Crea el objeto de la clase GestorFichero, que usaremos para realizar
       las operaciones del programa */
    private static GestorFichero<Cliente> gestor = new GestorFichero("clientes.dat");

    public static void main(String[] args) {

        //Esto sirve para que en caso de poner espacios no afecte
        teclado.useDelimiter("\n");
        boolean salir = false;

        int opcion; //Guardaremos la opcion del usuario

        //Creo un HashMap para almacenar los datos para crear un nuevo Objeto Cliente
        HashMap<String, String> datosCliente = new HashMap<>();

        String NIF, nombre, telefono, direccion;
        double deuda;

        while (!salir) {

            System.out.println("\n1. Añadir cliente.");
            System.out.println("2. Listar clientes.");
            System.out.println("3. Buscar clientes.");
            System.out.println("4. Borrar cliente.");
            System.out.println("5. Borrar fichero.");
            System.out.println("6. Salir\n");

            try {

                System.out.println("Escribe una de las opciones");
                opcion = teclado.nextInt();

                switch (opcion) {
                    case 1:
                        //Añadir cliente.//

                        //Creamos el Cliente
                        //Llama al método que solicita los datos y los almacena en un hashmap
                        solicitarDatos(datosCliente);

                        NIF = datosCliente.get("nif");
                        nombre = datosCliente.get("nombre");
                        telefono = datosCliente.get("telefono");
                        direccion = datosCliente.get("direccion");
                        //Asigno el valor de la clave deuda a la variable deuda, convirtiendo el String a Double.
                        deuda = Double.parseDouble(datosCliente.get("deuda").replace(",", "."));

                        auxC = new Cliente(NIF); // Creo un objeto para comprobar si existe el cliente

                        c = new Cliente(NIF, nombre, telefono, direccion, deuda);
                        gestor.guardarDato(c);
                        System.out.println("\nSe ha añadido correctamente");

                        break;
                    case 2:
                        //Listar clientes.//

                        if (gestor.existeFichero()) {
                            gestor.mostrarDatos();

                        } else {
                            System.out.println("*** No existe ningún fichero ***");
                        }

                        break;

                    case 3:
                        //Buscar clientes.//
                        if (gestor.existeFichero()) {
                            auxC = new Cliente(solicitaDatoParaBuscar());
                            if (gestor.existeDato(auxC)) {
                                System.out.println("El Cliente se encuentra en la lista"
                                        + "\nSus datos son: " + gestor.imprimirCliente(auxC));
                            } else {
                                System.out.println("El Cliente con el NIF indicado no existe");
                            }
                        } else {
                            System.out.println("*** No existe ningún fichero ***");
                        }
                        break;
                    case 4:
                        //Borrar cliente.//
                        if (gestor.existeFichero()) {
                            auxC = new Cliente(solicitaDatoParaBuscar());
                            if (gestor.existeDato(auxC)) {
                                gestor.borrarDatos(auxC);
                                System.out.println("El Cliente se ha borrado correctamente");

                            } else {
                                System.out.println("*** El Cliente con el NIF indicado no existe ***");
                            }

                        } else {
                            System.out.println("*** No existe ningún fichero ***");
                        }

                        break;

                    case 5:
                        //Borrar fichero de clientes completamente.//
                        gestor.borrarFichero();

                        break;
                    case 6:
                        salir = true;
                        break;
                    default:
                        System.out.println("Solo números entre 1 y 5");
                }
            } catch (InputMismatchException e) {
                System.out.println("Debes insertar un número");
                teclado.next();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                try {
                    System.in.read();
                } catch (IOException e) {
                }
            }
        }
    }

    /**
     * Solicita los datos necesarios para crear el objeto cliente.
     * <p>
     * Los almacena en el HashMap datosCliente, con su clave y al valor le
     * asigna la información introducida por el usuario.</p>
     *
     * @param datosCliente
     * @return datosCliente actualizado con los datos introducidos.
     * @throws : lanza un error, si el NIF ya existe, y vuelve al menú principal
     */
    private static HashMap solicitarDatos(HashMap datosCliente) throws Exception {
        teclado.useDelimiter("\n");

        String NIF;
        System.out.println("\nIntroduce un DNI");
        NIF = teclado.next().toUpperCase();
        auxC.setNIF(NIF);

        // Comprueba que no exista el cliente, mediante el NIF
        if (!gestor.existeDato(auxC)) {
            datosCliente.put("nif", NIF);

            System.out.println("\nIntroduce el nombre");
            //nombre = teclado.next();
            datosCliente.put("nombre", teclado.next());

            System.out.println("\nIntroduce el telefono");
            //telefono = teclado.next();
            datosCliente.put("telefono", teclado.next());

            System.out.println("\nIntroduce la dirección");
            //direccion = teclado.next();
            datosCliente.put("direccion", teclado.next());

            String deuda = "";
            //Le solicita el dato al usuario hasta que no introduzca solo caracteres numéticos
            do {
                System.out.println("\nIntroduce el importe de la deuda");
                deuda = teclado.next();
            } while (!esNumero(deuda));

            datosCliente.put("deuda", deuda);
        } else {
            throw new Exception("*** El NIF introducido ya existe, pulsa INTRO para continuar ***");
        }
        estaVacio(datosCliente);

        return datosCliente;
    }

    /**
     * Solicita el dato necesario para realizar la búsqueda, en este caso el DNI
     *
     * @return DNI
     */
    private static String solicitaDatoParaBuscar() {

        System.out.println("Introduce el NIF del cliente");
        String nifBuscar = teclado.next().toUpperCase();

        return nifBuscar;
    }

    //Comprobaciones
    /**
     * Comprueba que en el String introducido sean todo número para que no salte
     * error al hacer el casting a double
     *
     * @param deuda : String introducido por el usuario
     * @return true si son números, en caso contrario false
     */
    private static boolean esNumero(String deuda) {

        if ((deuda.matches("[+-]?\\d*(\\.\\d+)?") || deuda.equals("")) == false) {
            System.out.println("*** Solo se admiten caracteres numéricos ***");

            return false;
        }
        return true;
    }

//    private static boolean estaVacio(String campo) {
//
//        if (campo == null || campo.length() == 0) {
//            System.out.println("*** Este campo no puede estar vacio ***");
//            return false;
//        }
//        return true;
//    }
    /**
     * Comprueba que ninguno de los datos solicitados para crear el cliente
     * esten vacios. Si alguno de ellos esta vacio pide el valor para ese campo
     *
     * @param datosCliente
     * @return datosCliente : completado con los datos faltantes, si fuera el
     * caso, si no lo devuelve tal cual.
     */
    private static HashMap estaVacio(HashMap datosCliente) {
        Iterator<Entry<String, String>> it = datosCliente.entrySet().iterator();

        while (it.hasNext()) {
            Entry<String, String> campo = it.next();
            if (campo.getValue() == null || campo.getValue().length() == 0) {
                System.out.println("El campo \"" + campo.getKey().toUpperCase() + "\" no puede estar vacio"
                        + "\nPor favor introduce un valor para " + campo.getKey() + ":");
                datosCliente.put(campo.getKey(), teclado.next());

            }
        }
        return datosCliente;
    }
}
