package modulos;

import java.util.Scanner;

public class Consola {

    public static void Imprimir(Object linea)  {
        System.out.print(linea);
    }

    public static void ImprimirLinea(Object linea)  {
        System.out.println(linea);
    }

    public static void SaltarLinea(){
        System.out.println();
    };

    public static String LeerCadena() {
        String string;
        Scanner teclado = new Scanner(System.in);
        while (true) {
            Imprimir(" >> ");
            string = teclado.nextLine().trim();
            if (string.length() > 0) {
                teclado.reset();
                return string;
            }
            Imprimir("Entrada no valida, intente de nuevo");
        }
    }

    public static int LeerNumeroIntervalo(int min, int max) {
        int num;
        Scanner teclado = new Scanner(System.in);
        while (true) {
            Imprimir(" >> ");
            num = teclado.nextInt();
            if (num >= min && num <= max) {
                teclado.reset();
                return num;
            }
            Imprimir("Entrada no valida, intente de nuevo");
        }
    }

    public static int LeerNumeroMayorQue(int valor) {
        return LeerNumeroIntervalo(valor, Integer.MAX_VALUE);
    }

    public static int LeerNumeroMenorQue(int valor) {
        return LeerNumeroIntervalo(-Integer.MAX_VALUE, valor);
    }

    public static int LeerNumero() {
        return LeerNumeroIntervalo(0, 0);
    }

    public static void printf(String formato, Object... datos) {
        System.out.printf(formato, datos);
        Imprimir("\n");
    }

}
