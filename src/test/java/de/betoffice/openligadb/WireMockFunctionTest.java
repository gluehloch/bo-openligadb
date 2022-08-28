package de.betoffice.openligadb;

import static com.github.tomakehurst.wiremock.client.WireMock.options;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

/*
import com.github.tomakehurst.wiremock.client.WireMock;
*/
@WireMock
public class WireMockFunctionTest {

    void xxx() {
        UrlPattern urlPattern = UrlPattern.fromOneOf(null, null, null, null);
        StubMapping stubMapping = options(urlPattern).withPort(8089).build();
        WireMockServer wireMockServer = new WireMockServer();
        wireMockServer.start();
    }
    
}
