package com.example.minimo2calizmaria.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Repositories {

    private static Repositories repoinstance;

    public String name;
    public String repos_url;
    public String languages_url;
    public String language;

    public Repositories(){}

    public static synchronized Repositories getInstance(){
        if(repoinstance == null) {
            repoinstance = new Repositories();
        }
        return repoinstance;
    }

    public Repositories(String name, String repos_url, String languages_url, String language) {
        this.name = name;
        this.repos_url = repos_url;
        this.languages_url = languages_url;
        this.language = language;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRepos_url() {
        return repos_url;
    }

    public void setRepos_url(String repos_url) {
        this.repos_url = repos_url;
    }

    public String getLanguages_url() {
        return languages_url;
    }

    public void setLanguages_url(String languages_url) {
        this.languages_url = languages_url;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
