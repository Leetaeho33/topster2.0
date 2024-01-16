package com.sparta.topster.domain.open_api.service.spotify;

import com.neovisionaries.i18n.CountryCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.util.UriComponentsBuilder;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.exceptions.SpotifyWebApiException;
import se.michaelthelin.spotify.model_objects.credentials.ClientCredentials;
import se.michaelthelin.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import se.michaelthelin.spotify.requests.data.albums.GetSeveralAlbumsRequest;
import se.michaelthelin.spotify.requests.data.search.simplified.SearchArtistsRequest;

import java.io.IOException;
import java.net.URI;
import java.text.ParseException;

@Slf4j(topic = "SpotifyUtil")
@Configuration
@RequiredArgsConstructor
public class SpotifyUtil {

    private String CLIENT_ID;
    private String CLIENT_SECRET;
    private SpotifyApi spotifyApi;
    private URI redirecURI = UriComponentsBuilder.fromUriString("http://localhost:8080")
            .path("/users/login")
            .encode()
            .build()
            .toUri();

    public SpotifyUtil(@Value("${spotify.client.id}") String CLIENT_ID, @Value("${spotify.client.secret}") String CLIENT_SECRET) {
        spotifyApi = new SpotifyApi.Builder()
                .setClientId(CLIENT_ID)
                .setClientSecret(CLIENT_SECRET)
                .setRedirectUri(redirecURI)
                .build();
        this.CLIENT_ID = CLIENT_ID;
        this.CLIENT_SECRET = CLIENT_SECRET;
    }

    public String accesstoken() {
        log.info(CLIENT_ID);
        log.info(CLIENT_SECRET);

        ClientCredentialsRequest clientCredentialsRequest = spotifyApi.clientCredentials().build();
        try {
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();
            // Set access token for further "spotifyApi" object usage
            spotifyApi.setAccessToken(clientCredentials.getAccessToken());
            String accessToken = spotifyApi.getAccessToken();
            log.info("Spotify accessToken is " + accessToken);
            return accessToken;

        } catch (IOException | SpotifyWebApiException e) {
            log.error("Error: " + e.getMessage());
            return "error";
        } catch (org.apache.hc.core5.http.ParseException e) {
            throw new RuntimeException(e);
        }
    }

}
