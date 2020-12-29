
import javax.ws.rs.client.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

public class DocumentAPI {

    private static final String ASTRA_DB_ID = "00255586-55f2-461d-947d-e9566d55b42d";
    private static final String ASTRA_DB_REGION = "europe-west1";
    private static Client client = ClientBuilder.newClient();
    private static String authUrl = "https://" + ASTRA_DB_ID + "-" + ASTRA_DB_REGION + ".apps.astra.datastax.com/api/rest/v1/auth";
    private static String url = "https://" + ASTRA_DB_ID + "-" + ASTRA_DB_REGION + ".apps.astra.datastax.com/api/rest/v2";

    public static void main(String[] args) {


        Credentials creds = new Credentials("admin21" , "Admin21*");

        Response response = client
                .target(authUrl)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(creds, MediaType.APPLICATION_JSON));

        //Fetch this once and use for the duration of the program. If it expires, re-generate.
        Token token = response.readEntity(Token.class);

        String collectionId = "d6961f12-b2ec-4492-a6ec-d077c8c7f44b";
        String namespace = "Test21";
        String collection = "hello_docs";

        String getDocumentUrl = url + "/namespaces/" + namespace + "/collections/" + collection + "/" + collectionId;

        String out = client
                .target(getDocumentUrl)
                .request(MediaType.APPLICATION_JSON)
                .header("X-Cassandra-Token", token.getAuthToken())
                .get(String.class);

        System.out.println(out);

    }

}
