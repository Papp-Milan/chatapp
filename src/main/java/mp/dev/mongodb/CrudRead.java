package mp.dev.mongodb;

import io.github.cdimascio.dotenv.Dotenv;

import com.mongodb.client.*;
import org.bson.Document;

public class CrudRead {
    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.load();

        String uri = dotenv.get("mongodb_connection_string");

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            // database and collection code goes here
            MongoDatabase db = mongoClient.getDatabase("chats");
            MongoCollection<Document> collection = db.getCollection("messages");

            // find code goes here
            MongoCursor<Document> cursor = collection.find().iterator();

            // iterate code goes here
            try {
                while (cursor.hasNext()) {
                    System.out.println(cursor.next().toJson());
                }
            } finally {
                cursor.close();
            }

            // get every "text" value of every document
            for (Document doc : collection.find()) {
                System.out.println("TEXT: " + doc.get("text"));
            }
        }
    }
}