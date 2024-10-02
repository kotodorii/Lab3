package org.translation;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class provides the service of converting country codes to their names.
 */
public class CountryCodeConverter {

    /**
     * Index of the country name in the split parts array.
     */
    private static final int COUNTRY_NAME_INDEX = 0;

    /**
     * Index of the Alpha-3 country code in the split parts array.
     */
    private static final int ALPHA3_CODE_INDEX = 2;

    /**
     * Minimum required number of parts in each line after splitting.
     */
    private static final int MINIMUM_PARTS_LENGTH = 3;

    /**
     * Map from country code to country name.
     */
    private final Map<String, String> codeToCountry;

    /**
     * Map from country name to country code.
     */
    private final Map<String, String> countryToCode;

    /**
     * Default constructor which will load the country codes from "country-codes.txt"
     * in the resources folder.
     */
    public CountryCodeConverter() {
        this("country-codes.txt");
    }

    /**
     * Overloaded constructor which allows us to specify the filename to load the country code data from.
     * @param filename the name of the file in the resources folder to load the data from
     * @throws RuntimeException if the resource file can't be loaded properly
     */
    public CountryCodeConverter(String filename) {

        codeToCountry = new HashMap<>();
        countryToCode = new HashMap<>();

        try {
            List<String> lines = Files.readAllLines(Paths.get(getClass()
                    .getClassLoader().getResource(filename).toURI()));

            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] parts = line.split("\t");
                if (parts.length >= MINIMUM_PARTS_LENGTH) {
                    String country = parts[COUNTRY_NAME_INDEX].trim();
                    String code = parts[ALPHA3_CODE_INDEX].trim();
                    codeToCountry.put(code, country);
                    countryToCode.put(country, code);
                }
            }

        }
        catch (IOException | URISyntaxException ex) {
            throw new RuntimeException(ex);
        }

    }

    /**
     * Returns the name of the country for the given country code.
     * @param code the 3-letter code of the country
     * @return the name of the country corresponding to the code
     */
    public String fromCountryCode(String code) {
        return codeToCountry.get(code);
    }

    /**
     * Returns the code of the country for the given country name.
     * @param country the name of the country
     * @return the 3-letter code of the country
     */
    public String fromCountry(String country) {
        return countryToCode.get(country);
    }

    /**
     * Returns how many countries are included in this code converter.
     * @return how many countries are included in this code converter.
     */
    public int getNumCountries() {
        return codeToCountry.size();
    }
}
