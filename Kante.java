import java.io.Serializable;

/**
 * Beschreiben Sie hier die Klasse Kante.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Kante implements Serializable
{

    // alle Eigenschaften package-Access, statt private:
    // Da diese Klasse Kante nur ein Container für die Daten ist, 
    // und alle Werte auch geändert werden können sollen 
    // von Klassen dieses packages.
    Ecke von;
    Ecke nach;
    double gewicht;
    
    /**
     * Konstruktor für Objekte der Klasse Kante
     */
    public Kante(Ecke von, Ecke nach)
    {
        this(von, nach, 1);
    }
    
    public Kante(Ecke von, Ecke nach, double gewicht)
    {
        this.von = von;
        this.nach = nach;
        this.gewicht = gewicht;
    }


}
