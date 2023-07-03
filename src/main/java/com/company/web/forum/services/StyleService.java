package com.company.web.forum.services;

import com.company.web.forum.models.Style;

import java.util.List;

public interface StyleService {

    List<Style> get();

    Style get(int id);

}
