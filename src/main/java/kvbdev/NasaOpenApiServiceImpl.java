package kvbdev;

import com.fasterxml.jackson.databind.ObjectMapper;
import kvbdev.kvbdev.nasa.ApodResponse;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

public class NasaOpenApiServiceImpl {

    protected static final String APOD_URL = "https://api.nasa.gov/planetary/apod";
    protected static final String API_KEY_VALUE = "zZE5YU4ZzheB38F1jLgLiMRYEudbEDYmESoDNtew";
    protected static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    protected final CloseableHttpClient httpClient;
    protected final ObjectMapper mapper = new ObjectMapper();

    public NasaOpenApiServiceImpl(CloseableHttpClient httpClient) {
        this.httpClient = Objects.requireNonNull(httpClient);
    }

    public Optional<ApodResponse> getAstronomyPictureOfTheDay() {
        String url = APOD_URL + "?api_key=" + API_KEY_VALUE;
        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_JSON.getMimeType());

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            String body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
            return Optional.ofNullable(mapper.readValue(body, ApodResponse.class));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    public Optional<byte[]> download(String url) {
        HttpGet request = new HttpGet(url);
        request.setHeader(HttpHeaders.ACCEPT, ContentType.APPLICATION_OCTET_STREAM.getMimeType());

        try (CloseableHttpResponse response = httpClient.execute(request)) {
            return Optional.ofNullable(response.getEntity().getContent().readAllBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return Optional.empty();
    }

}
