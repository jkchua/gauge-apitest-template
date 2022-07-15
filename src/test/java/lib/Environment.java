package lib;

public class Environment {

    /**
     * Method that returns the base URL defined in the environment properties file.
     * 
     * @return The API base URL.
     */
    public static String getBaseUrlEndpoint() {
        return System.getenv().get("base_api_url");
    }

    /**
     * Method that returns the flag if the test are being ran on higher
     * environments.
     * Used only when the test code requires special setup such as SSL errors.
     * 
     * @return The flag if the environment is production or not.
     */
    public static boolean isProductionEnvironment() {
        return Boolean.parseBoolean(System.getenv().get("is_production"));
    }
}
