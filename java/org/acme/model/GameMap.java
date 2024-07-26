package org.acme.model;

public class GameMap extends AbstractModel{
    int id;
    String name;
    String author;

    public GameMap() {
        super();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
        changedSinceLastSerialization = true;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
        changedSinceLastSerialization = true;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
        changedSinceLastSerialization = true;
    }
}