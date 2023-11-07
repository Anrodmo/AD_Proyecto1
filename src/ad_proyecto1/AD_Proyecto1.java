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
        String ruta2 = "./src/ad_proyecto1/Libros2.xml";
        AccesoDOM acceso = new AccesoDOM();
        
        if(ruta==null)ruta=""; // Evito la PointNullException al crear File
        File archivo = new File(ruta);
        
        
        System.out.println("\n ================Abrimos XML y mostramos===========================\n");
        acceso.abrirXMLaDom(archivo);              
        acceso.recorreDOMyMuestra();
        
        System.out.println("\n================Creamos libro y mostramos --> El fari  ================= \n");
        acceso.insertarLibroEnDOM("El fari", "las aventuras", "aventuras",
                "50", "01/01/1990", "las aventuras de un taxista");
        acceso.recorreDOMyMuestra();
        System.out.println("\n ================Borramos Paradox Lost y mostramos======================\n");
        
        acceso.deleteNode("Paradox Lost");
        acceso.recorreDOMyMuestra();
        
        System.out.println("\n ================ Guardamos en un nuevo archivo.======================\n");
        acceso.guardarDomcomoArchivo(ruta2);
        
        
    }
    
}
