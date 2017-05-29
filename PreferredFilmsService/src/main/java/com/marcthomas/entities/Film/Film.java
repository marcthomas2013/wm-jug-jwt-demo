package com.marcthomas.entities.Film;

import java.util.Date;

/**
 * Created by marc.thomas on 01/04/2017.
 */
public class Film {
    private String title;
    private String director;
    private Date releaseDate;

    public Film(String title, String director, Date releaseDate) {
        this.title = title;
        this.director = director;
        this.releaseDate = releaseDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }
}
