package com.kimlngo.ws.clients.controller;

import com.kimlngo.ws.clients.response.AlbumRest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Controller
public class AlbumsController {

    @Autowired
    private OAuth2AuthorizedClientService oauth2ClientService;

//    @Autowired
//    private RestTemplate restTemplate;

    @Autowired
    private WebClient webClient;

    @GetMapping("/albums")
    public String getAlbums(Model model, @AuthenticationPrincipal OidcUser principal) {

        String url = "http://localhost:8082/albums";

        List<AlbumRest> albumRests = webClient.get()
                                              .uri(url)
                                              .retrieve()
                                              .bodyToMono(new ParameterizedTypeReference<List<AlbumRest>>() {
                                              })
                                              .block();//ok for now

        model.addAttribute("albums", albumRests);

        return "albums";
    }
}
