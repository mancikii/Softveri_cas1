/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import baza.DBBroker;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import model.Autor;
import model.Knjiga;
import model.User;
import model.ZanrKnjige;


/**
 *
 * @author tesic
 */
public class Controller {
    private DBBroker dbb;
    
    private List<Knjiga> listaKnjiga;
    private List<Autor> listaAutora;
    private List<User> listaUsera = new ArrayList<>();
   
    private static Controller instance;
    public static Controller getInstance(){
        if(instance == null){
            instance = new Controller();
        }
        return instance;
        }
        
    private Controller(){
        dbb = new DBBroker();
        
        //Ovo je u slucaju da se cuva u lokalnoj memoriji
        User u1 = new User(1, "mancikii", "123");
        User u2 = new User(2, "gandziki", "123");
        User u3 = new User(3, "anatesic", "123");
        
        listaUsera.add(u1);
        listaUsera.add(u2);
        listaUsera.add(u3);

        
        
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

    public List<User> getListaUsera() {
        return listaUsera;
    }

    public void setListaUsera(List<User> listaUsera) {
        this.listaUsera = listaUsera;
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
     this.listaKnjiga = dbb.ucitajListuKnjigaIzBaze();
      return this.listaKnjiga;
    }

    public List<Autor> ucitajListuAutoraIzBaze() {
        return dbb.ucitajListuAutoraIzBaze();
    }

    public void azurirajKnjigu(Knjiga knjigaZaIzmenu) {
        dbb.azurirajKnjigu(knjigaZaIzmenu);
    }

    public boolean login1(String username, String pass) {
        for(User u: listaUsera){
            if(u.getUsername().equals(username) && u.getPassword().equals(pass)){
                return true;
            }
        }
        
        return false;
    }

    public boolean login2(String username, String pass) {
        return dbb.login(username, pass);
    }

    public List<Knjiga> filtriraj(String autor, String naziv) {
        List<Knjiga> rezultat = new ArrayList<>();
        if(autor!=null && naziv == null){
            for(Knjiga k: listaKnjiga){
                String autorKnjige = k.getAutor().getIme()+" "+k.getAutor().getPrezime();
                if(autorKnjige.contains(autor)){
                    rezultat.add(k);
                }
            }
        }
        if(autor == null && naziv!=null){
            for(Knjiga k: listaKnjiga){
                if(k.getNaslov().contains(naziv)){
                    rezultat.add(k);
                }
            }
        }
        if(autor!=null && naziv!=null){
            for(Knjiga k: listaKnjiga){
                String autorKnjige = k.getAutor().getIme()+" "+k.getAutor().getPrezime();
                if(autorKnjige.contains(autor) && k.getNaslov().contains(naziv)){
                    rezultat.add(k);
                }
            }
        }
        
//        //Radi istu stvar kao i ovo gore 
//        List<Knjiga> rezultat2 = new ArrayList<>();
//        
//        rezultat2 = listaKnjiga.stream()
//                .filter(k -> (naziv!=null && k.getNaslov().contains(naziv)) &&
//                        (autor!=null && (k.getAutor().getIme()+" "+k.getAutor().getPrezime()).contains(autor)))
//                .collect(Collectors.toList());
        
        
        return rezultat;
    }

    public List<Knjiga> filtriraj2(String autor, String naziv) {
        return dbb.filtriraj(autor, naziv);
    }
         
    
    
}
    
    
    

