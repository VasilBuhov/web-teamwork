package com.company.web.forum.helpers;

public class ParseContent {

    public static String formatText(String content) {
        content.replaceAll("\\[img\\](.*?)\\[/img\\]", "<img src=\"$1\">");
        content.replaceAll("\\[b\\](.*?)\\[/b\\]", "<b>\"$1\"</b>");
        content.replaceAll("\\[i\\](.*?)\\[/i\\]", "<i>\"$1\"</i>");
        content.replaceAll("\\[u\\](.*?)\\[/u\\]", "<u>\"$1\"</u>");
        return content;
    }
}
