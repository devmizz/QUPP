package com.qupp.post.repository;

public enum Dept {
    Humanities("Humanities")
    , SocialScience("SocialScience")
    , Business("Business")
    , NaturalScience("NaturalScience")
    , Engineering("Engineering")
    , Art("Art");

    String value;
    Dept(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
}
