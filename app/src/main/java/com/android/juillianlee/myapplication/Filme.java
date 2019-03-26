package com.android.juillianlee.myapplication;

public class Filme {

    private String title;
    private String director;
    private Long episode_id;


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

    public Long getEpisode_id() {
        return episode_id;
    }

    public void setEpisode_id(Long episode_id) {
        this.episode_id = episode_id;
    }

    @Override
    public String toString() {
        return "Title: " + title + "\n Diretor: " +
                director + "\n Episode: " + episode_id;
    }
}
