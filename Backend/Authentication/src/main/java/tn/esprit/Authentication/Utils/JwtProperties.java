package tn.esprit.Authentication.Utils;

public class JwtProperties {
    public static final String SECRET = "F17F519C579233D456325187182F68AZE52RAZEAZEC2";
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}