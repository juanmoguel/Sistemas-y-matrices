package Escalonar;

public class Matriz
{
    // Array de arrays de reales que almacenara los elementos de la Matriz. 
    private double[][] elementos;
    // Dimensiones de la matriz (numero de filas y de columnas)
    private int nFilas, nCols;

    /**
     * Crea una matriz a partir de sus elementos.
     */
    public Matriz(double[][] elementos)
    {
        nFilas = elementos.length;
        nCols = elementos[0].length;
        this.elementos = new double[nFilas][nCols];
         for(int i=0; i<nFilas; i++) {
             for(int j=0; j<nCols; j++) {
                 this.elementos[i][j] = elementos[i][j];
             }
         }
    }

    /**
     * Crea una Matriz rectangular rellena de ceros, dadas sus dimensiones.
     */
    public Matriz(int nFilas, int nCols) {
        this.nFilas = nFilas;
        this.nCols = nCols;
        elementos = new double[nFilas][nCols];
        //Todos se inicializara a 0.0
    }
    
    /**
     * Metodo accesor (getter). Obtiene un elemento de la matriz dadas sus coordenadas.
     */
    public double getElemento(int i, int j) {
        return elementos[i][j];
    }

    
    
    /** 
     * Operación de multiplicación de una fila por un número real.
     * Crea una nueva matriz, resultado de multiplicar una fila por un número dado.
     * @param i1 Fila que se multiplicará
     * @param k Número por el que se multiplicará la fila
     * @return Matriz igual a la original en todos sus elementos, salvo que los elementos de la
     * fila i1 aparecen multiplicados por la constante.
     */
    public Matriz multiplicaFila(int i1, double k) {
        Matriz resultado = new Matriz(nFilas, nCols);
        for (int i=0; i<nFilas; i++) {
            for (int j=0; j<nCols; j++) {
                if (i == i1) {
                    resultado.elementos[i1][j] = k * this.elementos[i][j]; //Los elementos de la fila i1 la multiplicamos por k
                } else {
                    resultado.elementos[i][j] = this.elementos[i][j]; // Los demás elementos los dejamos tal cual
                }
            }
        }
        return resultado;
    }
    
    /**
     * Operación de suma de una fila a otra.
     * Crea una nueva matriz, resultado de sumar una fila por un número real dado a otra fila.
     * @param i1 Fila que se modificará
     * @param i2 Fila que se sumará a i1
     * @param k Factor por el que se multiplica la fila i2 para sumarla a i1
     * @return Matriz igual a la original en todos sus elementos, salvo que los elementos de la fila i1
     * aparecen sumados con k veces el elemento respectivo de la fila i2.
     */
    public Matriz anadeFila(int i1, int i2, double k) {
        Matriz resultado = new Matriz(nFilas, nCols);
        for (int i=0; i<nFilas; i++) {
            for (int j=0; j<nCols; j++) {
                if (i == i1) {
                    // A los elementos de la fila i1 le sumamos k veces el elemento correspondiente de la fila i2
                    resultado.elementos[i1][j] = this.elementos[i][j] + k * this.elementos[i2][j]; 
                } else {
                    resultado.elementos[i][j] = this.elementos[i][j]; // Los demás elementos los dejamos tal cual
                }
            }
        }        
        return resultado;        
    }
        
    /**
     * Crea una matriz triangular superior a partir de la dada, a base de operaciones
     * elementales de fila utilizando el Método de Eliminación de Gauss.
     * Una matriz triangular superior es aquella en la que todos los elementos 
     * por debajo de la diagonal principal son iguales a cero.
     * Para crear la matriz triangular superior, se seguirá el método de Gauss ():
     * <ol>
     * <li>Se recorre la matriz por columnas.</li>
     * <li>Para cada columna j, se colocan ceros por debajo de la diagonal principal. Para ello:
     *      <ol>
     *      <li>Se recorre la columna por filas.</li>
     *      <li>Para cada fila i mayor que j (es decir, para cada elemento mij por debajo de la diagonal principal),
     *      se añade la fila j (es decir, la misma que la columna en la que nos encontremos) a la fila i, 
     *      multiplicada por –m<sub>ij</sub>/m<sub>jj</sub>. De este modo, conseguimos hacer 0 el elemento m<sub>ij</sub>.
     *      </li>
     *      </ol>
     * </li>
     * </ol>
     * @return Matriz triangular superior (con ceros por debajo de la diagonal principal) resultado
     * de llevar a cabo operaciones de fila a partir de la matriz dada.
     */
    public Matriz gaussElim() {
        Matriz resultado = this; // Empezamos con la matriz actual como resultado; 
        // no hay problema porque las operaciones de fila no modifican la original sino que devuelven matrices nuevas
        for (int j=0; j<nCols; j++) {
            for (int i=j+1; i<nFilas; i++) {
                resultado = resultado.anadeFila(i, j, -resultado.elementos[i][j] / resultado.elementos[j][j] );
            }
        }
        return resultado;     
    }
    
    /**
     * Crea una matriz en forma de filas en escalón reducida (RREF) a partir de la dada, 
     * a base de operaciones elementales de fila,
     * utilizando el Método de Eliminación de Gauss-Jordan.
     * Una matriz está en forma RREF si:
     * <ul>
     * <li>Todas las filas no nulas están por encima de las filas todo ceros</li>
     * <li>El coeficiente dominante de una fila 
     * (es decir, el primer elemento no nulo, también llamado pivote) está 
     * siempre a la derecha del coeficiente dominante de la fila superior.</li>
     * <li>Todo coeficiente dominante es igual a 1 y además es el único elemento no nulo de su columna.</li>
     * </ul>
     * El Método de Eliminación de Gauss-Jordan comienza aplicando el Método de Eliminación de Gauss.
     * A partir de ahí, se multiplica cada fila por el inverso del pivote para hacerla igual a la unidad. 
     * A continuación, para cada columna j se toma el último elemento i no nulo, 
     * y se hacen ceros todos los elementos por encima de él, sumando la fila del pivote a las demás filas,
     * multiplicándolas por la constante necesaria para anular el elemento 
     * (de forma semejante al Método de Eliminación de Gauss).
     */
    public Matriz gaussJordanElim() {
        Matriz resultado = this.gaussElim(); // Empezamos con la matriz triangular superior como resultado
        for (int i=0; i<nFilas; i++) {
            resultado = resultado.multiplicaFila(i, 1.0/resultado.elementos[i][i]); // Hacemos los pivotes igual a la unidad
        }
        for (int j=0; j<nFilas; j++) {
            for (int i=0; i<j; i++) {
                  resultado = resultado.anadeFila(i, j, -resultado.elementos[i][j] / resultado.elementos[j][j] ); // Anulamos los elementos por encima de los pivotes (i<j)
            }
        }        
        return resultado;
    }
   
}    