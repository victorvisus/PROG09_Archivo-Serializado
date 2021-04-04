package com.cypherrstudios.serializacion;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

/**
 * <strong>Disco Duro de Roer: ficheros #9</strong>
 * Vamos a crear un gestor de fichero serializados.
 * <ul>Contendrá lo siguiente:
 * <li>Atributos: fichero y arraylist de objetos</li>
 * <li>obtenerDatos(): trae toda la información de un fichero</li>
 * <li>guardarDato(objeto): guarda un objeto en el fichero y en la lista</li>
 * </ul>
 *
 * <strong>Disco Duro de Roer: ficheros #10</strong>
 * <ul>Agregamos los siguientes métodos:
 * <li>mostrarDatos(): muestra el contenido de la lista</li>
 * <li>existeDato(): indica si existe ese elemento</li>
 * <li>borrarDato(): borra un objeto de la lista y del fichero</li>
 * <li>getDatos(): para obtener el array de datos</li>
 * </ul>
 *
 * <strong>Añado yo</strong>
 * <ul>
 * <li>existeFichero(): Comprueba la existencia del archivo de datos</li>
 * <li>getFichero(): para coger el fichero</li>
 * <li>borrarFichero(): elimina el fichero y reinicia el array</li>
 * </ul>
 */
/**
 *
 * @see https://youtu.be/XObwZSdYakk y https://youtu.be/p1qDdJ1zDbo
 *
 */
public class GestorFichero<T> {

    private File fichero;
    private ArrayList<T> datos;

    /**
     * Primer Constructor, crea el fichero - con el nombre pasado por el
     * parámetro- y el arraylist.
     *
     * @param fichero : String en el que se indica el nombre que tiene que darle
     * al fichero.
     */
    public GestorFichero(String fichero) {
        this.fichero = new File(fichero);
        this.datos = new ArrayList();
        obtenerDatos();
    }

    /**
     * Segundo Constructor, asigna el fichero pasado al atributo de la clase, y
     * crea el arraylist.
     *
     * @param fichero : Recibe un fichero ya creado.
     */
    public GestorFichero(File fichero) {
        this.fichero = fichero;
        this.datos = new ArrayList();
    }

    /**
     * Comprobar que existe el fichero
     */
    public boolean existeFichero() {
        if (fichero.exists()) {
            return true;
        }
        return false;
    }

    /**
     * Lee y extrae los datos del fichero y los añade al arraylist.
     *
     * Si el fichero existe, entonces lee el archivo y lo mete en el objeto ois
     */
    public void obtenerDatos() {

        if (fichero.exists()) {
            T elemento;
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichero))) {
                //hasta que no salte la exception del final del archivo
                while (true) {
                    elemento = (T) ois.readObject();
                    datos.add(elemento);
                }

            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (EOFException ex) {
                //Esta es la exception que hace la salida del while
            } catch (IOException | ClassNotFoundException ex) {
                System.out.println(ex.getMessage());
            }

        }
    }

    /**
     * Almacena el dato -objeto- en el fichero y en el arraylist, pasado como
     * parámetro
     *
     * Si el fichero NO existe usa la clase ObjectOutputStream, que crea la
     * cabecera, si existe usa la clase MiObjectOutputStream que implementa a la
     * anterior y NO crea cabeceras para evitar problemas
     *
     * @param elemento : objeto genérico que tiene que almacenar.
     */
    public void guardarDato(T elemento) {

        if (fichero.exists() && fichero.length() > 0) {
            try (MiObjectOutputStream oos = new MiObjectOutputStream(new FileOutputStream(fichero, true))) {

                oos.writeObject(elemento);

            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }
        } else {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichero))) {

                oos.writeObject(elemento);

            } catch (FileNotFoundException ex) {
                System.out.println(ex.getMessage());
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
            }

        }

        datos.add(elemento);

    }

    /**
     * get del objeto para poder cogerlo y ver que funciona
     */
    public ArrayList<T> getDatos() {
        return this.datos;
    }

    /**
     * Para poder coger el fichero
     *
     * @return fichero
     */
    public File getFichero() {
        return fichero;
    }

    /**
     * Muestra los datos de la lista, la clase debe tener un toString()
     */
    public void mostrarDatos() {
        for (T e : datos) {
            System.out.println(e);
        }
    }

    /**
     * Método para buscar un dato en el objeto, realmente lo busca en el
     * arraylist, puesto que, si existe en el arraylist, existe en el objeto
     *
     * @param elemento : objeto que queremos que busque
     * @return boolean : true si existo, false si no existe
     */
    public boolean existeDato(T elemento) {

        for (T e : datos) {
            if (e.equals(elemento)) {
                return true;
            }
        }
        return false;
    }

    /**
     * No se puede entrar en un fichero y decirle que borre algo, hay que
     * eliminar el fichero y volver a crearlo sin el dato que se quiera eliminar
     *
     * Elimina el elemento pasado como parámetro, hace una copia del arraylist
     * "datos", lo reinicia y elimina el fichero.
     *
     * Mediante el foreach añade los datos del arraylist "copia" al "nuevo"
     * fichero y al "nuevo" arraylist datos.
     *
     * @param elemento
     */
    public void borrarDatos(T elemento) {

        /*borramos el dato de la lista. Borra la primera coincidencia, si se ejecuta
        una segunda vez borrará la siguiente coincidencia, y asi sucesivamente.*/
        /**
         * Si existe el elemento, ejecuta el código, si no, no
         */
        if (datos.remove(elemento)) {

            //creamos una copia
            ArrayList<T> copia = datos;
            datos = new ArrayList<>();

            //borramos el archivo
            fichero.delete();

            //insertamos la nueva lista al fichero
            for (T e : copia) {
                guardarDato(e);
            }
        }
    }

    /**
     * Elimina el fichero y reinicia el arraylist.
     *
     */
    public void borrarFichero() {
        if (fichero.exists()) {
            fichero.delete();
            datos = new ArrayList<>();
            System.out.println("Fichero eliminado con exito");

        } else {
            System.out.println("*** No existe ningún fichero que borrar ***");
        }
    }
}
