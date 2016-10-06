package io.digitalreactor.logger;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

/**
 * Created by MStepachev on 06.10.2016.
 */
public class MongoDbAppender extends AppenderBase<LoggingEvent> {

    /*private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final String collectionName;
    private final String databaseName;
*/
   /* public MongoDbAppender() throws UnknownHostException {
        this.mongoClient = new MongoClient(System.getenv("mongodb_host"));
        this.collectionName = System.getenv("mongodb_logs_collection");
        this.databaseName = System.getenv("mongodb_db");
        this.mongoDatabase = this.mongoClient.getDatabase(databaseName);
    }*/

   /* @Override
    public void start() {
        super.start();
        //TODO[St.maxim] remove constructor
    }

    @Override
    public synchronized void stop() {
        super.stop();
        if (mongoClient != null) {
            mongoClient.close();
        }
    }*/

    @Override
    protected void append(LoggingEvent eventObject) {

        DBObject log =  new BasicDBObject();
        log.put("level", eventObject.getLevel().toString());
        log.put("timeStamp", eventObject.getTimeStamp());
        log.put("loggerName", eventObject.getLoggerName());
        log.put("message", eventObject.getMessage());
        log.put("threadName", eventObject.getThreadName());
        // log.append("argumentArray", eventObject.getArgumentArray());
        //  log.append("marker", eventObject.getMarker().toString());
        // log.append("stackTrace", eventObject.getThrowableProxy().getStackTraceElementProxyArray());



        synchronized (this) {

            //TODO try with resource
            try {
                MongoClient md = new MongoClient();

                //mongoDatabase.getCollection(collectionName).insertOne(new Document());
                DB test = md.getDB("test");
                test.getCollection("logs").insert(log);


                System.out.println("Mongo fine: " + eventObject.getMessage());
                md.close();
            } catch (Exception e) {
                e.printStackTrace();

            }
        }

    }
}