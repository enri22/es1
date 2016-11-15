package json_example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONArray;
import org.json.JSONObject;

/** 
 * Classe che legge da JSON la Astronomy Picture of the Day della NASA
 * Poi modifica il JSON aggiungendo degli attributi, e salvandolo.
 * 
 * Per i parametri del web service vedasi https://api.nasa.gov/api.html#web-service-rate-limits 
 * 
 * ATTENZIONE: attualmente ci sono dei limiti sull'uso di questo servizio, se non dovesse restituire un Json, impostare una API Key
 * 
 * Corso di informatica 3B, Unibg, A.A. 2015-2016
 */
public class JsonExample {
	public static void main(String[] args) {
		try {
			/*
			 * 1. Salvataggio in una stringa della risposta del WebService
			 */
            URL url = new URL("https://api.nasa.gov/planetary/apod?concept_tags=True&api_key=DEMO_KEY");
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
            //
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            bufferedReader.close();
            String s = sb.toString();
            
            /*
             * 2. Parsing della stringa come oggetto JSON, e output dei contenuti
             */
            //"s" stringa contenente il json
            JSONObject o = new JSONObject(s);
            System.out.println("URL Immagine: " + o.getString("url"));
            System.out.println("Titolo: " + o.getString("title"));
            
            System.out.print("TAGS: ");
           /* JSONArray a = o.getJSONArray("concepts"); // C'è un JSONArray dentro il JSONObject
            for (int i=0; i<a.length(); i++) System.out.print( a.getString(i) + (i<a.length()-1 ? ", " : "") );
     
            System.out.print("\nDescrizione: "+o.getString("explanation"));
            
            /*
             * 3. Inserimento nel JSON, a scopo dimostrativo, di un nuovo JSONArray contentente:
             * - data di oggi
             * - un array con il nome dell'utente e il suo gradimento, per 3 utenti diversi (gradimento è casuale da 1 a 10)
             */
            JSONObject myAttr = new JSONObject();
            /*serve per aggiungere degli attributi ad un json object */
            myAttr.accumulate("data", new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
            
            JSONArray gradimenti = new JSONArray();
            for (int i=0; i<3; i++) {
            	JSONObject t = new JSONObject();
            	t.put("User"+(i+1), ""+(int)(Math.random()*10+0.5));
            	gradimenti.put(t);
            }
            myAttr.put("gradimenti", gradimenti); // Si usa "put" per aggiungere un array
            o.accumulate("myAttr", myAttr);
            System.out.println("\nJSON CON ATTRIBUTI AGGIUNTI:\n"+ o.toString(2)); // Stampa il JSON Finale, indentandolo con una distanza di 2 spazi
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
}
