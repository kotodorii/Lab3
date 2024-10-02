package org.translation;

import java.util.ArrayList;
import java.util.List;

/**
 * An implementation of the Translator interface which translates
 * the country code "can" to several languages.
 */
public class InLabByHandTranslator implements Translator {

    /**
     * Country code for Canada.
     */
    public static final String CANADA = "can";

    /**
     * Country code for France.
     */
    public static final String FRANCE = "fra";

    /**
     * Language code for German.
     */
    public static final String GERMAN = "de";

    /**
     * Language code for English.
     */
    public static final String ENGLISH = "en";

    /**
     * Language code for Chinese.
     */
    public static final String CHINESE = "zh";

    /**
     * Language code for Spanish.
     */
    public static final String SPANISH = "es";

    /**
     * Language code for French.
     */
    public static final String FRENCH = "fr";

    /**
     * Returns the language abbreviations for all languages whose translations are
     * available for the given country.
     *
     * @param country the country
     * @return list of language abbreviations which are available for this country
     */
    @Override
    public List<String> getCountryLanguages(String country) {
        if (CANADA.equals(country) || FRANCE.equals(country)) {
            return new ArrayList<>(List.of(GERMAN, ENGLISH, CHINESE, SPANISH, FRENCH));
        }
        return new ArrayList<>();
    }

    /**
     * Returns the country abbreviations for all countries whose translations are
     * available from this Translator.
     *
     * @return list of country abbreviations for which we have translations available
     */
    @Override
    public List<String> getCountries() {
        return new ArrayList<>(List.of(CANADA, FRANCE));
    }

    /**
     * Returns the name of the country based on the specified country abbreviation and language abbreviation.
     *
     * @param country  the country
     * @param language the language
     * @return the name of the country in the given language or null if no translation is available
     */
    @Override
    public String translate(String country, String language) {
        String result;

        switch (country) {
            case CANADA:
                result = translateCanada(language);
                break;
            case FRANCE:
                result = translateFrance(language);
                break;
            default:
                result = null;
                break;
        }

        return result;
    }

    /**
     * Translates the country name for Canada into the specified language.
     *
     * @param language the language code
     * @return the translated country name or null if not available
     */
    private String translateCanada(String language) {
        String translation;

        switch (language) {
            case GERMAN:
                translation = "Kanada";
                break;
            case ENGLISH:
            case FRENCH:
                translation = "Canada";
                break;
            case CHINESE:
                translation = "加拿大";
                break;
            case SPANISH:
                translation = "Canadá";
                break;
            default:
                translation = null;
                break;
        }

        return translation;
    }

    /**
     * Translates the country name for France into the specified language.
     *
     * @param language the language code
     * @return the translated country name or null if not available
     */
    private String translateFrance(String language) {
        String translation;

        switch (language) {
            case GERMAN:
                translation = "Frankreich";
                break;
            case ENGLISH:
            case FRENCH:
                translation = "France";
                break;
            case CHINESE:
                translation = "法国";
                break;
            case SPANISH:
                translation = "Francia";
                break;
            default:
                translation = null;
                break;
        }

        return translation;
    }
}
