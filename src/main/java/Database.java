/********************
 Hambartzum Gamburian
 Database.java
 **********************/

import org.bson.Document;
import java.util.ArrayList;
import java.util.Scanner;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.lte;

public class Database {
    private ArrayList<Document> items;
    private MongoClient client;
    private MongoDatabase db;
    private MongoCollection<Document> collection;
    private MongoCursor<Document> cursor;
    Scanner in = new Scanner(System.in);
    String input;

    public Database() {
        items = new ArrayList<Document>();
        client = new MongoClient("localhost");
        db = client.getDatabase("contactBook");
        collection = db.getCollection("contacts");
    }

    public void createNewMongoCollection() {
        boolean exists = false;
        for (String col : db.listCollectionNames()) {
            exists = col.equals("contacts") ? true : false;
        }

        if (exists) {
            System.out.println("Collection \"students\" already exists. Cannot create another collection.");
        }
        else {
            System.out.println("Create New Collection called \"contacts\" in Current Database \"contactBook\" ?");
            System.out.print("Enter 'y' for yes, 'n' for no : ");
            input = in.next().toLowerCase();
            if (input.equals("y")) {
                db.createCollection("contacts");
                System.out.println("Successfully Created New Collection Called: \"contacts\"");
            }
        }
    }

    public void dropCurrentMongoCollection() {
        boolean exists = false;
        for (String col : db.listCollectionNames()) {
            exists = col.equals("contacts") ? true : false;
        }

        if (exists) {
            System.out.println("Drop Current Collection called \"contacts\" in Current Database \"contactBook\" ?");
            System.out.print("Enter 'y' for yes, 'n' for no : ");
            input = in.next().toLowerCase();
            if (input.equals("y")) {
                db.getCollection("contacts").drop();
                System.out.println("Successfully Dropped Current Collection Called: \"contacts\"");
            }
        }
        else {
            System.out.println("Collection \"students\" does not exist. Cannot delete something that is non-existant!");
        }
    }

    public void loadFromDBinit() {
        cursor = collection.find().iterator();
        try {
            while (cursor.hasNext()) {
                items.add(cursor.next());
            }
        } finally {
            cursor.close();
        }
    }

    public void printAll() {
        for(int i = 0; i < items.size(); i++) {
            System.out.println(items.get(i).toJson());
        }
    }

    public void addContact(String name, long number, String comment) {
        Document add = new Document("i", items.size() + 1).append("name", name).append("number", number).append("comment", comment);
        collection.insertOne(add);
        items.add(add);
    }

    public void removeContactByName(String name) {
        Document remove = collection.find(eq("name", name)).first();
        if (items.contains(remove)) {
            System.out.println("Successfully removed\n" + remove + "\n\n");
            collection.findOneAndDelete(eq("name", name));
            items.remove(remove);
        }
        else {
            System.out.println("No such contact with name \"" + name + "\" exists.\n\n");
        }
    }

    public void removeContactByNumber(long number) {
        Document remove = collection.find(eq("number", number)).first();
        if (items.contains(remove)) {
            System.out.println("Successfully removed\n" + remove + "\n\n");
            collection.findOneAndDelete(eq("number", number));
            items.remove(remove);
        }
        else {
            System.out.println("No such contact with number \"" + number + "\" exists.\n\n");
        }
    }

    public void removeContactByComment(String comment) {
        Document remove = collection.find(eq("comment", comment)).first();
        if (items.contains(remove)) {
            System.out.println("Successfully removed\n" + remove + "\n\n");
            collection.findOneAndDelete(eq("comment", comment));
            items.remove(remove);
        }
        else {
            System.out.println("No such contact with comment \"" + comment + "\" exists.\n\n");
        }
    }

    public void showByName(String name) {
        Document find = collection.find(eq("name", name)).first();
        if (items.contains(find)) {
            System.out.println(find.toJson());
        }
        else {
            System.out.println("No such contact with name \"" + name + "\" exists.\n\n");
        }
    }

    public void showByNumber(long number) {
        Document find = collection.find(eq("number", number)).first();
        if (items.contains(find)) {
            System.out.println(find.toJson());
        }
        else {
            System.out.println("No such contact with number \"" + number + "\" exists.\n\n");
        }
    }

    public void showByComment(String comment) {
        Document find = collection.find(eq("comment", comment)).first();
        if (items.contains(find)) {
            System.out.println(find.toJson());
        }
        else {
            System.out.println("No such contact with comment \"" + comment + "\" exists.\n\n");
        }
    }

    public void removeAllData() {
        DeleteResult deleteResult = collection.deleteMany(lte("i", items.size()));
        System.out.println("Successfully deleted " + deleteResult.getDeletedCount() + " items.");
    }

}
