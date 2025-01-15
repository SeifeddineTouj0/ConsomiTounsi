package com.jellali.forum.Command.Update;

public class UpdateSujetCommand {
    private Long id;
    private String titre;
    private String contenu;

    public Long getId() {
        return id;
    }

    public String getTitre() {
        return titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
