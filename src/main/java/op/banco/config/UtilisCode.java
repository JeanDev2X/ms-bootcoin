package  op.banco.config;

import java.util.Random;

public class UtilisCode {
	
	public static String numTransaccion() {
		Random rand = new Random();
        // Generar un número aleatorio de 4 dígitos
        int numeroAleatorio = rand.nextInt(9000) + 1000;
        String numero = String.valueOf(numeroAleatorio);        
        return numero;
	}

}
