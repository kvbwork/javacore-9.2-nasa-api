package kvbdev;

import kvbdev.kvbdev.nasa.ApodResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {

        try (CloseableHttpClient httpClient = getHttpClient()) {

            NasaOpenApiServiceImpl nasaOpenApiService = new NasaOpenApiServiceImpl(httpClient);

            String apodUrl = nasaOpenApiService.getAstronomyPictureOfTheDay()
                    .map(ApodResponse::getUrl)
                    .orElseThrow();

            Path fileName = Path.of(new URI(apodUrl).getPath()).getFileName();

            Files.write(fileName, nasaOpenApiService.download(apodUrl)
                    .orElseThrow());

            if (Files.exists(fileName) && Files.size(fileName) > 0) {
                System.out.println("Astronomy Picture of the Day was successfully downloaded");
                System.out.println(fileName + " has size " + Files.size(fileName) + " bytes");
            }

        }
    }

    public static CloseableHttpClient getHttpClient() {
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(RequestConfig.custom()
                        .setConnectTimeout(5000)
                        .setSocketTimeout(30000)
                        .setRedirectsEnabled(false)
                        .build())
                .build();
    }

}
