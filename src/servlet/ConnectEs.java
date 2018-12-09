package servlet;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import  java.net.*;



public class ConnectEs {
    public static TransportClient  getClient (){
        String hostname = "localhost";
        int port=9300;
        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(
                new InetSocketTransportAddress(new InetSocketAddress(hostname,port))
        );
        Logger logger = LoggerFactory.getLogger(ConnectEs.class);
        logger.info("connect sucessfully"+client.toString());
        return client;

    }




}



