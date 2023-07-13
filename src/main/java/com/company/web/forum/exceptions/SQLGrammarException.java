package com.company.web.forum.exceptions;

public class SQLGrammarException extends RuntimeException {

    public SQLGrammarException(String type, int id) {
        this(type, "id", String.valueOf(id));
    }

    public SQLGrammarException(String type, String attribute, String value) {
        super(String.format("%s with %s %s not found.", type, attribute, value));
    }
}
