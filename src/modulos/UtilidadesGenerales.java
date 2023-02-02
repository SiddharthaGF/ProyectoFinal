package modulos;

import java.util.Scanner;

public class UtilidadesGenerales {

    Scanner teclado = new Scanner(System.in);

    public int LeerNumeroIntervalo(int min, int max) {
        int num;
        while (true) {
            num = teclado.nextInt();
            if (num >= min && num <= max) {
                teclado.reset();
                return num;
            }
            System.out.print("Entrada no valida, intente de nuevo >> ");
        }
    }

    public int LeerNumeroMayorQue(int valor) {
        return LeerNumeroIntervalo(valor, Integer.MAX_VALUE);
    }

    public int LeerNumeroMenorQue(int valor) {
        return LeerNumeroIntervalo(-Integer.MAX_VALUE, valor);
    }

    public int LeerNumero() {
        return LeerNumeroIntervalo(0, 0);
    }

    public String LeerCadena() {
        String string;
        Scanner teclado = new Scanner(System.in);
        while (true) {
            string = teclado.nextLine().trim();
            if (string.length() > 0) {
                teclado.reset();
                return string;
            }
            System.out.print("Entrada no valida, intente de nuevo >> ");
        }
    }

}
