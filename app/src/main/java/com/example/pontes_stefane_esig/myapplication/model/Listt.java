package com.example.pontes_stefane_esig.myapplication.model;

public class Listt extends Model{

    private long project_id;
    private String name;

    public Listt() {
    }

    public Listt(String name) {
        this.name = name;
    }

    public Listt(long id, String name, long project_id) {
        this.id = id;
        this.name = name;
        this.project_id = project_id;
    }

    public long getProject_id() {
        return project_id;
    }

    public void setProject_id(long project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
