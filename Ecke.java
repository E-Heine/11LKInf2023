import java.util.*;
import java.io.Serializable;

/**
 * Beschreiben Sie hier die Klasse Ecke.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Ecke implements Serializable
{ 
    
    // Die Eigenschaften label, x und y dürfen von alle Klassen 
    // dieses package direkt geändert werden (keine getter und setter nötig).
    String label;
    int x;
    int y;

    // auf dieser Liste soll nur mit den Methoden gearbeitet werden können.
    private List<Kante> kanten;

    /**
     * Konstruktor für Objekte der Klasse Ecke
     */
    public Ecke(String label)
    {
        this(label, 0, 0);
    }

    public Ecke(String label, int x, int y)
    {
        this.label = label;
        this.x = x;
        this.y = y;
        kanten = new ArrayList();
    }

    public void addKante(Ecke nach, double gewicht){
        addKante(new Kante(this, nach, gewicht));
    }
    
    public void addKante(Kante k){
        kanten.add(k);
    }
    
    public void removeKante(Kante k){
        kanten.remove(k);
    }
    
    /*
     * Lesezugriff auf alle Kantenobjekte
     * 
     * @return ein Array aller Kantenobjekte - diese sind änderbar!
     */
    public Kante[] getKanten() {
        
        // geht leider nicht:
        // return (Kante[]) kanten.toArray(); // => Wer erklärt mir dieses Problem?
        
        // Der funktionierende Umweg, jedes Objekt einzeln casten:
        Object[] o = kanten.toArray();
        Kante[] k = new Kante[o.length];
        for (int i=0; i<o.length; i++) {
            k[i] = (Kante)o[i];
        }
        return k;
    }
    

}
