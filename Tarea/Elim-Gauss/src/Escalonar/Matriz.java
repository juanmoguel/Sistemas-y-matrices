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
}