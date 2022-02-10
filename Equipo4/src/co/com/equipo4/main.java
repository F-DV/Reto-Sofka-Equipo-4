package co.com.equipo4;
import java.util.Arrays;

public class main {
    public static void main(String[] args) {

        String[] subrutina={
                "MOV 5,R00",
                "MOV 10,R01",
                "JZ 7",
                "ADD R02,R01",
                "DEC R00",
                "JMP 3",
                "MOV R02,R42"
        };
        Emulador em = new Emulador();

        em.emular(subrutina);
        System.out.println("Registros");
        System.out.println(Arrays.toString(em.registros));
    }
}