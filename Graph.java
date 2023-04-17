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
    
        g.neueEcke("Mainz");
        g.neueEcke("Wiesbaden");
        g.neueEcke("Oppenheim");
        g.neueEcke("Ingelheim");
        g.neueKante("Mainz", "Wiesbaden", 12, 12);
        g.neueKante("Mainz", "Oppenheim", 22, 22);
        g.neueKante("Mainz", "Ingelheim", 17, 17);
        
        //g.lade();
        //g.speichere();

        //g.exportiere();
        //g.importiere();
        
        g.zeige();
        
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
        if (ecken.containsKey(label)) throw new Error("no duplicate keys allowed: "+label);
        
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
     * Erzeugt eine gerichtete Kante mit Gewicht 1
     */
    public void neueKante(String vonEcke, String zuEcke){
        Ecke von = ecken.get(vonEcke);
        Ecke nach = ecken.get(zuEcke);
        if (von!=null && nach!=null) {
            von.addKante(nach, 1);
        } else throw new Error ("Ecken "+vonEcke+" "+zuEcke+" existieren nicht beide.");
    }

    /**
     * Erzeugt zwei gerichtete Kanten, beide Gewichte werden angegeben.
     * Soll nur eine gewichtete Kante erzeigt werden, für das andere Gewicht
     * 0 übergeben.
     */
    public void neueKante(String vonEcke, String zuEcke, double gewicht, double rueckgewicht)
    {
        Ecke von = ecken.get(vonEcke);
        Ecke nach = ecken.get(zuEcke);
        if (von!=null && nach!=null) {
            if (gewicht != 0) von.addKante(nach, gewicht);
            if (rueckgewicht != 0) nach.addKante(von, rueckgewicht);            
        } else throw new Error ("Ecken "+vonEcke+" "+zuEcke+" existieren nicht beide.");
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
        } else throw new Error ("Ecken "+vonEcke+" "+zuEcke+" existieren nicht beide.");
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

    public void exportiere(){

        try {
            PrintStream ps = new PrintStream(new FileOutputStream("graph.txt"));

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

    public void importiere(){

        List <String[]> lines = new ArrayList();
        try (BufferedReader br = new BufferedReader(new FileReader("graph.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(" "); // Trennzeichen ist hier das Leerzeichen " "
                lines.add(values);
            }
        } catch (IOException ioe) {}

        int n=Integer.parseInt(lines.get(0)[0]); // erste Zeile, nur ein Eintrag: Anzahl der Ecken
        lines.remove(0); // erste Zeile entfernen
        lines.remove(0); // optische Trennung entfernen
        
        for (int i=0; i<n; i++) { // beginnend bei der ersten Ecke, Daten lesen und Ecken erzeugen
            String label = lines.get(0)[0];
            int x = Integer.parseInt(lines.get(0)[1]);
            int y = Integer.parseInt(lines.get(0)[2]);
            this.neueEcke(label);
            Ecke e = getEcke(label); // neue Ecke holen und die Koordinaten nachträglich setzen
            e.x = x;
            e.y = y;
            lines.remove(0); // gerade verarbeitete Zeile entfernen
        }
        
        while (lines.size() > 0) { // alle Kanten verarbeiten
            String von = lines.get(0)[0];
            String nach = lines.get(0)[1];
            double gewicht = Double.parseDouble(lines.get(0)[2]);
            neueKante(von, nach, gewicht, 0); // nur jeweils die Hin-Kante erzeugen
            lines.remove(0);
        }
    }
    

}
