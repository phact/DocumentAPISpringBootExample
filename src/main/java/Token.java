import com.fasterxml.jackson.annotation.JsonProperty;

public class Token {

    @JsonProperty("authToken")
    public String authToken;

    public Token() {
    }

    @JsonProperty("authToken")
    public String getAuthToken() {
        return authToken;
    }

    @JsonProperty("authToken")
    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }
}
