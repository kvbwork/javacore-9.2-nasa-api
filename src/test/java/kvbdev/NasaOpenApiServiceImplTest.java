package kvbdev;

import kvbdev.kvbdev.nasa.ApodResponse;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;
import java.net.URI;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;

class NasaOpenApiServiceImplTest {

    HttpEntity httpEntityMock;
    CloseableHttpResponse httpResponseMock;
    CloseableHttpClient httpClientMock;
    NasaOpenApiServiceImpl sut;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws Exception {
        httpEntityMock = Mockito.mock(HttpEntity.class);

        httpResponseMock = Mockito.mock(CloseableHttpResponse.class);
        when(httpResponseMock.getEntity()).thenReturn(httpEntityMock);

        httpClientMock = Mockito.mock(CloseableHttpClient.class);
        when(httpClientMock.execute(Mockito.any(HttpUriRequest.class))).thenReturn(httpResponseMock);

        sut = new NasaOpenApiServiceImpl(httpClientMock);
    }

    @org.junit.jupiter.api.AfterEach
    void tearDown() {
        httpEntityMock = null;
        httpResponseMock = null;
        httpClientMock = null;
        sut = null;
    }

    @org.junit.jupiter.api.Test
    void getAstronomyPictureOfTheDay_image_preview_success() throws Exception {
        String json = "{\"copyright\":\"Makrem Larnaout\",\"date\":\"2022-08-16\",\"explanation\":\"Does the Earth ever pass through....\",\"hdurl\":\"https://apod.nasa.gov/apod/image/2208/MeteorWind_Larnaout_2048.jpg\",\"media_type\":\"image\",\"service_version\":\"v1\",\"title\":\"A Meteor Wind over Tunisia\",\"url\":\"https://apod.nasa.gov/apod/image/2208/MeteorWind_Larnaout_960.jpg\"}";
        when(httpEntityMock.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()));
        String expectedUrl = "https://apod.nasa.gov/apod/image/2208/MeteorWind_Larnaout_960.jpg";

        ApodResponse apod = sut.getAstronomyPictureOfTheDay().orElseThrow();

        assertThat(URI.create(apod.getPreviewImageUrl()).toString(), equalTo(expectedUrl));
    }

    @org.junit.jupiter.api.Test
    void getAstronomyPictureOfTheDay_video_preview_success() throws Exception {
        String json = "{\"date\":\"2022-08-14\",\"explanation\":\"Over 4000 planets are now known...\",\"media_type\":\"video\",\"service_version\":\"v1\",\"thumbnail_url\":\"https://img.youtube.com/vi/aiFD_LBx2nM/0.jpg\",\"title\":\"4000 Exoplanets\",\"url\":\"https://www.youtube.com/embed/aiFD_LBx2nM?rel=0\"}";
        when(httpEntityMock.getContent()).thenReturn(new ByteArrayInputStream(json.getBytes()));
        String expectedUrl = "https://img.youtube.com/vi/aiFD_LBx2nM/0.jpg";

        ApodResponse apod = sut.getAstronomyPictureOfTheDay().orElseThrow();

        assertThat(URI.create(apod.getPreviewImageUrl()).toString(), equalTo(expectedUrl));
    }

}