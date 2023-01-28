package com.qupp.post.repository;

public enum College {
    Humanities("Humanities")
    , SocialScience("SocialScience")
    , Business("Business")
    , NaturalScience("NaturalScience")
    , Engineering("Engineering")
    , Art("Art");

    String value;
    College(String value) {
        this.value = value;
    }
    public String value() {
        return value;
    }
}
