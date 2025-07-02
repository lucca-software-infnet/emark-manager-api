package mercado.emark.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

import mercado.emark.model.Cliente;
import mercado.emark.model.Funcionario;

@Service
public class TokenService {

    private final String secret = "segredinho";

    public String generateToken(UserDetails userDetails) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);

            String tipo;
            if (userDetails instanceof Cliente) {
                tipo = "CLIENTE";
            } else if (userDetails instanceof Funcionario) {
                tipo = "FUNCIONARIO";
            } else {
                tipo = "DESCONHECIDO";
            }

            return JWT.create()
                .withIssuer("auth")
                .withSubject(userDetails.getUsername())
                .withClaim("tipo", tipo)
                .withExpiresAt(getExpirationDate())
                .sign(algorithm);

        } catch (JWTCreationException e) {
            throw new RuntimeException("Erro ao gerar token JWT", e);
        }
    }

    public String validateToken(String token) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            var verifier = JWT.require(algorithm)
                .withIssuer("auth")
                .build();
            var decodedJWT = verifier.verify(token);
            return decodedJWT.getSubject();
        } catch (JWTVerificationException e) {
            System.out.println("Token inválido ou expirado: " + e.getMessage());
            return null;
        }
    }

    private Instant getExpirationDate() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}
