package com.example.chatapplication.controller;



import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

@Controller
@RequestMapping("api/test")
public class TestController {

    @GetMapping("")
    public RedirectView test() throws URISyntaxException, IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpPost = new HttpGet("https://accounts.google.com/o/oauth2/v2/auth");
        URI uri = new URIBuilder(httpPost.getURI())
                .addParameter("client_id", "494684683435-1vbnfp35hpr92eu2fitpfk616rv1chq0.apps.googleusercontent.com")
                .addParameter("redirect_uri", "http://localhost:3003/auth/google/callback")
                .addParameter("response_type", "code")
                .addParameter("scope","email")
                .build();
        httpPost.setURI(uri);
        CloseableHttpResponse response = client.execute(httpPost);
        long len=response.getEntity().getContentLength();
        return new RedirectView("http://localhost:3003/auth/google/callback");
    }
}
