package com.wywm.superconsole.Functions;

public class Troops {

    public String fullName;
    public int id;

    public Troops(String fullName, int id) {
        this.fullName = fullName;
        this.id = id;
    }

    public Troops(String tagName) {
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return id + ", " + fullName;
    }
}
