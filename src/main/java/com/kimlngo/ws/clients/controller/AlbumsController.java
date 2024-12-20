package com.kimlngo.ws.clients.controller;

import com.kimlngo.ws.clients.response.AlbumRest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class AlbumsController {

    @GetMapping("/albums")
    public String getAlbums(Model model) {
        AlbumRest albumOne = new AlbumRest();
        albumOne.setAlbumId("albumOne");
        albumOne.setAlbumTitle("Album One Title");
        albumOne.setAlbumUrl("http://localhost:8082/albums/1");

        AlbumRest albumTwo = new AlbumRest();
        albumTwo.setAlbumId("albumTwo");
        albumTwo.setAlbumTitle("Album Two Title");
        albumTwo.setAlbumUrl("http://localhost:8082/albums/2");

        model.addAttribute("albums", Arrays.asList(albumOne, albumTwo));

        return "albums";
    }
}
