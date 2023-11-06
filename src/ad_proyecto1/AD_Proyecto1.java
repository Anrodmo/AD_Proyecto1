/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package ad_proyecto1;

import clases.AccesoDOM;
import java.io.File;
import org.w3c.dom.Document;

/**
 *
 * @author anrod
 */
public class AD_Proyecto1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String ruta = "./src/ad_proyecto1/Libros.xml";
        AccesoDOM acceso = new AccesoDOM();
        
        if(ruta==null)ruta=""; // Evito la PointNullException al crear File
        File archivo = new File(ruta);
        
        acceso.abrirXMLaDom(archivo);              
        acceso.recorreDOMyMuestra();
        
        acceso.insertarLibroEnDOM("El fari", "las aventuras", "01", "aventuras",
                "50", "01/01/1990", "las aventuras de un taxista");
        
        
        acceso.recorreDOMyMuestra();
        
    }
    
}
