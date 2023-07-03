package com.company.web.forum.repositories;

import com.company.web.forum.exceptions.EntityNotFoundException;
import com.company.web.forum.models.Post;
import com.company.web.forum.models.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Repository
public class PostRepositoryImpl implements PostRepository{

    private final List<Post> posts;

    public PostRepositoryImpl() {
        posts = new ArrayList<>();

        Post post1 = new Post(1, 2, 1, "Proba 1", "Proba 1 beshe uspeshna.", 5, LocalDateTime.of(2023, 1, 5, 10, 10, 10), LocalDateTime.of(2023, 1, 5, 10, 10, 15), 10, 5);

        Post post2 = new Post(2, 2,2, "Proba 2", "Proba 2 beshe uspeshna.", 6, LocalDateTime.of(2023, 1, 6, 10, 10, 10), LocalDateTime.of(2023, 1, 5, 11, 10, 15), 15, 10);

        Post post3 = new Post(3, 3,3, "Proba 3", "Proba 3 beshe uspeshna.", 7, LocalDateTime.of(2023, 1, 7, 10, 10, 10), LocalDateTime.of(2023, 1, 5, 12, 10, 15), 20, 15);

        posts.add(post1);
        posts.add(post2);
        posts.add(post3);
    }

    @Override
    public List<Post> get() {
        return posts;
    }

    @Override
    public Post get(int id) {
        return posts.stream()
                .filter(post -> post.getId() == id)
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Post", id));
    }
    @Override
    public void create(Post post) {
        posts.add(post);
    }

    @Override
    public void delete(int id) {
        Post postToDelete = get(id);
        posts.remove(postToDelete);
    }

    @Override
    public void update(Post post) {
        Post postToUpdate = get(post.getId());
        postToUpdate.setContent(post.getContent());
        postToUpdate.setDislikes(post.getDislikes());
        postToUpdate.setLastActivity(post.getLastActivity());
        postToUpdate.setLikes(post.getLikes());
        postToUpdate.setViews(post.getViews());
        postToUpdate.setTitle(post.getTitle());
        postToUpdate.setTopic(post.getTopic());
        postToUpdate.setCreator(post.getCreator());
    }
    public static List<Post> sortBy(List<Post> posts, String sortBy) {
        if (sortBy != null && !sortBy.isEmpty()) {
            switch (sortBy.toLowerCase()) {
                case "comments":
                    posts.sort(Comparator.comparing(Post::getComments));
                    break;
                case "creation date":
                    posts.sort(Comparator.comparing(Post::getCreationDate));
                break;
            }
        }
        return posts;
    }
}
