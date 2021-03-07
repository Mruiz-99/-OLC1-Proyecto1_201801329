package Analizadores;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Mruiz
 */
public class Nodo {
    public String valor;
    public Nodo left;
    public Nodo right;
    public String Anulable;
    public String ultimos ;
    public String primeros;
    public LinkedList<Integer> siguientes= new LinkedList<>();
    public String Para;
    public int idHoja;
    public int NoHoja;
    
    public String graficar(){
        if((left == null) && (right==null))
        {   return String.valueOf(valor);
        }else{
            String texto="";
            if(left != null){
                texto = "'"+valor+"' " + "->  '" + left.graficar()+"' \n";
            }
            if(right != null){
                texto += "'"+valor+"' " + "->  '" + right.graficar()+"' \n";
            }
        return texto;
        }
    }
}
