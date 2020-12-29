import org.glassfish.jersey.message.internal.StringHeaderProvider;

import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DocumentService {

    private static final String ASTRA_DB_ID = "";
    private static final String ASTRA_DB_REGION = "";
    private static Client client = ClientBuilder.newClient();
    private static String authUrl = "https://" + ASTRA_DB_ID + "-" + ASTRA_DB_REGION + ".apps.astra.datastax.com/api/rest/v1/auth";
    private static String url = "https://" + ASTRA_DB_ID + "-" + ASTRA_DB_REGION + ".apps.astra.datastax.com/api/rest/v2";

    public static void main(String[] args) {


        Credentials creds = new Credentials("username" , "password*");

        Response response = client
                .target(authUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(creds, MediaType.APPLICATION_JSON));

        //Fetch this once and use for the duration of the program. If it expires, re-generate.
        Token token = response.readEntity(Token.class);

        String collectionId = "your collection id";
        String namespace = "your namespace / keyspace name";
        String collection = "your collection name";

        String getDocumentUrl = url + "/namespaces/" + namespace + "/collections/" + collection + "/" + collectionId;

        String out = client
                .target(getDocumentUrl)
                .request(MediaType.APPLICATION_JSON)
                .header("X-Cassandra-Token", token.getAuthToken())
                .get(String.class);

        System.out.println(out);

    }

}
