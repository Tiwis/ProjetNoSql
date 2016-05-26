import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;
import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.BulkWriteOperation;
import com.mongodb.BulkWriteResult;
import com.mongodb.Cursor;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.ParallelScanOptions;
import com.mongodb.ServerAddress;


import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.text.BadLocationException;

import static java.util.concurrent.TimeUnit.SECONDS;
public class MongoDBJDBC {
	static DBCollection coll;
	static String profil ="";
	static DBCursor cursor;
   public static void main( String args[] ) {
	   JOptionPane jop = new JOptionPane();
	   profil = jop.showInputDialog(null, "Veuillez entrer votre profil :", "Configuration", JOptionPane.QUESTION_MESSAGE);
	   if(profil==null || profil==""){
		   System.exit(0);
	   }
	   MainInterface frame = new MainInterface();
	   frame.setVisible(true);
      try{
    	 MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
    	 MainInterface.affiche0("Connect to database successfully\n");
         DB db = mongoClient.getDB( "Twitter" );
         if (db.collectionExists("ColTwitter")) {
             coll = db.getCollection("ColTwitter");
             MainInterface.affiche0("Connect to collection successfully\n");
         }else{
        	 DBObject options = BasicDBObjectBuilder.start().add("capped", true).add("size", 2000000000l).get();
             coll = db.createCollection("ColTwitter",options);
             MainInterface.affiche0("Create to collection successfully\n");
         }  
      }catch(Exception e){
         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	  }
   }      
//  Taille de la base de donnée  
	public static void nombreTweet() throws BadLocationException{
		MainInterface.affiche0("\n\tIl y a "+coll.getCount()+" tweets\n");
	}         
//	Afficher avec une recherche de la base de donnée      
   	public static void rechercheTweet(String key,String message) throws BadLocationException{
   		BasicDBObject query=null;
   		if(message==""){
   			query = new BasicDBObject();
   		}else{
   			query = new BasicDBObject(key, message);
   		}
        BasicDBObject fields = new BasicDBObject("_id", 0);
        cursor = coll.find(query,fields);
        MainInterface.affiche0("\n\tListe de la recherche souhaité\n");
        while(cursor.hasNext()) {
            MainInterface.affiche0(""+cursor.next()+"\n");
        }
   	}
//	Afficher avec une recherche de la base de donnée      
   	public static void TweetAuteur() throws BadLocationException{
   		System.out.println(profil);
        BasicDBObject query = new BasicDBObject("auteur", profil);
        BasicDBObject fields = new BasicDBObject("_id", 0);
        cursor = coll.find(query,fields);
        while(cursor.hasNext()) {
            ProfilInterface.affiche1(""+cursor.next()+"\n");
        }
   	}
//	ajouter un tweet
     public static void insertTweet(String auteur,String hash,String message){
       BasicDBObject doc = new BasicDBObject("auteur", auteur)
       .append("hash", "#"+hash)
       .append("message", message);
       coll.insert(doc);
       System.out.println(coll.getCount());
     }
}