package com.example.pokemon.rest.schema;

import java.util.Objects;

public class NamedUrl {
    private String name;
    private String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        NamedUrl namedUrl = (NamedUrl) o;
        return Objects.equals(name, namedUrl.name)
            && Objects.equals(url, namedUrl.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, url);
    }
}
