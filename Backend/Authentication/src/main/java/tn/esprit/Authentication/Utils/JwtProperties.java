package tn.esprit.Authentication.Utils;

public class JwtProperties {
    public static final String SECRET = "Achraf.ABBES";
    public static final int EXPIRATION_TIME = 864_000_000; // 10 days
    public static final int REFRESH_TOKEN_EXPIRATION_TIME = 864_000_000; // 10 days
    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
}