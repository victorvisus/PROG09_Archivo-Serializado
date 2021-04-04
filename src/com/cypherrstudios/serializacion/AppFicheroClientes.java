package com.cypherrstudios.serializacion;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Tarea correspondiente al tema 9 de la asignatura de Programación.
 *
 * Aplicación que gestiona los clientes de una empresa. Esos datos, se
 * almacenarán en un fichero serializado, denominado clientes.dat.
 *
 * @author Victor Visús García
 * @version 1.0
 *
 */
public class AppFicheroClientes {

    private static Scanner teclado = new Scanner(System.in);

    /**
     * Clase main. En cuanto se ejecuta el programa, crea el fichero -si no
     * existe-
     *
     * @throws lanza un error si ha habido algún problema con la creación del
     * fichero
     */
    public static void main(String[] args) {
        File fichero = new File("clientes.dat");

        if (!fichero.exists()) {
            try {
                //Crea el fichero
                fichero.createNewFile();
            } catch (IOException ex) {
                System.out.println("Ha habido un error al crear el fichero.");
            }
        }

        MenuApp(fichero);
    }

    /**
     * Crea y gestiona el menú del programa
     *
     * @param fichero
     */
    private static void MenuApp(File fichero) {

        //Esto sirve para que en caso de poner espacios no afecte
        teclado.useDelimiter("\n");
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        HashMap<String, String> datosCliente = new HashMap<>();

        String NIF, nombre, telefono, direccion;
        double deuda;

        Cliente c;

        while (!salir) {

            System.out.println("1. Añadir cliente.");
            System.out.println("2. Listar clientes.");
            System.out.println("3. Buscar clientes.");
            System.out.println("4. Borrar cliente.");
            System.out.println("5. Borrar fichero.");
            System.out.println("6. Salir");

            try {

                System.out.println("Escribe una de las opciones");
                opcion = teclado.nextInt();

                switch (opcion) {
                    case 1:
                        //Añadir cliente.

                        if (fichero.exists()) {

                            //Creamos el Cliente
                            //Llama al método que solicita los datos y los almacena en un hashmap
                            solicitarDatos(datosCliente);

                            NIF = datosCliente.get("nif");
                            nombre = datosCliente.get("nombre");
                            telefono = datosCliente.get("telefono");
                            direccion = datosCliente.get("direccion");
                            //Asigno el valor de la clave deuda a la variable deuda, convirtiendo el String a Double.
                            deuda = Double.parseDouble(datosCliente.get("deuda").replace(",", "."));

                            c = new Cliente(NIF, nombre, telefono, direccion, deuda);

                            //Esto no vale para un ArrayList, porque añade todo el contenido.
                            ObjectOutputStream oos;

                            //ESTO ES IMPORTANTE, esta relacionado con la clase MiObjectOutputStream
                            if (fichero.length() == 0) {
                                oos = new ObjectOutputStream(new FileOutputStream(fichero));

                            } else {
                                oos = new MiObjectOutputStream(new FileOutputStream(fichero, true));
                            }
                            oos.writeObject(c);

                            System.out.println("\nSe ha añadido correctamente");

                        } else {
                            System.out.println("*** No existe ningún fichero ***");
                        }
                        break;
                    case 2:
                        //Listar clientes

                        if (fichero.exists()) {

                            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero));

                            while (true) {

                                c = (Cliente) ois.readObject();

                                System.out.println(c.toString());

                            }
                        } else {
                            System.out.println("*** No existe ningún fichero ***");
                        }

                        break;
                    case 3:
                        //Buscar clientes

                        break;
                    case 4:
                        //Borrar cliente

                        break;
                    case 5:
                        //Borrar fichero de clientes completamente
                        if (fichero.exists()) {
                            fichero.delete();
                            System.out.println("\nEl fichero se ha borrado");
                        } else {
                            System.out.println("*** No existe ningún fichero que borrar ***");
                        }

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
            } catch (FileNotFoundException ex) {
                Logger.getLogger(AppFicheroClientes.class.getName()).log(Level.SEVERE, null, ex);
            } catch (EOFException e) {
                System.out.println("Se ha llegado al final del archivo");
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(AppFicheroClientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Solicita los datos necesarios para crear el objeto cliente.
     * <p>
     * Los almacena en el HashMap datosCliente, con su clave y al valor le
     * asigna la información introducida por el usuario</p>
     *
     * @param datosCliente
     * @return datosCliente actualizado con los datos introducidos.
     */
    public static HashMap solicitarDatos(HashMap datosCliente) {
        teclado.useDelimiter("\n");

        System.out.println("\nIntroduce un DNI");
        //NIF = teclado.next();
        datosCliente.put("nif", teclado.next());

        System.out.println("\nIntroduce el nombre");
        //nombre = teclado.next();
        datosCliente.put("nombre", teclado.next());

        System.out.println("\nIntroduce el telefono");
        //telefono = teclado.next();
        datosCliente.put("telefono", teclado.next());

        System.out.println("\nIntroduce la dirección");
        //direccion = teclado.next();
        datosCliente.put("direccion", teclado.next());

        System.out.println("\nIntroduce el importe de la deuda");
        //deuda = teclado.nextDouble();
        datosCliente.put("deuda", teclado.next());

        return datosCliente;
    }
}
