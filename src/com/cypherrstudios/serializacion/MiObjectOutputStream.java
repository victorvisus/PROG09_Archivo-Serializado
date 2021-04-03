package com.cypherrstudios.serializacion;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * al parecer ObjectOutputString tiene problemas con las cabeceras, que es lo
 * que usa para * leer. Si estamos constantemente cambiando hay partes del
 * fichero que se quedan innaccesibles.
 *
 * Lo ideal es que solo tengamos una cabecera.
 *
 * En este caso si el fichero no tiene nada lo que tenemos que hacer es escribir
 * con el ObjectOutputStream.
 *
 * ¿qué pasa si el fichero ya existe y ya tiene datos? Tenemos que escribir con
 * la clase MiObjectOutputStream. Porque ésta no escribe cabeceras
 */
public class MiObjectOutputStream extends ObjectOutputStream {

    //Sobreescribimos el método que crea la cabecera
    @Override
    protected void writeStreamHeader() throws IOException {
        // No hacer nada
    }

    //Constructores
    public MiObjectOutputStream() throws IOException, SecurityException {
        super();
    }

    public MiObjectOutputStream(OutputStream out) throws IOException {
        super(out);
    }

}
