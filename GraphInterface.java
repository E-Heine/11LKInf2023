
/**
 * Tragen Sie hier eine Beschreibung des Interface GraphInterface ein.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */

public interface GraphInterface
{
    /**
     * allgemeine Schnittstelle für Verwaltung von Graphen
     */
    public void neueEcke(String label);

    public void loescheEcke(String label);

    public void neueKante(String vonEcke, String zuEcke);

    public void neueKante(String vonEcke, String zuEcke, double gewicht, double rueckgewicht);

    public void loescheKante(String vonEcke, String zuEcke);

    public void zeige();

    public void lade();

    public void speichere();

}
