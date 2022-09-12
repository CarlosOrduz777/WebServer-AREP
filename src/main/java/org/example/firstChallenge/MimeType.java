package org.example.firstChallenge;

public enum MimeType {

    HTML("text/html"),
    JS("text/javascript"),
    JPEG("image/jpeg"),
    JPG("image/jpg"),
    PNG("image/png"),
    CSS("text/css");

    public final String value;

    private MimeType(String value){
        this.value = value;
    }
}
