package kvbdev.kvbdev.nasa;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class ApodResponse {
    private String copyright;
    private String date;
    private String explanation;
    private String hdurl;
    private String media_type;
    private String serviceVersion;
    private String title;
    private String url;
    private String thumbnailUrl;

    @JsonCreator
    public ApodResponse(
            @JsonProperty("copyright") String copyright,
            @JsonProperty("date") String date,
            @JsonProperty("explanation") String explanation,
            @JsonProperty("hdurl") String hdurl,
            @JsonProperty("media_type") String media_type,
            @JsonProperty("service_version") String serviceVersion,
            @JsonProperty("title") String title,
            @JsonProperty("url") String url,
            @JsonProperty("thumbnail_url") String thumbnailUrl
    ) {
        this.copyright = copyright;
        this.date = date;
        this.explanation = explanation;
        this.hdurl = hdurl;
        this.media_type = media_type;
        this.serviceVersion = serviceVersion;
        this.title = title;
        this.url = url;
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getPreviewImageUrl() {
        if ("video".equalsIgnoreCase(getMedia_type()))
            return getThumbnailUrl();

        return getUrl();
    }
}
