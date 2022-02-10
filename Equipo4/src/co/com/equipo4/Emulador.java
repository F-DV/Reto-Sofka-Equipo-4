package co.com.equipo4;

import java.util.Arrays;

import static java.lang.Integer.parseInt;

public class Emulador {
    static long[] registros = new long[43];

    public static void emular(String[] subrutina) {
        if (subrutina.length >= 1 && subrutina.length <= 1024) {
            for (int i = 0; i < subrutina.length; i++) {
                String[] token = subrutina[i].split("[\\s+,]");
                switch (token[0]) {
                    case "MOV":
                        if (token[1].contains("R")) {
                            long valor = registros[parseInt(token[1].substring(1))]; // valor = Rxx
                            registros[parseInt(token[2].substring(1))] = valor;  //Rxx=Ryy
                        } else {
                            //Cuando no existe la letra R en el token 1
                            registros[parseInt(token[2].substring(1))] = Long.parseLong(token[1]);
                        }
                        break;
                    case "ADD":
                        // Rxx + Ryy = Rxx
                        // todo: VERIFICAR SI SI DA 50, SI NO DA 50 REVISAR EL MODULO.
                        long valorRxx = registros[parseInt(token[1].substring(1))];
                        long valorRyy = registros[parseInt(token[2].substring(1))];
                        double multiADD = Math.pow(2, 32);
                        long multi2ADD = new Double(multiADD).longValue();
                        registros[parseInt(token[1].substring(1))] = ((valorRxx + valorRyy) % (multi2ADD));
                        break;
                    case "DEC":
                        if (registros[parseInt(token[1].substring(1))] == 0) {
                            double multiDEC = Math.pow(2, 32);
                            long multi2DEC = new Double(multiDEC).longValue() - 1;
                            registros[parseInt(token[1].substring(1))] = multi2DEC;
                        } else
                            registros[parseInt(token[1].substring(1))] -= 1;
                        break;
                    case "INC":
                        double multiINC = Math.pow(2, 32);
                        long multi2INC = new Double(multiINC).longValue() - 1;
                        if (registros[parseInt(token[1].substring(1))] == multi2INC) {
                            registros[parseInt(token[1].substring(1))] = 0;
                        } else
                            registros[parseInt(token[1].substring(1))] += 1;
                        break;
                    case "INV":
                        //todo : Verificar si esto era lo que teniamos que hacer.
                        long valorINV = registros[parseInt(token[1].substring(1))];
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
                        registros[parseInt(token[1].substring(1))] = Long.parseLong(valorINVinv, 2);
                        break;
                    case "JMP":
                        i = parseInt(token[1]) - 2;
                        break;
                    case "JZ":
                        if (registros[0] == 0) {
                            i = parseInt(token[1]) - 2;
                        }
                        break;
                    case "NOP":
                        break;
                    default:
                        System.out.println("No se reconoce la instrucción.");
                }
            }
        } else {
            System.out.println("Cantidad de instrucciones incorrecta.");
        }
    }

}
