/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baza;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Autor;
import model.Knjiga;
import model.ZanrKnjige;

/**
 *
 * @author tesic
 */
public class DBBroker {

    public List<Knjiga> ucitajListuKnjigaIzBaze() {
    List<Knjiga> lista = new ArrayList<>();
        try {

            String upit = "SELECT * FROM KNJIGA K JOIN AUTOR A ON k.autorId = a.id";
            Statement st = Konekcija.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(upit);
            while(rs.next()){
                int id = rs.getInt("k.id");
                String naslov = rs.getString("k.naslov");
                int godIz = rs.getInt("k.godinaIzdanja");
                String ISBN = rs.getString("k.ISBN");
                String zanr = rs.getString("k.zanr");
                ZanrKnjige z = ZanrKnjige.valueOf(zanr);
                int idAutor = rs.getInt("a.id");
                String ime = rs.getString("a.ime");
                String prezime = rs.getString("a.prezime");
                int godR = rs.getInt("a.godinaRodjenja");
                String biografija = rs.getString("a.biografija");
                
                Autor a  = new Autor(idAutor, ime, prezime, godR, biografija);

                Knjiga k = new Knjiga(id, naslov, a, ISBN, godIz, z);
                lista.add(k);
                
                
            }
            
          
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
          return lista;
    }

    public List<Autor> ucitajListuAutoraIzBaze() {
        List<Autor> lista = new ArrayList<>();
        try {

            String upit = "SELECT * FROM autor a";
            Statement st = Konekcija.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(upit);
            while(rs.next()){   
                int idAutor = rs.getInt("a.id");
                String ime = rs.getString("a.ime");
                String prezime = rs.getString("a.prezime");
                int godR = rs.getInt("a.godinaRodjenja");
                String biografija = rs.getString("a.biografija");
                
                Autor a  = new Autor(idAutor, ime, prezime, godR, biografija);

                lista.add(a);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
          return lista;
        
    }

    public void obrisiKnjigu(int id) {
        try {
            String upit = "DELETE FROM KNJIGA WHERE id=?";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
           
            ps.setInt(1, id);
            ps.executeUpdate();
            Konekcija.getInstance().getConnection().commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public void dodajKnjigu(Knjiga novaKnjiga) {
        try {
            String upit = "INSERT INTO knjiga (id, naslov, autorId, ISBN, godinaIzdanja, zanr)"
                    + "VALUES (?,?,?,?,?,?)";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
            ps.setInt(1, novaKnjiga.getId());
            ps.setString(2, novaKnjiga.getNaslov());
            ps.setInt(3, novaKnjiga.getAutor().getId());
            ps.setString(4, novaKnjiga.getISBN());
            ps.setInt(5, novaKnjiga.getGodinaIzdanja());
            ps.setString(6, String.valueOf(novaKnjiga.getZanr()));
            
            ps.executeUpdate();
            Konekcija.getInstance().getConnection().commit();
            
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
       
    }

    public void azurirajKnjigu(Knjiga knjigaZaIzmenu) {
        try {
            String upit = "UPDATE KNJIGA SET naslov=?, autorId=?, godinaIzdanja=?, "
                    + "zanr=? where id=?";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
   
            ps.setString(1, knjigaZaIzmenu.getNaslov());
            ps.setInt(2, knjigaZaIzmenu.getAutor().getId());
            ps.setInt(3, knjigaZaIzmenu.getGodinaIzdanja());
            ps.setString(4, String.valueOf(knjigaZaIzmenu.getZanr()));
            ps.setInt(5, knjigaZaIzmenu.getId());
            
            ps.executeUpdate();
            Konekcija.getInstance().getConnection().commit();
            System.out.println("USPESNO AZURIRANO!");
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    public boolean login(String username, String pass) {
        try {
            String upit = "SELECT * FROM USER WHERE username=? AND password=?";
            PreparedStatement ps = Konekcija.getInstance().getConnection().prepareStatement(upit);
            ps.setString(1, username);
            ps.setString(2, pass);
            
            ResultSet rs = ps.executeQuery();
            
            if(rs.next()){
                return true;
            }else{
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<Knjiga> filtriraj(String autor, String naziv) {
        List<Knjiga> lista = new ArrayList<>();
        try {

            String upit = "SELECT * \n"
                    + "FROM KNJIGA K \n"
                    + "JOIN AUTOR A ON K.AUTORID = A.ID WHERE 1=1";
            
            if(naziv!=null){
                upit+=" AND k.naslov ='"+naziv+"'";
            }
            if(autor!=null){
                String[] podaci = autor.split(" ");
                if(podaci[0]!=null){
                    upit+=" AND a.ime='"+podaci[0]+"'";
                }
                if(podaci.length >=2 && podaci[1]!=null){
                    upit+=" AND a.prezime='"+podaci[1]+"'";
                }
            }
            System.out.println(upit);
            Statement st = Konekcija.getInstance().getConnection().createStatement();
            ResultSet rs = st.executeQuery(upit);
            while(rs.next()){
                int id = rs.getInt("k.id");
                String naslov = rs.getString("k.naslov");
                int godIz = rs.getInt("k.godinaIzdanja");
                String ISBN = rs.getString("k.ISBN");
                String zanr = rs.getString("k.zanr");
                ZanrKnjige z = ZanrKnjige.valueOf(zanr);
                int idAutor = rs.getInt("a.id");
                String ime = rs.getString("a.ime");
                String prezime = rs.getString("a.prezime");
                int godR = rs.getInt("a.godinaRodjenja");
                String biografija = rs.getString("a.biografija");
                
                Autor a  = new Autor(idAutor, ime, prezime, godR, biografija);

                Knjiga k = new Knjiga(id, naslov, a, ISBN, godIz, z);
                lista.add(k);
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(DBBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        
                    return lista;
    }
    
}
