package fr.sortyquizz.web.rest.client;

import fr.sortyquizz.service.dto.imports.openquizzdb.ThemeQuizzDBDTO;
import fr.sortyquizz.web.rest.vm.JWTToken;
import fr.sortyquizz.web.rest.vm.LoginVM;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
public class ImportOpenQuizzDBClient {

    private final Logger log = LoggerFactory.getLogger(ImportOpenQuizzDBClient.class);

    private final RestTemplate restTemplate;

    private JWTToken token;

    private final String baseURL = "http://127.0.0.1:8080";

    public ImportOpenQuizzDBClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    @Scheduled(initialDelay = 2000, fixedDelay = 30000000)
    public void importQuizzData() {
        try {
            List<String> urls = extractURLFromCSV();
            List<ThemeQuizzDBDTO> quizzToImport = urls.parallelStream().map(this::callOpenDB)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            token = login("admin", "admin");
            if (token != null) {
                quizzToImport.forEach(this::saveNewPack);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean saveNewPack(ThemeQuizzDBDTO themeQuizzDBDTO) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(token.getIdToken());
            HttpEntity<ThemeQuizzDBDTO> entity = new HttpEntity<>(themeQuizzDBDTO, headers);

            ResponseEntity<Boolean> requestSuccess = restTemplate.exchange(baseURL + "/api/openquizz/import-quizz", HttpMethod.POST, entity, Boolean.class);
            log.debug("success for the pack {}  ??? {}", themeQuizzDBDTO.getTheme(), requestSuccess.getBody());
            return requestSuccess.getBody();
        } catch (HttpClientErrorException hcee) {
            log.error("Problem when import to WS for {}", themeQuizzDBDTO.getUrl());
            return false;
        }
    }

    private JWTToken login(String username, String password) {
        LoginVM loginVM = new LoginVM();
        loginVM.setUsername(username);
        loginVM.setPassword(password);
        return restTemplate.postForObject(baseURL + "/api/authenticate", loginVM, JWTToken.class);
    }

    private ThemeQuizzDBDTO callOpenDB(String url) {
        this.log.debug("Processing : {}", url);
        try {
            ThemeQuizzDBDTO forObject = restTemplate.getForObject(url, ThemeQuizzDBDTO.class);
            forObject.setUrl(url);
            return forObject;
        } catch (HttpMessageConversionException hmce) {
            log.debug("Problem when mapping for : {}", url);
            return null;
        }
    }

    private List<String> extractURLFromCSV() throws IOException {
        List<String> urls = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("C:\\run_results.csv"))) {
            while (scanner.hasNextLine()) {
                String[] data = scanner.nextLine().split(",");
                try {
                    if (data[2].contains("https:")) {
                        String extractedUrl = data[2].replace("\"", "");
                        urls.add(extractedUrl);
                        log.debug("Extracted URL : " + extractedUrl);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    log.error("no url present");
                }
            }
        }
        return urls;
    }
}
