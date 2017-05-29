package com.marcthomas.services;

import com.marcthomas.entities.Film;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * Created by marc.thomas on 01/04/2017.
 */
@RestController
public class PreferredFilmsService {
    @Autowired
    private SecretService secretService;

    private Map<String, List<Film>> filmsByUsername;

    public PreferredFilmsService() {
        filmsByUsername = new HashMap<>();

        List<Film> films = new ArrayList<>();
        films.add(new Film("Star Wars", "George Lucas", new GregorianCalendar(1977, Calendar.FEBRUARY, 11).getTime()));
        films.add(new Film("Empire Strikes Back", "George Lucas", new GregorianCalendar(1979, Calendar.MAY, 11).getTime()));

        filmsByUsername.put("marc.thomas", films);

        films = new ArrayList<>();
        films.add(new Film("E.T.", "Steven Spielburg", new GregorianCalendar(1981, Calendar.NOVEMBER, 2).getTime()));
        films.add(new Film("Empire Strikes Back", "George Lucas", new GregorianCalendar(1979, Calendar.MAY, 11).getTime()));

        filmsByUsername.put("john.smith", films);
    }

    @RequestMapping("/preferred-films")
    public List<Film> getPreferredFilms(@RequestParam String jwt) {
        if (jwt != null) {
            Jws<Claims> jwsClaims = Jwts.parser()
                    .setSigningKeyResolver(secretService.getSigningKeyResolver())
                    .parseClaimsJws(jwt);

            boolean accessToFilms = (boolean)jwsClaims.getBody().get("accessToFilms");

            if (accessToFilms) {
                return filmsByUsername.get(jwsClaims.getBody().get("sub"));
            } else {
                return null;
            }
        }

        return null;
    }
}
