package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * An implementation of the Translator interface which reads in the translation
 * data from a JSON file. The data is read in once each time an instance of this class is constructed.
 */
public class JSONTranslator implements Translator {

    /**
     * Map from country code to a map of language codes to translations.
     */
    private final Map<String, Map<String, String>> translations;

    /**
     * List of available country codes.
     */
    private final List<String> countryCodes;

    /**
     * Set of available language codes.
     */
    private final List<String> languageCodes;

    /**
     * Constructs a JSONTranslator using data from the sample.json resources file.
     */
    public JSONTranslator() {
        this("sample.json");
    }

    /**
     * Constructs a JSONTranslator populated using data from the specified resources file.
     * @param filename the name of the file in resources to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public JSONTranslator(String filename) {
        translations = new HashMap<>();
        countryCodes = new ArrayList<>();
        languageCodes = new ArrayList<>();

        try {

            String jsonString = Files.readString(Paths.get(getClass()
                    .getClassLoader()
                    .getResource(filename)
                    .toURI()));

            JSONArray jsonArray = new JSONArray(jsonString);

            // Populate the translations map and country/language lists
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject countryObj = jsonArray.getJSONObject(i);
                String countryCode = countryObj.getString("alpha3").toLowerCase();
                Map<String, String> langToName = new HashMap<>();

                // Iterate over keys to get language codes and translations
                for (String key : countryObj.keySet()) {
                    if (!"id".equals(key) && !"alpha2".equals(key) && !"alpha3".equals(key)) {
                        String translation = countryObj.getString(key);
                        String languageCode = key.toLowerCase();
                        langToName.put(languageCode, translation);

                        if (!languageCodes.contains(languageCode)) {
                            languageCodes.add(languageCode);
                        }
                    }
                }

                translations.put(countryCode, langToName);
                if (!countryCodes.contains(countryCode)) {
                    countryCodes.add(countryCode);
                }
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public List<String> getCountryLanguages(String country) {
        Map<String, String> langMap = translations.get(country.toLowerCase());
        if (langMap != null) {
            return new ArrayList<>(langMap.keySet());
        }
        return new ArrayList<>();
    }

    @Override
    public List<String> getCountries() {
        return new ArrayList<>(countryCodes);
    }

    @Override
    public String translate(String country, String language) {
        Map<String, String> langMap = translations.get(country.toLowerCase());
        if (langMap != null) {
            return langMap.get(language.toLowerCase());
        }
        return null;
    }
}
