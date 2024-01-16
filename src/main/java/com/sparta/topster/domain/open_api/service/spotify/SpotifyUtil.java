package com.sparta.topster.domain.open_api.service.spotify;

import com.neovisionaries.i18n.CountryCode;
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
public class SpotifyUtil {

    @Value("${spotify.client.id}")
    private static final String CLIENT_ID = "9bca8c7747be4c06aa416f8961c96a3b";
    @Value("${spotify.client.secret}")
    private static final String CLIENT_SECRET = "9de70f9d8e024fbbaaaedfed3a18ca3a";

    private static final URI redirecURI = UriComponentsBuilder.fromUriString("http://localhost:8080")
            .path("/users/login")
            .encode()
            .build()
            .toUri();
    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(CLIENT_ID)
            .setClientSecret(CLIENT_SECRET)
            .setRedirectUri(redirecURI)
            .build();



    public String accesstoken() {
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
