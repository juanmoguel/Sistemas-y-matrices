package Escalonar;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;
import java.io.*;


public class MAIN {
    static Scanner scan = null;
    static int filas ;
    static int columnas ;
    static File archivo;
    static FileOutputStream fos;
    static BufferedWriter bw;
    
    public static void main(String[] args) throws IOException {
        int cont ;
        //se abre al archivo de una carpeta txt dentro la raiz deldisco duro c
        File archivo = new File("C:\\txt\\input2.txt");
         // Se crea el objeto scanner
        try {
        // abre el fichero
        scan = new Scanner(new FileReader(archivo));
        // configura el formato de números
        scan.useLocale(Locale.ENGLISH);
        
        // lee el fichero palabra a palabra
        cont = 0;
        double matriz[][];
        int nummatrices = scan.nextInt();
        scan.nextLine();
           while(cont < nummatrices){
               if(cont == 0){
                   archivo = new File("C:\\txt\\output2.txt");
                   fos = new FileOutputStream(archivo);
                   bw = new BufferedWriter(new OutputStreamWriter(fos));
               }
               matriz = generadorMatrices();
               matriz = funcionGauss(matriz);
               //Funcion para crear el txt de salida
               output(matriz);
               bw.newLine();
               cont++;
            }
           bw.close();
        } catch (FileNotFoundException e) {
            System.out.println("Error abriendo el fichero ");
        } finally {
            if (scan!=null){
                scan.close();
            }
        } // try

    } // main
   
    public static double[][] funcionGauss(double[][] matriz){
        double valor;
        int valor2;
        //Se crea un objeto matriz con los valores de la matriz anteriormente llenada
        Matriz ob = new Matriz(matriz);
        //Se crea otro obj matriz que da una arreglo con el resultado
        Matriz ob2 = ob.gaussJordanElim();
        //El arreglo de la matriz se cambia por los valores de el segundo objeto matriz
            System.out.println("");
        for(int i = 0; i < filas; i++){
            System.out.print("|");
            for(int j = 0; j < columnas; j++){
                matriz[i][j] = ob2.getElemento(i, j);
                if (matriz[i][j] == Math.round(matriz[i][j])) {
                    //System.out.println("Integer");
                    valor = matriz[i][j];
                    valor2 = (int) valor;
                    System.out.print(valor2 + ", ");
                    } else {
                    //System.out.println("Not an integer");
                    System.out.print(matriz[i][j] + ", ");
                    }
                
            }
            System.out.print("|");
            matriz[i][columnas] = ob2.getElemento(i, columnas);
            System.out.print(matriz[i][columnas]);
            System.out.println("|");
        }    
            
        return matriz;
    }
    
    public static double[][] generadorMatrices(){
        System.out.println("");
        filas = scan.nextInt();
            System.out.println("filas : " + filas);
        columnas = scan.nextInt();
            System.out.println("columnas : " + columnas);
        //Crea la matriz 
        double[][] matriz = new double[filas][columnas+1];
        //Inserta la matriz con los valores del txt
        for(int i = 0; i < filas; i++){
            //salta a la siguiente linea del txt
            scan.nextLine();
            System.out.print("|");
            for(int j = 0; j < columnas; j++){
                matriz[i][j] = scan.nextDouble();
                System.out.print(matriz[i][j] + ", ");
            }
            System.out.print("|");
            matriz[i][columnas] = scan.nextDouble();
            System.out.print(matriz[i][columnas]);
            System.out.println("|");
        }
        return matriz;
    }
    
    public static void output(double[][] matriz) {

    try {
        double valor;
        int valor2;
        
        for (int i = 0; i < matriz.length; i++) {
            for (int j = 0; j < matriz[i].length; j++) {
                if (matriz[i][j] == Math.round(matriz[i][j])) {
                    //System.out.println("Integer");
                    valor = matriz[i][j];
                    valor2 = (int) valor;
                    bw.write(valor2+" ");
                    } else {
                    //System.out.println("Not an integer");
                    bw.write(matriz[i][j]+" ");
                    }
            }
            bw.newLine();
        }

    } catch (IOException e) {
        e.printStackTrace();
    }

}
}

