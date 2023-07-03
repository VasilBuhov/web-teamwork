package com.company.web.forum.repositories;

import com.company.web.forum.models.Style;

import java.util.List;

public interface StyleRepository {

    List<Style> get();

    Style get(int id);

}
