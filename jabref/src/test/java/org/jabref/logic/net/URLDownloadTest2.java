package org.jabref.logic.net;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

import org.jabref.support.DisabledOnCIServer;

import kong.unirest.UnirestException;
import org.junit.jupiter.api.Test;

//import nz.ac.wgtn.saflate.junit5.*;

import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class URLDownloadTest2 {

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

//   @Rule public SanitiseNetworkDependenciesRule rule = new SanitiseNetworkDependenciesRule();
//


    @Test
    public void testStringDownloadWithSetEncoding() throws IOException {
        URLDownload dl = new URLDownload(new URL("http://www.google.com"));

        assertTrue(dl.asString().contains("Google"), "google.com should contain google");
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void testStringDownload() throws IOException {
        URLDownload dl = new URLDownload(new URL("http://www.google.com"));

        assertTrue(dl.asString(StandardCharsets.UTF_8).contains("Google"), "google.com should contain google");
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void testFileDownload() throws IOException {
        File destination = File.createTempFile("jabref-test", ".html");
        try {
            URLDownload dl = new URLDownload(new URL("http://www.google.com"));
            dl.toFile(destination.toPath());
            assertTrue(destination.exists(), "file must exist");
        } finally {
            // cleanup
            if (!destination.delete()) {
                System.err.println("Cannot delete downloaded file");
            }
        }
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void testDetermineMimeType() throws IOException {
        URLDownload dl = new URLDownload(new URL("http://www.google.com"));

        assertTrue(dl.getMimeType().startsWith("text/html"));
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void downloadToTemporaryFilePathWithoutFileSavesAsTmpFile() throws IOException {
        URLDownload google = new URLDownload(new URL("http://www.google.com"));

        String path = google.toTemporaryFile().toString();
        assertTrue(path.endsWith(".tmp"), path);
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void downloadToTemporaryFileKeepsName() throws IOException {
        URLDownload google = new URLDownload(new URL("https://github.com/JabRef/jabref/blob/master/LICENSE.md"));

        String path = google.toTemporaryFile().toString();
        assertTrue(path.contains("LICENSE") && path.endsWith(".md"), path);
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    @DisabledOnCIServer("CI Server is apparently blocked")
    public void downloadOfFTPSucceeds() throws IOException {
        URLDownload ftp = new URLDownload(new URL("ftp://ftp.informatik.uni-stuttgart.de/pub/library/ncstrl.ustuttgart_fi/INPROC-2016-15/INPROC-2016-15.pdf"));

        Path path = ftp.toTemporaryFile();
        assertNotNull(path);
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void downloadOfHttpSucceeds() throws IOException {
        URLDownload ftp = new URLDownload(new URL("http://www.jabref.org"));

        Path path = ftp.toTemporaryFile();
        assertNotNull(path);
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void downloadOfHttpsSucceeds() throws IOException {
        URLDownload ftp = new URLDownload(new URL("https://www.jabref.org"));

        Path path = ftp.toTemporaryFile();
        assertNotNull(path);
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void testCheckConnectionSuccess() throws MalformedURLException {
        URLDownload google = new URLDownload(new URL("http://www.google.com"));

        assertTrue(google.canBeReached());
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void testCheckConnectionFail() throws MalformedURLException {
        URLDownload nonsense = new URLDownload(new URL("http://nonsenseadddress"));

        assertThrows(UnirestException.class, nonsense::canBeReached);
    }

    @ExtendWith(nz.ac.wgtn.saflate.junit5.SanitiseNetworkDependenciesExtension.class)

    @Test
    public void connectTimeoutIsNeverNull() throws MalformedURLException {
        URLDownload urlDownload = new URLDownload(new URL("http://www.example.com"));
        assertNotNull(urlDownload.getConnectTimeout(), "there's a non-null default by the constructor");

        urlDownload.setConnectTimeout(null);
        assertNotNull(urlDownload.getConnectTimeout(), "no null value can be set");
    }
}