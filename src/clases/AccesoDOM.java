/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package clases;

import java.io.File;
import java.io.IOException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author anrod
 */
public class AccesoDOM {
    private Document miDocumento;
    
    /**
     * Mátodo que obtiene un objeto Document y lo asigna al atributo de la clase
     * @param archivo Objeto FIle represetnativo del archivo XML a parsear
     * @return 1 todo correcto; -1 error al crear el DOM
     */
    public int abrirXMLaDom (File archivo){
       int retorno=-1;
        try {
            System.out.println("Abriendo el archivo XTMl y generando el DOM ....");
            
            // se crea un DocumentBuilderFactory
            DocumentBuilderFactory fabricaDeDocumentos = DocumentBuilderFactory.newInstance();
            // se configura para ignore comentarios y espacios en blanco
            fabricaDeDocumentos.setIgnoringComments(true);
            fabricaDeDocumentos.setIgnoringElementContentWhitespace(true);
            
            // Creamos un constructor de documentos con la fábrica.
            
            DocumentBuilder constructorDeDocumentos = fabricaDeDocumentos.newDocumentBuilder();
            miDocumento = constructorDeDocumentos.parse(archivo);
            retorno=0;              
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            System.out.println(ex); 
        }       
        return retorno;
    }
    
    
    /**
     * Meñtodo que recorre y muestra en pantalla el atrbuto de la clase Document, 
     * previamente debe haber sido generado con el método abrirXMLaDom. 
     */
    public void recorreDOMyMuestra(){
        System.out.println(miDocumento.getFirstChild().getNodeName()); // obtengo el nombre de la raiz y lo imprimo
            // obtengo una lista con todos los nodos hijos de la raiz (En este caso los "Libro")
        NodeList nodosHijos = miDocumento.getFirstChild().getChildNodes();
            // Llamoal mñetodo recursivo con la lista y el numero de nivel en elque estoy para las tabulaciones
        recorreElementosyMuestra(nodosHijos,0);
    }
    
    /**
     * metodo recursivo que recorre un nodelist y muestra por pantalla el nivel actual y se llama a si mismo 
     * para mostrar el nivel inferior.
     * @param nodosHijos NodeList
     * @param nivel entero que indica el nivel del elemento para gestionar las tabulaciones al mostrar
     * @return true -> si en el nivel actual hay nodos hijos que sean elementos; false en caso contrario.
     */
    private boolean recorreElementosyMuestra(NodeList  nodosHijos, int nivel){
            // Boolean que me va a ayudar a saber si en esta iteración de mostrar un nodo hay elementos
        boolean tieneHijosElementos=false;
            // en cada iteracion auemtno el nivel para las tabulaciones.
        nivel++;
            // recorro todos los nodos de la lsista de nodos.
        for (int i = 0; i < nodosHijos.getLength(); i++) {  
            // para cada nodo si es un elemento hago cosas
            if(nodosHijos.item(i).getNodeType() == Node.ELEMENT_NODE){
                // aqui guardo si sus hijos tienen elemetnos, esto es solo para saber si tengo que mostrar el valor (nota1)
                tieneHijosElementos = true;
                //Muestro el nombre del elemento tabulado.
                System.out.print(tabs(nivel)+nodosHijos.item(i).getNodeName()); 
                // Compruebo si tiene atributos, y si los tiene llamo al mñetodo que  los recorre y muestra.
                if(nodosHijos.item(i).hasAttributes()){
                    recorreAtributosyMuestra(nodosHijos.item(i));
                }
                // Obtengo una lista  con los nodos hijos del nodo actual del bucle.
                // Llamo al metodo que me indica si sus hijos son elementos, si son elementos salto la linea pues en el que estoy no tiene valor
                if(hijosSonElementos(nodosHijos.item(i).getChildNodes())){
                   System.out.println(""); 
                }                   
            }
            
           // commpruebo si el nodo en el que estoy del bucle for tiene hijos.
            if(nodosHijos.item(i).hasChildNodes()){
                // si los tiene (siempre tienen algo) los recorro con recursividad y muestro y me informo de si son Element
                if ( !recorreElementosyMuestra(nodosHijos.item(i).getChildNodes(),nivel) ){
                    // si sus hijos no son elementos entonces imprimo su valor y salto linea.
                    System.out.print(" :  "+nodosHijos.item(i).getTextContent()+"\n");              
                }else{
                    // si sus hijos son elementos se habrán mostrado en la recursividad y aqui solo necesito saltar linea para mantener el formato.
                    System.out.println("");
                }              
            }
                
        }      
        return tieneHijosElementos;
    }
    // Nota 1 -->  eso es porque si muestro el  valor o textcontent de un nodo, si itene hijos me muestra el suyo (que no tiene al tener hijos)
    // pero tambien el de  todos sus hijos <-- esto con dom4j no pasa
    
    /**
     * Mñetodo que revcorre los atributos de un nodo y losmuiestra en pantalla en la misma línea.
     * @param nodo nodo del que se quieren  mostrar los atributos.
     */
    private void recorreAtributosyMuestra(Node nodo){
        NamedNodeMap atributos= nodo.getAttributes();       
        for (int i = 0; i < atributos.getLength(); i++) {
            System.out.print("  "+atributos.item(i).getNodeName()+" = "+
                    atributos.item(i).getNodeValue());
        } 
    }
    
    /**
     * metodo que recorre una lista de nodos para saber si alguno es elemmento o no.
     * @param nodosHijos NodeList
     * @return true si alguno de los nodos hijos es un Element, false en caso contrario.
     */
    private boolean hijosSonElementos(NodeList  nodosHijos){
        boolean tieneHijosElementos=false;
        for (int i = 0; i < nodosHijos.getLength(); i++) {           
            if(nodosHijos.item(i).getNodeType() == Node.ELEMENT_NODE)
                tieneHijosElementos = true;                    
        }
        return tieneHijosElementos;
    }
    
    /**
     * Método que devuelve un String con tabulaciones en función del entero facilitado. 
     * @param nivel int -> contaidad de tabulaciones que se quiere devolver
     * @return String que solo contiene tabulaciones.
     */
    private String tabs(int nivel){
        String tabulacion="";
        for (int i = 0; i < nivel; i++) {
            tabulacion+="\t";
        }
        return tabulacion;
    }
    
    /**
     * Método que inserta un Libro en el Document de la clase. Funcionará en un esquema XML:
     * <Libro publicado=valor>
     *      <Titulo>valor</Titulo>
     *      <Autor>valor</Autor>
     * </Libro>
     * @param author valor de <Titulo>
     * @param title valor de <Autor>
     * @param id valor de publicado
     * @return 0 operación correcta, -1 error.
     */
    public int insertarLibroEnDOM(String author, String title, String id, String genre,
            String price,String publish_date, String description){
        int correcto = 0;
        
        try{
            System.out.println("Añadir libro al arbol DOM: "+author+";"+title+";"+id+" ...");
            //crea los nodos=>los añade al padre desde las hojas a la raíz
                    //CREATE TITULO con el texto en medio
            Node nAuthor = miDocumento.createElement("author"); // crea atiquetas.
            Node nAuthor_Text=miDocumento.createTextNode(author); // asigna el author
            nAuthor.appendChild(nAuthor_Text); // añade el texto del author al nodo author.

            Node nTitle = miDocumento.createElement("title"); // crea atiquetas.
            Node nTitle_text=miDocumento.createTextNode(title); // asigna el title
            nTitle.appendChild(nTitle_text); // añade el texto del title al nodo title.
            
            Node nGenre = miDocumento.createElement("genre"); // crea atiquetas.
            Node nGenre_Text=miDocumento.createTextNode(genre); // asigna el genero
            nGenre.appendChild(nGenre_Text); // añade el texto del title al nodo genero.
            
            Node nPrice = miDocumento.createElement("price"); // crea atiquetas.
            Node nPrice_Text=miDocumento.createTextNode(price); // asigna el precio
            nPrice.appendChild(nPrice_Text); // añade el texto del title al nodo precio.
            
            Node nDate = miDocumento.createElement("publish_date"); // crea atiquetas.
            Node nDate_Text=miDocumento.createTextNode(publish_date); // asigna el fecha pub
            nDate.appendChild(nDate_Text); // añade el texto del title al nodo fecha pub.
            
            Node nDescripcion = miDocumento.createElement("description"); // crea atiquetas.
            Node nDescripcion_Text=miDocumento.createTextNode(description); // asigna el fecha pub
            nDescripcion.appendChild(nDescripcion_Text); // añade el texto del title al nodo fecha pub.

            Node nBook = miDocumento.createElement("book"); // creo el padre Libro
            ((Element)nBook).setAttribute("id", id); // creo el atributo y lo pongo en Libro
            
            nBook.appendChild(nAuthor);
            nBook.appendChild(nTitle);
            nBook.appendChild(nGenre);
            nBook.appendChild(nPrice); // Asigno como hijos los nodos author y title
            nBook.appendChild(nDate);
            nBook.appendChild(nDescripcion);

            nBook.appendChild(miDocumento.createTextNode("\n")); // inserta salto de linea

            Node raiz  = miDocumento.getFirstChild();  // cojo la raiz --> miDocumento.getChildNodes().item(0).
            raiz.appendChild(nBook);  // añado el Libro a la raiz.
            System.out.println("... Libro insertado en el DOM");         
        }catch (DOMException ex){
            System.out.println("... Error en la creación del nodo Hijo.");
            correcto= -1;       
        }     
        return correcto;
    }
    
    
        public int deleteNode(String tit){
        System.out.println("Buscando el libro "+tit+"para borrarlo");
        
        try{
            //Node root)=doc.getFirstChil();
            Node raiz= miDocumento.getDocumentElement();
            NodeList nl1=miDocumento.getElementsByTagName("title");
            Node n1;
            
            for (int i = 0;i<nl1.getLength(); i++) {
                n1=nl1.item(i);
                
                if(n1.getNodeType()==Node.ELEMENT_NODE){//redundante por getelemetsbt tagname, no lo es si usamos getchild notes
                    if(n1.getChildNodes().item(0).getNodeValue().equals(tit)){
                        System.out.println("Borrando el nodo <libro> con titulo"+tit);
                        //n1,getParentNode().removeChild(n1);//borra <titulo> tit</titulo> , pero deja libro y autor
                        n1.getParentNode().getParentNode().removeChild(n1.getParentNode());                      
                    }                    
                }
            }
            
            System.out.println("Nodo borrado");
            
            //guardar el arbol DOM en un nuevo arcghivio para mantener nuestro archivo orignal 
            // guardar DOMcomo Archivo("libros borrados .xml);
            return 0;
        }catch(Exception e){
            System.out.println(e);
            e.printStackTrace();
            return -1;
        }
        
    }
    
}
