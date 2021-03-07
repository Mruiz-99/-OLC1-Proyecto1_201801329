package Analizadores;

import java.util.LinkedList;
import Analizadores.Nodo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * @author Mruiz
 */
public class Arbol {

    public LinkedList<Nodo> listaAST = new LinkedList<>();
    int contador = 0;
    int cHojas = 0;
    int i = 0;
    String para[];
    int Hoja;
    int siguiente;
    String siguientes[];
    String HojaSig[];
    String comodin = null;
    String nombre = null;
    boolean sig;
    int fila;
    LinkedList<Integer> idAux = new LinkedList<>();
    LinkedList<String> sigAux = new LinkedList<>();
    String texto, texto2;
    String sinComillas;

    /**
     * @return the listaAST
     */
    public LinkedList<Nodo> getListaAST() {
        return listaAST;
    }

    /**
     * @param raizAST
     * @param listaAST the listaAST to set
     */
    public void addAST(Nodo raizAST) {
        listaAST.add(raizAST);
        contador++;
        texto = "";
        texto2 = "";
        cHojas = 0;
        i = 0;
        para = null;
        siguientes = null;
        idAux = null;
        sigAux = null;
        HojaSig = null;
        IdentificandoHojas(raizAST);
        IdentificandoPrimeroUltimo(raizAST);
        IdentificandoSiguientes(raizAST);
        graficar(raizAST, contador);
        graficarTablaSiguientes(raizAST, contador);

    }

    public void graficar(Nodo raiz, int numArbol) {
        FileWriter fichero = null;
        try {
            fichero = new FileWriter("arbolER" + numArbol + ".dot");
            PrintWriter pw = null;
            pw = new PrintWriter(fichero);
            texto2 = "digraph dibujo{ \n node [shape = record, style=filled]; \n";
            texto2 += InOrden(raiz, numArbol);
            texto2 += "\n }";
            pw.println(texto2);
            pw.close();
            try {
                ProcessBuilder proceso;
                proceso = new ProcessBuilder("dot", "-Tpng", "-o", "arbolER" + numArbol + ".jpg", "arbolER" + numArbol + ".dot");
                proceso.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero) {
                    fichero.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }

    }

    public String InOrden(Nodo raiz, int numGrafico) {
        String comodin;
        if (raiz == null) {
            return "";
        } else {
            if (raiz.left != null) {
                sinComillas = raiz.valor.replace("\"", "");
                comodin = sinComillas;
                if ((comodin.equals("|")) || (comodin.equals("\\n"))) {
                    comodin = "\\" + comodin;
                }
                System.out.println(comodin);
                texto += raiz.idHoja + " [label=\" " + raiz.primeros + " |{ " + raiz.Anulable + "| " + comodin + "| Hoja = " + raiz.NoHoja + "}| " + raiz.ultimos + "  \"] \n";
                sinComillas = raiz.left.valor.replace("\"", "");
                comodin = sinComillas;
                if ((comodin.equals("|")) || (comodin.equals("\\n"))) {
                    comodin = "\\" + comodin;
                }
                System.out.println(comodin);
                texto += raiz.left.idHoja + " [label=\" " + raiz.left.primeros + " |{ " + raiz.left.Anulable + "| " + comodin + "| Hoja = " + raiz.left.NoHoja + "}|" + raiz.left.ultimos + "  \"] \n";
                texto += raiz.idHoja + " -> " + raiz.left.idHoja + " \n";
            }
            if (raiz.right != null) {

                sinComillas = raiz.right.valor.replace("\"", "");
                comodin = sinComillas;
                if ((comodin.equals("|")) || (comodin.equals("\\n"))) {
                    comodin = "\\" + comodin;
                }
                System.out.println(comodin);
                texto += raiz.right.idHoja + " [label=\" " + raiz.right.primeros + " |{ " + raiz.right.Anulable + "| " + comodin + "| Hoja = " + raiz.right.NoHoja + "}|" + raiz.right.ultimos + "  \"] \n";
                texto += raiz.idHoja + " -> " + raiz.right.idHoja + " \n";
            }
            InOrden(raiz.left, numGrafico);

            InOrden(raiz.right, numGrafico);

            i++;
        }
        return texto;
    }

    private void IdentificandoHojas(Nodo raiz) {
        if (raiz != null) {
            IdentificandoHojas(raiz.left);
            IdentificandoHojas(raiz.right);
            if ((raiz.left == null) && (raiz.right == null)) {
                cHojas++;
                raiz.NoHoja = cHojas;
                raiz.primeros = "" + cHojas;
                raiz.ultimos = "" + cHojas;
            } else {
                raiz.NoHoja = 0;
            }
        }
    }

    private void IdentificandoSiguientes(Nodo raiz) {
        String comodin;
        if (raiz != null) {
            IdentificandoSiguientes(raiz.left);
            IdentificandoSiguientes(raiz.right);
            sinComillas = raiz.valor.replace("\"", "");
            comodin = sinComillas;
            if (comodin.equals("*")) {
                para = raiz.left.ultimos.split(",");
                siguientes = raiz.left.primeros.split(",");
                for (int i = 0; i < para.length; i++) {
                    Hoja = Integer.parseInt(para[i]);
                    for (int j = 0; j < siguientes.length; j++) {
                        siguiente = Integer.parseInt(siguientes[j]);
                        TablaSiguientes(raiz, Hoja, siguiente);
                    }
                }
            } else if (comodin.equals("+")) {
                para = raiz.left.ultimos.split(",");
                siguientes = raiz.left.primeros.split(",");
                for (int i = 0; i < para.length; i++) {
                    Hoja = Integer.parseInt(para[i]);
                    for (int j = 0; j < siguientes.length; j++) {
                        siguiente = Integer.parseInt(siguientes[j]);
                        TablaSiguientes(raiz, Hoja, siguiente);
                    }
                }
            } else if (comodin.equals(".")) {
                if ((raiz.left != null) && (raiz.right != null)) {
                    para = raiz.left.ultimos.split(",");
                    siguientes = raiz.right.primeros.split(",");
                    for (int i = 0; i < para.length; i++) {
                        Hoja = Integer.parseInt(para[i]);
                        for (int j = 0; j < siguientes.length; j++) {
                            siguiente = Integer.parseInt(siguientes[j]);
                            TablaSiguientes(raiz, Hoja, siguiente);
                        }
                    }
                }
            }
        }
    }

    private void IdentificandoPrimeroUltimo(Nodo raiz) {
        String comodin;
        if (raiz != null) {
            IdentificandoPrimeroUltimo(raiz.left);
            IdentificandoPrimeroUltimo(raiz.right);
            sinComillas = raiz.valor.replace("\"", "");
            comodin = sinComillas;
            if (comodin.equals("*")) {
                raiz.primeros = raiz.left.primeros;
                raiz.ultimos = raiz.left.ultimos;
            } else if (comodin.equals("|")) {
                raiz.primeros = raiz.left.primeros + "," + raiz.right.primeros;
                raiz.ultimos = raiz.left.ultimos + "," + raiz.right.ultimos;
            } else if (comodin.equals("+")) {
                raiz.primeros = raiz.left.primeros;
                raiz.ultimos = raiz.left.ultimos;
            } else if (comodin.equals("?")) {
                raiz.primeros = raiz.left.primeros;
                raiz.ultimos = raiz.left.ultimos;
            } else if (comodin.equals(".")) {
                if ((raiz.left != null) && (raiz.right != null)) {
                    if (raiz.left.Anulable.equals("Anulable")) {
                        raiz.primeros = raiz.left.primeros + "," + raiz.right.primeros;
                    } else {
                        raiz.primeros = raiz.left.primeros;
                    }
                    if (raiz.right.Anulable.equals("Anulable")) {
                        raiz.ultimos = raiz.left.ultimos + "," + raiz.right.ultimos;
                    } else {
                        raiz.ultimos = raiz.right.ultimos;
                    }
                }
            }
        }
    }

    private void TablaSiguientes(Nodo raiz, int numHoja, int siguiente) {
        if (raiz != null) {
            TablaSiguientes(raiz.left, numHoja, siguiente);
            TablaSiguientes(raiz.right, numHoja, siguiente);
            if ((raiz.left == null) && (raiz.right == null) && (raiz.NoHoja == numHoja)) {
                if (raiz.siguientes.isEmpty()) {
                    raiz.siguientes.add(siguiente);
                } else {
                    sig = raiz.siguientes.contains(siguiente);
                    if (sig == false) {
                        raiz.siguientes.add(siguiente);
                    }
                }
            }
        }
    }

    private LinkedList<Integer> ExisteSiguiente(Nodo raiz, int Hoja) {
        if (raiz != null) {
            ExisteSiguiente(raiz.left, Hoja);
            ExisteSiguiente(raiz.right, Hoja);
            if ((raiz.left == null) && (raiz.right == null) && (raiz.NoHoja == Hoja)) {
                idAux = raiz.siguientes;
            }
        }
        return idAux;
    }

    private void graficarTablaSiguientes(Nodo raiz, int numArbol) {
        FileWriter fichero2 = null;
        LinkedList<Integer> sigAux;
        try {
            String aux = "";
            boolean repetido;
            int idHijo = 0;
            fichero2 = new FileWriter("TablaSig" + numArbol + ".dot");
            PrintWriter pw2 = null;
            pw2 = new PrintWriter(fichero2);
            texto2 = "digraph { \n"
                    + "  tbl [ \n"
                    + " shape=plaintext\n"
                    + " label=<\n"
                    + "<table border='0' cellborder='1' cellspacing='0' cellpadding='10'>"
                    + "\n <tr><td>Hoja</td><td>Siguientes</td></tr>";
            for (int k = 1; k <= cHojas; k++) {
                sigAux = ExisteSiguiente(raiz, k);
                if (sigAux != null) {
                    for (int l = 0; l < sigAux.size(); l++) {
                        if (l > 0) {
                            aux += ",";
                        }
                        aux += sigAux.get(l);
                    }
                    texto2 += "\n <tr><td>" + k + ".  " + ValorK(raiz, k) + "</td><td>" + aux + "</td></tr>";
                    aux = "";
                }
            }
            texto2 += "\n</table>\n"
                    + "\n"
                    + "    >];\n"
                    + "\n"
                    + "}";
            pw2.println(texto2);
            pw2.close();
            try {
                ProcessBuilder proceso;
                proceso = new ProcessBuilder("dot", "-Tpng", "-o", "TablaSig" + numArbol + ".jpg", "TablaSig" + numArbol + ".dot");
                proceso.start();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != fichero2) {
                    fichero2.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
    }

    private String ValorK(Nodo raiz, int k) {
        if (raiz != null) {
            ValorK(raiz.left, k);
            ValorK(raiz.right, k);
            if ((raiz.left == null) && (raiz.right == null) && (raiz.NoHoja == k)) {
                nombre = raiz.valor;
            }
        }
        return nombre;
    }
}
