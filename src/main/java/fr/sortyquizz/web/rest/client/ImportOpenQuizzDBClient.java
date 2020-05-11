package fr.sortyquizz.web.rest.client;

import fr.sortyquizz.service.dto.imports.openquizzdb.ThemeQuizzDBDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    public ImportOpenQuizzDBClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

//    @Scheduled(initialDelay = 2000, fixedDelay = 30000000)
    public void importQuizzData() {
        try {
            List<String> urls = extractURLFromCSV();
            List<ThemeQuizzDBDTO> quizzToImport = urls.parallelStream().map(this::callOpenDB)
                .filter(themeQuizzDBDTO -> !Objects.isNull(themeQuizzDBDTO))
                .collect(Collectors.toList());
            quizzToImport.forEach(this::saveNewPack);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Boolean saveNewPack(ThemeQuizzDBDTO themeQuizzDBDTO) {
        try {
            Boolean success = restTemplate.postForObject("http://127.0.0.1:8080/api/openquizz/import-quizz", themeQuizzDBDTO, Boolean.class);
            log.debug("success for the pack {}  ??? {}", themeQuizzDBDTO.getTheme(), success);
            return success;
        } catch (HttpClientErrorException hcee) {
            log.error("Problem when import to WS for {}", themeQuizzDBDTO.getUrl());
            return false;
        }
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
