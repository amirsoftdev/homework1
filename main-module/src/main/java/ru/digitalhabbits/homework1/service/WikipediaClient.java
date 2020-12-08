package ru.digitalhabbits.homework1.service;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;

public class WikipediaClient {
    public static final String WIKIPEDIA_SEARCH_URL = "https://en.wikipedia.org/w/api.php";

    @Nonnull
    public String search(@Nonnull String searchString) {
        final URI uri = prepareSearchUrl(searchString);
        //TODO:Review
        String json = "";
        HttpGet request = new HttpGet(uri);

        try(CloseableHttpClient httpClient = HttpClients.createDefault()){
            HttpResponse httpResponse = httpClient.execute(request);
            HttpEntity entity = httpResponse.getEntity();

            if (entity != null) {
                json =  EntityUtils.toString(entity);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return extractText(json);
    }

    private String extractText(String json) {
        JsonObject pages = (JsonObject) JsonParser.parseString(json);
        String res = Objects.requireNonNull(pages
                .getAsJsonObject()
                .getAsJsonObject("query")
                .getAsJsonObject("pages")
                .entrySet().stream().findFirst().orElse(null))
                .getValue()
                .getAsJsonObject()
                .get("extract")
                .getAsString();
        return res;
    }

    @Nonnull
    private URI prepareSearchUrl(@Nonnull String searchString) {
        try {
            return new URIBuilder(WIKIPEDIA_SEARCH_URL)
                    .addParameter("action", "query")
                    .addParameter("format", "json")
                    .addParameter("titles", searchString)
                    .addParameter("prop", "extracts")
                    .addParameter("explaintext", "")
                    .build();
        } catch (URISyntaxException exception) {
            throw new RuntimeException(exception);
        }
    }
}
