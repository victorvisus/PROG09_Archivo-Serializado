package com.cypherrstudios.serializacion;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AppFicheroClientes {

    private static Scanner teclado = new Scanner(System.in);

    public static void main(String[] args) {
        File fichero = new File("clientes.dat");

        try {
            //Crea el fichero
            fichero.createNewFile();
        } catch (IOException ex) {
            System.out.println("Ha habido un error al crear el fichero");
        }

        MenuApp(fichero);
    }

    private static void MenuApp(File fichero) {

        //Esto sirve para que en caso de poner espacios no afecte
        teclado.useDelimiter("\n");
        boolean salir = false;
        int opcion; //Guardaremos la opcion del usuario

        String NIF, nombre, telefono, direccion;

        Cliente c;

        double deuda;

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
                            System.out.println("\nIntroduce un DNI");
                            NIF = teclado.next();

                            System.out.println("\nIntroduce el nombre");
                            nombre = teclado.next();

                            System.out.println("\nIntroduce el telefono");
                            telefono = teclado.next();

                            System.out.println("\nIntroduce la dirección");
                            direccion = teclado.next();

                            System.out.println("\nIntroduce el importe de la deuda");
                            deuda = teclado.nextDouble();

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
}
