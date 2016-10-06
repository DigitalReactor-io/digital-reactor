package io.digitalreactor.logger;

import ch.qos.logback.classic.spi.LoggingEvent;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.core.db.dialect.SybaseSqlAnywhereDialect;
import com.mongodb.MongoClient;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoDatabase;
import org.apache.log4j.Level;
import org.bson.Document;

import java.net.UnknownHostException;

/**
 * Created by MStepachev on 06.10.2016.
 */
public class MongoDbAppender extends AppenderBase<LoggingEvent> {

    private final MongoClient mongoClient;
    private final MongoDatabase mongoDatabase;
    private final String collectionName;
    private final String databaseName;

    public MongoDbAppender() throws UnknownHostException {
        this.mongoClient = new MongoClient(System.getenv("mongodb_host"));
        this.collectionName = System.getenv("mongodb_logs_collection");
        this.databaseName = System.getenv("mongodb_db");
        this.mongoDatabase = this.mongoClient.getDatabase(databaseName);
    }

    @Override
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
    }

    @Override
    protected void append(LoggingEvent eventObject) {

        Document log = new Document();
        log.append("level", eventObject.getLevel());
        log.append("timeStamp", eventObject.getTimeStamp());
        log.append("loggerName", eventObject.getLoggerName());
        log.append("message", eventObject.getMessage());
        log.append("threadName", eventObject.getThreadName());
        // log.append("argumentArray", eventObject.getArgumentArray());
        //  log.append("marker", eventObject.getMarker().toString());
        // log.append("stackTrace", eventObject.getThrowableProxy().getStackTraceElementProxyArray());



        synchronized (this) {

            try {
                //mongoDatabase.getCollection(collectionName).insertOne(new Document());

                MongoClient md = new MongoClient();
                MongoDatabase testdb = md.getDatabase("test");
                //, WriteConcern.SAFE
                testdb.getCollection("logs").insertOne(new Document());

                System.out.println("Mongo fine: " + eventObject.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}