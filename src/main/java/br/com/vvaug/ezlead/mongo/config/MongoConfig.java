package br.com.vvaug.ezlead.mongo.config;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.util.Collection;
import java.util.Collections;

@Profile("!dev")
@Configuration
/*
 * https://docs.spring.io/spring-boot/docs/2.0.3.RELEASE/reference/htmlsingle/#boot-features-testing-spring-boot-applications-testing-user-configuration
 */
@EnableMongoRepositories(basePackages = "br.com.vvaug.ezlead.repository")
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public MongoClient mongoClient() {

        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/ezleads-db?authSource=admin");

        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .credential(MongoCredential.createCredential("mongoadmin", "ezleads-db", "secret".toCharArray()))
                .build();

        return MongoClients.create(mongoClientSettings);
    }

    @Override
    protected String getDatabaseName() {
        return "ezleads-db";
    }

    @Override
    public Collection getMappingBasePackages() {
        return Collections.singleton("br.com.vvaug.ezlead");
    }

}
