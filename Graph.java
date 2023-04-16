import java.util.*;
import java.io.*;

/**
 * Beschreiben Sie hier die Klasse Graph.
 * 
 * @author (Ihr Name) 
 * @version (eine Versionsnummer oder ein Datum)
 */
public class Graph implements GraphInterface
{

    
    
    /*
     * Einfache Start- und Testmethode für die Klasse Graph: erzeugen, hinzufügen,
     * etfernen, ausgeben, laden, speichern
     * 
     */
    public static void main() {
        Graph g = new Graph();
        /*
        g.neueEcke("Mainz");
        g.neueEcke("Wiesbaden");
        g.neueEcke("Oppenheim");
        g.neueEcke("Ingelheim");
        g.neueKante("Mainz", "Wiesbaden", 12, 12);
        g.neueKante("Mainz", "Oppenheim", 22, 22);
        g.neueKante("Mainz", "Ingelheim", 17, 17);
        */
        g.lade();
        g.zeige();
        //g.speichere();

        g.exportiereCSV();
        //g.importiereCSV(); // fehlt noch

    }

    /*
     * Datenstruktur für die Ecken
     * Die Kanten werden jeweils bei jeder Ecke als Liste gespeichert 
     */
    private Map <String, Ecke> ecken;

    /**
     * Konstruktor für Objekte der Klasse Graph
     */
    public Graph()
    {
        ecken = new HashMap();
    }

    /*
     * Gibt ein Eckenobjekt zurück
     * 
     * @param label die Bezeichnung der Ecke, als String
     * @return die Ecke, oder null, wenn die Ecke mit der Bezeichnung nicht existiert.
     */
    public Ecke getEcke(String label){
        return ecken.get(label);
    }

    /*
     * gibt alle Ecken des Graphen als Array zurück
     * 
     * @return ein Array von Ecken
     */
    public Ecke[] getEcken(){
        //return (Ecke[]) ecken.values().toArray();

        // Der funktionierende Umweg, jedes Objekt einzeln casten:
        Object[] o = ecken.values().toArray();
        Ecke[] e = new Ecke[o.length];
        for (int i=0; i<o.length; i++) {
            e[i] = (Ecke)o[i];
        }
        return e;

    }

    /**
     * 
     * Erzeigt eine neue Ecke
     * @param  label Bezeichnung der Ecke
     * @return die Ecke
     */
    public void neueEcke(String label)
    {
        Ecke e = new Ecke(label);
        ecken.put(label, e);
    }

    /**
     * 
     * Entfernt eine Ecke (aber OHNE die Ecke aus der Kantenliste zu entfernen!)
     * 
     * @param  label die Bezeichnung der Ecke
     */
    public void loescheEcke(String label)
    {
        Ecke e = ecken.get(label);
        if (e!=null) ecken.remove(e);

        // TODO: diese Ecke auch aus allen fremden Kanten entfernen!

    }

    /**
     * 
     */
    public void neueKante(String vonEcke, String zuEcke){
        Ecke von = ecken.get(vonEcke);
        Ecke nach = ecken.get(zuEcke);
        if (von!=null && nach!=null) {
            von.addKante(nach, 1);
        }
    }

    /**
     * 
     */
    public void neueKante(String vonEcke, String zuEcke, double gewicht, double rueckgewicht)
    {
        Ecke von = ecken.get(vonEcke);
        Ecke nach = ecken.get(zuEcke);
        if (von!=null && nach!=null) {
            von.addKante(nach, gewicht);
            nach.addKante(von, rueckgewicht);            
        }
    }

    /**
     * 
     */
    public void loescheKante(String vonEcke, String zuEcke){
        Ecke von = ecken.get(vonEcke);
        Ecke nach = ecken.get(zuEcke);
        if (von!=null && nach!=null) {
            Kante[] k = von.getKanten();
            for (int i=0; i<k.length; i++) {
                if (k[i].nach.label.equals(zuEcke)) {
                    von.removeKante(k[i]);
                }
            }
        }
    }

    /**
     * 
     */
    public void zeige(){
        System.out.println("Ecken");
        Ecke[] e = getEcken();
        for (int i=0; i<e.length; i++) {
            System.out.print(e[i].label+" ("+e[i].x+", "+e[i].y+") ");
        }
        System.out.println();
        System.out.println();

        System.out.println("Adjazenzliste");
        for (int i=0; i<e.length; i++) {
            System.out.print(e[i].label+": ");
            Kante[] k = e[i].getKanten();
            for (int j=0; j<k.length; j++) {
                System.out.print(k[j].nach.label+" ("+k[j].gewicht+"), ");
            }
            System.out.println();
        }

    }



    /**
     * 
     */
    public void lade(){
        // https://mkyong.com/java/how-to-read-and-write-java-object-to-a-file/
        try {
            FileInputStream fis = new FileInputStream(new File("graph.obj"));
            ObjectInputStream ois = new ObjectInputStream(fis);            
            // der OIS ist "um den FIS herum" gewrappt. Stelle Dir Rohre vor,
            // die ineinander geschoben werden - so geht das mit IO-Streams.

            ecken = (HashMap<String, Ecke>) ois.readObject();
            fis.close();
        } catch (Exception e) {
            System.err.println(e);
        }

    }

    /**
     * 
     */
    public void speichere(){
        try {
            FileOutputStream fos = new FileOutputStream(new File("graph.obj"));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(ecken);
            fos.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void exportiereCSV(){

        try {
            PrintStream ps = new PrintStream(new FileOutputStream("export.csv"));

            Ecke[] e = getEcken();
            ps.println(e.length); // header: Anzahl Ecken, der Rest sind Kanten
            ps.println("---"); // optische Trennnung, muss überlesen werden
            for (int i=0; i<e.length; i++) {
                ps.println(e[i].label+" "+e[i].x+" "+e[i].y);
            }

            for (int i=0; i<e.length; i++) {
                Kante[] k = e[i].getKanten();
                for (int j=0; j<k.length; j++) {
                    ps.println(k[j].von.label+" "+k[j].nach.label+" "+k[j].gewicht);
                }
            }
            ps.close();
        } catch (Exception e){
            System.err.println(e);
        }

    }

    public void importiereCSV(){

    }

}
