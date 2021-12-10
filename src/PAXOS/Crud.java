package PAXOS;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.io.FileOutputStream;
import java.io.InputStream;
import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

//TODO: NEED TO ADD TO PARAMS DATABASE NAME TO RETRIEVE FROM GIVEN DATABASE

public class Crud extends java.rmi.server.UnicastRemoteObject implements ICrud{

    public Crud() throws RemoteException {
        super();
    }

    @Override
    public ObjectId upload(InputStream inputStream, String fileName) throws RemoteException {
        System.out.println("Calling upload...");
        MongoClient mongoClient = MongoClients.create("mongodb+srv://testUser:testUser@server1.w54b6.mongodb.net/firstDb?retryWrites=true&w=majority");
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.ENGLISH);
        ObjectId fileId = null;
        try {
            MongoDatabase database = mongoClient.getDatabase("firstDb");
            GridFSBucket gridBucket = GridFSBuckets.create(database);
            GridFSUploadOptions uploadOptions = new GridFSUploadOptions().chunkSizeBytes(1024).metadata(new Document("type", "image").append("upload_date", format.parse("2016-09-01T00:00:00Z")).append("content_type", "image/jpg"));
            fileId = gridBucket.uploadFromStream(fileName, inputStream, uploadOptions);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
        return fileId;
    }

    @Override
    public void download(String fileName, String outputString) throws RemoteException {
        System.out.println("Calling download...");
        MongoClient mongoClient = MongoClients.create("mongodb+srv://testUser:testUser@server1.w54b6.mongodb.net/firstDb?retryWrites=true&w=majority");

        try {
            MongoDatabase database = mongoClient.getDatabase("firstDb");
            GridFSBucket gridBucket = GridFSBuckets.create(database);

            FileOutputStream fileOutputStream = new FileOutputStream(fileName);
            gridBucket.downloadToStream(fileName, fileOutputStream);
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }

    @Override
    public void delete(ObjectId objectId) throws RemoteException {
        System.out.println("Calling delete...");
        MongoClient mongoClient = MongoClients.create("mongodb+srv://testUser:testUser@server1.w54b6.mongodb.net/firstDb?retryWrites=true&w=majority");
        try {
            MongoDatabase database = mongoClient.getDatabase("firstDb");
            GridFSBucket gridBucket = GridFSBuckets.create(database);
            gridBucket.delete(objectId);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mongoClient.close();
        }
    }
}
