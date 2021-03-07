
package olc1.proyecto1;
import Interfaz.Interfaz;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        try {
            Runtime.getRuntime().exec("cmd /c start C:\\Users\\Mruiz\\Documents\\NetBeansProjects\\[OLC1]Proyecto1\\src\\Analizadores\\compilador.bat");
            
        } catch (IOException ex) {
            Logger.getLogger(OLC1Proyecto1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
