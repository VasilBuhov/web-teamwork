package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.models.*;
import com.company.web.forum.repositories.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private static final String MODIFY_POST_ERROR_MESSAGE = "Only admin or post creator can modify a post.";
    private final PostRepository repository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.repository = postRepository;
    }

    public List<Post> get(FilterPostOptions filterPostOptions) {
        List<Post> resultList = repository.get(filterPostOptions);
        for (Post post : resultList) replaceSmileys(post);
        return resultList;
    }

    @Override
    public Post get(int id) {
        Post post = repository.get(id);
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(id));
        }
        return replaceSmileys(post);
    }

    public Post get(String creatorUsername) {
        Post post = repository.get(creatorUsername);
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(post.getId()));
        }
        return replaceSmileys(post);
    }
    @Override
    public List<Post> getPostsByUser(User user) {
        List<Post> resultList = repository.getPostsByUser(user);
        for (Post post : resultList) replaceSmileys(post);
        return resultList;
    }

    @Override
    public void create(Post post, User user, Topic topic) {
        post.setCreator(user);
        post.setCreationDate(LocalDateTime.now());
        post.setLikes(0);
        post.setTopic(topic);
        post.setStatusDeleted(0);
        topic.getPosts().add(post);
        repository.create(post);
    }
    public void update(Post post, User user) {
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(post.getId()));
        }
        checkModifyPermissions(post.getId(), user);
        repository.update(post);
    }

    @Override
    public void updateLike(Post post, User user) {
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(post.getId()));
        }
        if (post.getLikedBy().contains(user)) {
            post.setLikes(post.getLikes() - 1);
            post.getLikedBy().remove(user);
            repository.updateLike(post);
        } else {
            post.setLikes(post.getLikes() + 1);
            post.getLikedBy().add(user);
            repository.updateLike(post);
        }
    }

    @Override
    public void delete(int id, User user, Post post, Topic topic) {
        checkModifyPermissions(id, user);
        if (post.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(id));
        }
        topic.getPosts().remove(post);
        repository.delete(id);
    }

    public Post replaceSmileys(Post post){
        post.setContent(post.getContent().replace(":wink:", "<img src=\"/assets/img/emoticons/icon_wink.gif\">"));
        post.setContent(post.getContent().replace(":guitar:", "<img src=\"/assets/img/emoticons/music_guitar.gif\">"));
        post.setContent(post.getContent().replace(":yippy:", "<img src=\"/assets/img/emoticons/icon_yippy.gif\">"));
        post.setContent(post.getContent().replace(":yes:", "<img src=\"/assets/img/emoticons/icon_yes.gif\">"));
        post.setContent(post.getContent().replace(":yahoo:", "<img src=\"/assets/img/emoticons/icon_yahoo.gif\">"));
        post.setContent(post.getContent().replace(":winkie:", "<img src=\"/assets/img/emoticons/icon_winkie.gif\">"));
        post.setContent(post.getContent().replace(":v:", "<img src=\"/assets/img/emoticons/icon_v.gif\">"));
        post.setContent(post.getContent().replace(":dns:", "<img src=\"/assets/img/emoticons/icon_uvdns.gif\">"));
        post.setContent(post.getContent().replace(":v:", "<img src=\"/assets/img/emoticons/icon_v.gif\">"));
        post.setContent(post.getContent().replace(":lach:", "<img src=\"/assets/img/emoticons/totlach.gif\">"));
        post.setContent(post.getContent().replace(":tongue:", "<img src=\"/assets/img/emoticons/icon_tongue.gif\">"));
        post.setContent(post.getContent().replace(":thanks:", "<img src=\"/assets/img/emoticons/icon_thanks.gif\">"));
        post.setContent(post.getContent().replace(":surprised:", "<img src=\"/assets/img/emoticons/icon_surprised.gif\">"));
        post.setContent(post.getContent().replace(":stretcher:", "<img src=\"/assets/img/emoticons/icon_stretcher.gif\">"));
        post.setContent(post.getContent().replace(":spam:", "<img src=\"/assets/img/emoticons/icon_spam.gif\">"));
        post.setContent(post.getContent().replace(":smile:", "<img src=\"/assets/img/emoticons/icon_smile.gif\">"));
        post.setContent(post.getContent().replace(":slide:", "<img src=\"/assets/img/emoticons/icon_slide.gif\">"));
        post.setContent(post.getContent().replace(":sleeping:", "<img src=\"/assets/img/emoticons/icon_sleeping.gif\">"));
        post.setContent(post.getContent().replace(":slap:", "<img src=\"/assets/img/emoticons/icon_slap.gif\">"));
        post.setContent(post.getContent().replace(":sauer:", "<img src=\"/assets/img/emoticons/icon_sauer.gif\">"));
        post.setContent(post.getContent().replace(":sad:", "<img src=\"/assets/img/emoticons/icon_sad.gif\">"));
        post.setContent(post.getContent().replace(":rofl:", "<img src=\"/assets/img/emoticons/icon_rofl.gif\">"));
        post.setContent(post.getContent().replace(":psst:", "<img src=\"/assets/img/emoticons/icon_psst.gif\">"));
        post.setContent(post.getContent().replace(":pop:", "<img src=\"/assets/img/emoticons/icon_pop.gif\">"));
        post.setContent(post.getContent().replace(":pff:", "<img src=\"/assets/img/emoticons/icon_pff.gif\">"));
        post.setContent(post.getContent().replace(":pat:", "<img src=\"/assets/img/emoticons/icon_pat.gif\">"));
        post.setContent(post.getContent().replace(":opos:", "<img src=\"/assets/img/emoticons/icon_oops.gif\">"));
        post.setContent(post.getContent().replace(":ohhno:", "<img src=\"/assets/img/emoticons/icon_ohhno.gif\">"));
        post.setContent(post.getContent().replace(":music:", "<img src=\"/assets/img/emoticons/icon_music.gif\">"));
        post.setContent(post.getContent().replace(":muahah:", "<img src=\"/assets/img/emoticons/icon_muahah.gif\">"));
        post.setContent(post.getContent().replace(":mml:", "<img src=\"/assets/img/emoticons/icon_mml.gif\">"));
        post.setContent(post.getContent().replace(":mmh:", "<img src=\"/assets/img/emoticons/icon_mmh.gif\">"));
        post.setContent(post.getContent().replace(":mad:", "<img src=\"/assets/img/emoticons/icon_mad.gif\">"));
        post.setContent(post.getContent().replace(":lol:", "<img src=\"/assets/img/emoticons/icon_lol.gif\">"));
        post.setContent(post.getContent().replace(":kop:", "<img src=\"/assets/img/emoticons/icon_kop.gif\">"));
        post.setContent(post.getContent().replace(":kissed:", "<img src=\"/assets/img/emoticons/icon_kissed.gif\">"));
        post.setContent(post.getContent().replace(":kiss:", "<img src=\"/assets/img/emoticons/icon_kiss.gif\">"));
        post.setContent(post.getContent().replace(":kaffee:", "<img src=\"/assets/img/emoticons/icon_kaffee.gif\">"));
        post.setContent(post.getContent().replace(":hihi:", "<img src=\"/assets/img/emoticons/icon_hihi.gif\">"));
        post.setContent(post.getContent().replace(":help:", "<img src=\"/assets/img/emoticons/icon_help.gif\">"));
        post.setContent(post.getContent().replace(":hehehe:", "<img src=\"/assets/img/emoticons/icon_hehehe.gif\">"));
        post.setContent(post.getContent().replace(":gaehn:", "<img src=\"/assets/img/emoticons/icon_gaehn.gif\">"));
        post.setContent(post.getContent().replace(":faint:", "<img src=\"/assets/img/emoticons/icon_faint.gif\">"));
        post.setContent(post.getContent().replace(":dudu:", "<img src=\"/assets/img/emoticons/icon_dudu.gif\">"));
        post.setContent(post.getContent().replace(":duck:", "<img src=\"/assets/img/emoticons/icon_duckundweg.gif\">"));
        post.setContent(post.getContent().replace(":drinking:", "<img src=\"/assets/img/emoticons/icon_drinking.gif\">"));
        post.setContent(post.getContent().replace(":down:", "<img src=\"/assets/img/emoticons/icon_down.gif\">"));
        post.setContent(post.getContent().replace(":dau:", "<img src=\"/assets/img/emoticons/icon_daumen.gif\">"));
        post.setContent(post.getContent().replace(":dance:", "<img src=\"/assets/img/emoticons/icon_dance3.gif\">"));
        post.setContent(post.getContent().replace(":megadance:", "<img src=\"/assets/img/emoticons/icon_dance2.gif\">"));
        post.setContent(post.getContent().replace(":cry:", "<img src=\"/assets/img/emoticons/icon_cry.gif\">"));
        post.setContent(post.getContent().replace(":confused:", "<img src=\"/assets/img/emoticons/icon_confused.gif\">"));
        post.setContent(post.getContent().replace(":clap:", "<img src=\"/assets/img/emoticons/icon_clapping.gif\">"));
        post.setContent(post.getContent().replace(":cheers:", "<img src=\"/assets/img/emoticons/icon_cheers.gif\">"));
        post.setContent(post.getContent().replace(":blow:", "<img src=\"/assets/img/emoticons/icon_blow.gif\">"));
        post.setContent(post.getContent().replace(":blink:", "<img src=\"/assets/img/emoticons/icon_blink.gif\">"));
        post.setContent(post.getContent().replace(":v:", "<img src=\"/assets/img/emoticons/icon_v.gif\">"));
        post.setContent(post.getContent().replace(":big:", "<img src=\"/assets/img/emoticons/icon_big.gif\">"));
        post.setContent(post.getContent().replace(":bet:", "<img src=\"/assets/img/emoticons/icon_betruebt.gif\">"));
        post.setContent(post.getContent().replace(":applause:", "<img src=\"/assets/img/emoticons/icon_applause.gif\">"));


        return post;
    }

    private void checkModifyPermissions(int postId, User user) {
        Post post = repository.get(postId);
        if (!(user.getIsAdmin() == 1 || post.getCreator().equals(user))) {
            throw new AuthorizationException(MODIFY_POST_ERROR_MESSAGE);
        }
    }
}
