package org.translation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * Main class for this program.
 * Complete the code according to the "to do" notes.<br/>
 * The system will:<br/>
 * - prompt the user to pick a country name from a list<br/>
 * - prompt the user to pick the language they want it translated to from a list<br/>
 * - output the translation<br/>
 * - at any time, the user can type quit to quit the program<br/>
 */
public class Main {

    /**
     * Command to exit the program.
     */
    private static final String QUIT_COMMAND = "quit";

    /**
     * Country code converter instance.
     */
    private static final CountryCodeConverter COUNTRY_CODE_CONVERTER = new CountryCodeConverter();

    /**
     * Language code converter instance.
     */
    private static final LanguageCodeConverter LANGUAGE_CODE_CONVERTER = new LanguageCodeConverter();

    /**
     * This is the main entry point of our Translation System!<br/>
     * A class implementing the Translator interface is created and passed into a call to runProgram.
     * @param args not used by the program
     */
    public static void main(String[] args) {

        Translator translator = new InLabByHandTranslator();

        runProgram(translator);
    }

    /**
     * This is the method which we will use to test your overall program, since
     * it allows us to pass in whatever translator object that we want!
     * See the class Javadoc for a summary of what the program will do.
     * @param translator the Translator implementation to use in the program
     */
    public static void runProgram(Translator translator) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            String countryName = promptForCountry(translator, scanner);
            if (QUIT_COMMAND.equalsIgnoreCase(countryName)) {
                break;
            }

            // Convert country name to country code
            String countryCode = COUNTRY_CODE_CONVERTER.fromCountry(countryName);
            if (countryCode == null) {
                System.out.println("Invalid country name. Please try again.");
                continue;
            }
            countryCode = countryCode.toLowerCase();

            String languageName = promptForLanguage(translator, countryCode, scanner);
            if (QUIT_COMMAND.equalsIgnoreCase(languageName)) {
                break;
            }

            // Convert language name to language code
            String languageCode = LANGUAGE_CODE_CONVERTER.fromLanguage(languageName);
            if (languageCode == null) {
                System.out.println("Invalid language name. Please try again.");
                continue;
            }
            languageCode = languageCode.toLowerCase();

            String translation = translator.translate(countryCode, languageCode);
            if (translation != null) {
                System.out.println(countryName + " in " + languageName + " is " + translation);
            }
            else {
                System.out.println("Translation not available.");
            }

            System.out.println("Press enter to continue or type 'quit' to exit.");
            String textTyped = scanner.nextLine();

            if (QUIT_COMMAND.equalsIgnoreCase(textTyped)) {
                break;
            }
        }
    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForCountry(Translator translator, Scanner scanner) {
        List<String> countryCodes = translator.getCountries();
        List<String> countryNames = new ArrayList<>();

        // Convert country codes to country names
        for (String code : countryCodes) {
            String name = COUNTRY_CODE_CONVERTER.fromCountryCode(code.toUpperCase());
            if (name != null) {
                countryNames.add(name);
            }
        }

        // Sort the country names alphabetically
        Collections.sort(countryNames);

        // Print the country names one per line
        System.out.println("Available countries:");
        for (String name : countryNames) {
            System.out.println(name);
        }

        System.out.println("Select a country from above or type 'quit' to exit:");
        String input = scanner.nextLine();

        return input.trim();

    }

    // Note: CheckStyle is configured so that we don't need javadoc for private methods
    private static String promptForLanguage(Translator translator, String countryCode, Scanner scanner) {

        List<String> languageCodes = translator.getCountryLanguages(countryCode);
        List<String> languageNames = new ArrayList<>();

        // Convert language codes to language names
        for (String code : languageCodes) {
            String name = LANGUAGE_CODE_CONVERTER.fromLanguageCode(code);
            if (name != null) {
                languageNames.add(name);
            }
        }

        // Sort the language names alphabetically
        Collections.sort(languageNames);

        // Print the language names one per line
        System.out.println("Available languages:");
        for (String name : languageNames) {
            System.out.println(name);
        }

        System.out.println("Select a language from above or type 'quit' to exit:");
        String input = scanner.nextLine();

        return input.trim();
    }
}
