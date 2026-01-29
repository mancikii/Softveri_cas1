/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import baza.DBBroker;
import java.util.ArrayList;
import java.util.List;
import model.Autor;
import model.Knjiga;
import model.ZanrKnjige;


/**
 *
 * @author tesic
 */
public class Controller {
    private DBBroker dbb;
    
    private List<Knjiga> listaKnjiga;
    private List<Autor> listaAutora;

   
   
    private static Controller instance;
    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
        }
        
    private Controller(){
        dbb = new DBBroker();
        
        
        
       /* Autor autor1 = new Autor("Ivo", "Andric", 1899, "Biografija blabla");
        Autor autor2 = new Autor("Mesa", "Selimovic", 1920, "Biografija blabla");
        Autor autor3 = new Autor("Danilo", "Kis", 1935, "Biografija blabla");
         
        Knjiga knjiga1 = new Knjiga("Na drini cuprija", autor1, "184556",1985, ZanrKnjige.DETEKTIVSKI);
        Knjiga knjiga2 = new Knjiga("Tvrdjava", autor2, "1234556",1986, ZanrKnjige.ROMAN);
        Knjiga knjiga3 = new Knjiga("Basta, pepeo", autor3, "236456",1975, ZanrKnjige.ROMAN);
        listaKnjiga = new ArrayList<>();
        listaAutora = new ArrayList<>();
        
        listaAutora.add(autor1);
        listaAutora.add(autor2);
        listaAutora.add(autor3);
        
        listaKnjiga.add(knjiga1);
        listaKnjiga.add(knjiga2);
        listaKnjiga.add(knjiga3);*/
        }

    public List<Knjiga> getListaKnjiga() {
        return listaKnjiga;
    }

    public void setListaKnjiga(List<Knjiga> listaKnjiga) {
        this.listaKnjiga = listaKnjiga;
    }

    public List<Autor> getListaAutora() {
        return listaAutora;
    }

    public void setListaAutora(List<Autor> listaAutora) {
        this.listaAutora = listaAutora;
    }

    public void obrisiKnjigu(int id) {
        
       dbb.obrisiKnjigu(id);
        
        //listaKnjiga.remove(selektovaniRed);
    }

    public void dodajKnjigu(Knjiga novaKnjiga) {
        dbb.dodajKnjigu(novaKnjiga);
        //System.out.println("KNJIGA JE DODATA");
        //System.out.println(listaKnjiga);
        
              
    }

    public List<Knjiga> ucitajListuKnjigaIzBaze() {
      return dbb.ucitajListuKnjigaIzBaze();
    }

    public List<Autor> ucitajListuAutoraIzBaze() {
        return dbb.ucitajListuAutoraIzBaze();
    }

    public void azurirajKnjigu(Knjiga knjigaZaIzmenu) {
        dbb.azurirajKnjigu(knjigaZaIzmenu);
    }
         
    
    
}
    
    
    

