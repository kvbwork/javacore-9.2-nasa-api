package kvbdev;

import kvbdev.kvbdev.nasa.ApodResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;

import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

    public static void main(String[] args) throws Exception {

        try (CloseableHttpClient httpClient = getHttpClient()) {

            NasaOpenApiServiceImpl nasaOpenApiService = new NasaOpenApiServiceImpl(httpClient);

            ApodResponse apod = nasaOpenApiService.getAstronomyPictureOfTheDay().orElseThrow();

            String imageUrl = apod.getPreviewImageUrl();
            Path filePath = Path.of(imageUrl.substring(imageUrl.lastIndexOf('/') + 1));

            Files.write(filePath, nasaOpenApiService.download(imageUrl).orElseThrow());

            if (Files.exists(filePath) && Files.size(filePath) > 0) {
                System.out.println("Astronomy Picture of the Day was successfully downloaded");
                System.out.println(filePath + " has size " + Files.size(filePath) + " bytes");
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
