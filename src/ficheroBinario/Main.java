/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ficheroBinario;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Scanner;

/**
 *
 * @author Morad
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Morad
 */
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int precio, edad, cont = 0;
        String Dni, matricula;
        double seguro;

        System.out.println("Introduce precio");
        precio = sc.nextInt();
        File f = new File("seguros.bin");
        File f1 = new File("segurosActualizados.bin");

        try { //leemos el fichero binario
            FileInputStream fis = new FileInputStream(f);
            DataInputStream dis = new DataInputStream(fis);

            try {
                while (true) {

                    Dni = dis.readUTF();
                    edad = dis.readInt();
                    matricula = dis.readUTF();
                    seguro = dis.readDouble();

                    System.out.println("Dni: " + Dni + " Edad: " + edad + " Matricula: " + matricula + " Seguro: " + seguro);

                }

            } catch (EOFException ef) {
                System.out.println("fin del fichero");
            }

            dis.close();
            fis.close();

            fis = new FileInputStream(f1);
            dis = new DataInputStream(fis);
            //escribimos
            FileOutputStream fos = new FileOutputStream(f1);
            DataOutputStream dos = new DataOutputStream(fos);

            try {

                while (true) {
                    Dni = dis.readUTF();
                    edad = dis.readInt();
                    matricula = dis.readUTF();
                    seguro = dis.readDouble();

                    if (seguro == 0) {
                        if (edad < 40) {
                            seguro = precio * 0.4;
                        } else {
                            seguro = precio * 0.1;
                        }
                    } else if (seguro < 500) {
                        seguro = seguro * 1.05;
                    }

                    if (seguro < 1000) {
                        dos.writeUTF(Dni);
                        dos.writeInt(edad);
                        dos.writeUTF(matricula);
                        dos.writeDouble(seguro);
                    } else {
                        cont++;
                    }

                }

            } catch (EOFException e) {
            }

            dos.close();
            fos.close();
            dis.close();
            fis.close();
            f.delete();
            f1.renameTo(f);

            fis = new FileInputStream(f);
            dis = new DataInputStream(fis);

            System.out.println("-----------------------------------------------------------");

            try {
                while (true) {
                    Dni = dis.readUTF();
                    edad = dis.readInt();
                    matricula = dis.readUTF();
                    seguro = dis.readDouble();

                    System.out.println("Dni: " + Dni + " Edad: " + edad + " Matricula: " + matricula + " Seguro: " + seguro);

                }

            } catch (EOFException ef) {
                System.out.println("fin del fichero");
            }

            dis.close();
            fis.close();
            System.out.println("Se han dado de baja " + cont + " clientes");

        } catch (IOException e) {
        }

    }
}
