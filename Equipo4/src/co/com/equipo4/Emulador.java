package co.com.equipo4;

import java.util.Arrays;
/**
 * @Authores:
 *  - Mathías Andrés Collazo Epíscopo
 *  - Juan Esteban Cardona Nieto
 *  - Jose Ignacio Ruiz Corrales
 *  - Veronica Pava Rodriguez
 *  - Daniel Mauricio Naranjo Benavides
 *  - Johan Muñeton Muñeton
 *  - Felipe Quiceno Gomez
 */
import static java.lang.Integer.parseInt;

public class Emulador {
    static long[] registros = new long[43];

    public static String emular(String[] subrutina) {
        int contador = 0;
        if (subrutina.length >= 1 && subrutina.length <= 1024) {
            for (int i = 0; i < subrutina.length; i++) {
                contador += 1;
                if (contador <= 50000) {
                    String[] token = subrutina[i].split("[\\s+,]");
                    switch (token[0]) {

                        case "MOV":
                            metodoMOV(token[1], token[2]);
                            break;
                        case "ADD":
                            metodoADD(token[1], token[2]);
                            break;
                        case "DEC":
                            metodoDEC(token[1]);
                            break;
                        case "INC":
                            metodoINC(token[1]);
                            break;
                        case "INV":
                            metodoINV(token[1]);
                            break;
                        case "JMP":
                            i = metodoJMP(token[1]);
                            break;
                        case "JZ":
                            i = metodoJZ(token[1], i);
                            break;
                        /**El método NOP, no hace nada y le hace break al ciclo.
                         * @   */
                        case "NOP":
                            break;
                        default:
                            System.out.println("No se reconoce la instrucción.");
                    }
                } else {
                    System.out.println("Se excedió el número de instrucciones (50000).");
                }
            }
        } else {
            System.out.println("Cantidad de instrucciones incorrecta, deben ser (1-1024).");
        }
        return "El resultado del R42 es: " + registros[42] + ".";
    }


    /**
     * El método MOV copia el valor de Rxx en el registro Ryy a partir de param1 y param2.
     *
     * @param param1 Parametro 1 : Rxx
     * @param param2 Parametro 2 : Ryy
     */
    public static void metodoMOV(String param1, String param2) {
        if (param1.contains("R")) {
            long valor = registros[parseInt(param1.substring(1))]; // valor = Rxx
            registros[parseInt(param2.substring(1))] = valor;  //Ryy = Rxx
        } else {
            //Cuando no existe la letra R en el token 1
            registros[parseInt(param2.substring(1))] = Long.parseLong(param1);
        }
    }

    /**
     * El método ADD calcula la suma de los parámetros Rxx y Ryy y luego aplica el módulo a dicho número.
     * Resultantes, este valor es finalmente almacenando en Rxx.
     *
     * @param param1 Parametro 1 : Rxx
     * @param param2 Parametro 2 : Ryy
     */
    public static void metodoADD(String param1, String param2) {
        long valorRxx = registros[parseInt(param1.substring(1))];
        long valorRyy = registros[parseInt(param2.substring(1))];
        double multiADD = Math.pow(2, 32);
        long multi2ADD = new Double(multiADD).longValue();
        registros[parseInt(param1.substring(1))] = ((valorRxx + valorRyy) % (multi2ADD));
    }

    /**
     * El método DEC disminuye el valor del parámetro Rxx en 1
     * se genera desbordamiento y el resultado es 0
     *
     * @param param1 Parametro 1 : Rxx
     */
    public static void metodoDEC(String param1) {
        if (registros[parseInt(param1.substring(1))] == 0) {
            double multiDEC = Math.pow(2, 32);
            long multi2DEC = new Double(multiDEC).longValue() - 1;
            registros[parseInt(param1.substring(1))] = multi2DEC;
        } else
            registros[parseInt(param1.substring(1))] -= 1;
    }

    /**
     * El método INC aumenta el valor del parámetro Rxx en 1
     *
     * @param param1 Parametro 1 : Rxx
     */
    public static void metodoINC(String param1) {
        double multiINC = Math.pow(2, 32);
        long multi2INC = new Double(multiINC).longValue() - 1;
        if (registros[parseInt(param1.substring(1))] == multi2INC) {
            registros[parseInt(param1.substring(1))] = 0;
        } else
            registros[parseInt(param1.substring(1))] += 1;
    }

    /**
     * Método INV invertirá el valor del parámetro Rxx.
     *
     * @param param1 Parametro 1 : Rxx
     */
    public static void metodoINV(String param1) {
        long valorINV = registros[parseInt(param1.substring(1))];
        String valorINVenBytes = Long.toBinaryString(valorINV);
        StringBuilder sb = new StringBuilder(valorINVenBytes);
        for (int j = 0; j < sb.length(); j++) {
            if (sb.charAt(j) == '1') {
                sb.setCharAt(j, '0');
            } else {
                sb.setCharAt(j, '1');
            }
        }
        String valorINVinv = sb.toString();
        registros[parseInt(param1.substring(1))] = Long.parseLong(valorINVinv, 2);
    }

    /**
     * Método JMP saltará al parámetro indicado d, garantizando que esta instrucción es válida.
     *
     * @param param1 Parametro 1 : Rxx
     */
    public static int metodoJMP(String param1) {
        int ret = parseInt(param1) - 2;
        return ret;
    }

    /**
     * Metodo JZ saltará al parámetro si el valor del registro Rxx es 0
     *
     * @param param1 Parametro 1 : Rxx
     * @param index  Indice de la instrucción
     * @return Indice de la instrucción
     */
    public static int metodoJZ(String param1, int index) {
        if (registros[0] == 0) {
            int ret = parseInt(param1) - 2;
            return ret;
        }
        return index;
    }
}
