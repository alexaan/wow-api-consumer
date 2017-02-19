package application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import api.dto.Mount;
import api.result.CharacterMountsResult;
import api.result.AllMountsResult;

import static java.lang.String.format;

@RestController
@RequestMapping("/mount")
public class MountController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MountController.class);

    private static final String API_BASE = "https://eu.api.battle.net/wow";
    private static final String API_KEY = "whp93utjy2cnh7t9whvhy68ppv976pcj";
    private static final String LOCALE = "en_GB";

    @RequestMapping(value = "/", method = RequestMethod.GET, produces = "application/json")
    public CharacterMountsResult getMountsForCharacter(@RequestParam("character") String character, @RequestParam("realm") String realm) {

        if(character != null && realm != null) {

            RestTemplate restTemplate = new RestTemplate();

            Map<String, String> uriParams = new HashMap<>();
            uriParams.put("apibase", API_BASE);
            uriParams.put("character", character);
            uriParams.put("realm", realm);
            uriParams.put("locale", LOCALE);
            uriParams.put("apikey", API_KEY);


            String url = "{apibase}/character/{realm}/{character}?fields=mounts&locale={locale}&apikey={apikey}";
            UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);

            try {
                CharacterMountsResult result = restTemplate.getForObject(builder.buildAndExpand(uriParams).toUri(), CharacterMountsResult.class);
                populateNotCollectedMounts(result);
                return result;
            } catch (HttpClientErrorException e) {
                LOGGER.info("wups.." + e.getStatusCode());
            }
        }

        return null;
    }

    private void populateNotCollectedMounts(CharacterMountsResult result) {
        AllMountsResult allMounts = getAllMounts();
        List<Mount> notCollected = new ArrayList<>();

        for(Mount m : allMounts.getMounts()) {
            if(!result.getMounts().getCollected().contains(m)) {
                notCollected.add(m);
            }
        }

        result.getMounts().setNotCollected(notCollected);
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET, produces = "application/json")
    @Cacheable("mounts")
    public AllMountsResult getAllMounts() {

        Long start = System.currentTimeMillis();

        RestTemplate restTemplate = new RestTemplate();

        Map<String, String> uriParams = new HashMap<>();
        uriParams.put("apibase", API_BASE);
        uriParams.put("locale", LOCALE);
        uriParams.put("apikey", API_KEY);

        String url = "{apibase}/mount/?locale={locale}&apikey={apikey}";

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
        URI u = builder.buildAndExpand(uriParams).toUri();

        try {
            AllMountsResult allMountsResult = restTemplate.getForObject(u, AllMountsResult.class);
            LOGGER.info(format("fetched [%s] mounts in [%s]ms", allMountsResult.getMounts().size(), (System.currentTimeMillis()-start)));
            return allMountsResult;
        } catch (HttpClientErrorException e) {
            LOGGER.info("wups all.." + e.getStatusCode());
        }

        return null;
    }
}