package br.edu.utfpr.pb.pw44s.server.security;

public class SecurityConstants {
    public static final String SECRET = "utfpr"; // secret utilizado para gerar o token
    public static final long EXPIRATION_TIME = 60L*60*24*1000*30; // 30 dias em ms
    public static final String TOKEN_PREFIX = "Bearer "; // tipo da autenticação
    public static final String HEADER_STRING = "Authorization"; // header que será passado ao server com o token
}