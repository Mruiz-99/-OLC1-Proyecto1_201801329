
package olc1.proyecto1;
import Interfaz.Interfaz;
public class OLC1Proyecto1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Dimensiones del JFRAME de la ventana principal
        Interfaz ventana = new Interfaz();
        ventana.setVisible(true);
        ventana.setTitle("REGEXIVE");
        ventana.setSize(1500, 1000);
        ventana.setLocation(100, 20);
    }
    
}
