package com.sparta.topster.domain.openApi.service.spotify;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;

import java.io.IOException;
import java.net.URI;

@Slf4j(topic = "SpotifyUtil")
@Configuration
public class SpotifyUtil {

    @Value("${spotify.client.id}")
    private String CLIENT_ID;
    @Value("${spotify.client.secret}")
    private String CLIENT_SECRET;
    @Value("${base.url}")
    private String baseUrl;
    private SpotifyApi spotifyApi;
    private URI redirecURI;

    @PostConstruct
    public void init() {
        redirecURI = UriComponentsBuilder.fromUriString(baseUrl)
            .path("/users/login")
            .encode()
            .build()
            .toUri();
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(redirecURI)
                .build();
    }

    public String accesstoken() {
        log.debug(CLIENT_ID);
        log.debug(CLIENT_SECRET);

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            String accessToken = spotifyApi.getAccessToken();
            log.debug("Spotify accessToken is " + accessToken);
            return accessToken;

        } catch (IOException | SpotifyWebApiException e) {
            log.error("Error: " + e.getMessage());
            return "error";
        } catch (org.apache.hc.core5.http.ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
