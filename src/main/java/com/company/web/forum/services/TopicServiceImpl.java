package com.company.web.forum.services;

import com.company.web.forum.exceptions.AuthorizationException;
import com.company.web.forum.exceptions.EntityDeletedException;
import com.company.web.forum.models.FilterTopicOptions;
import com.company.web.forum.models.Topic;
import com.company.web.forum.models.User;
import com.company.web.forum.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {
    private static final String MODIFY_TOPIC_ERROR_MESSAGE = "Only admin or topic creator can modify a topic.";
    private final TopicRepository repository;

    @Autowired
    public TopicServiceImpl(TopicRepository repository) {
        this.repository = repository;
    }


    @Override
    public List<Topic> get(FilterTopicOptions filterTopicOptions) {
        return repository.get(filterTopicOptions);
    }

    @Override
    public Topic get(int id) {
        Topic topic = repository.get(id);
        if (topic.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Topic", "ID", String.valueOf(id));
        }
        topic.setViews(topic.getViews() + 1);
        repository.updateViews(topic);
        replaceSmileys(topic);
        return topic;
    }
    @Override
    public List<Topic> getTopicsByUser(User user) {
        return repository.getTopicsByUser(user);
    }

    @Override
    public Topic replaceSmileys(Topic topic){
        topic.setContent(topic.getContent().replace(":wink:", "<img src=\"/assets/img/emoticons/icon_wink.gif\">"));
        topic.setContent(topic.getContent().replace(":guitar:", "<img src=\"/assets/img/emoticons/music_guitar.gif\">"));
        topic.setContent(topic.getContent().replace(":yippy:", "<img src=\"/assets/img/emoticons/icon_yippy.gif\">"));
        topic.setContent(topic.getContent().replace(":yes:", "<img src=\"/assets/img/emoticons/icon_yes.gif\">"));
        topic.setContent(topic.getContent().replace(":yahoo:", "<img src=\"/assets/img/emoticons/icon_yahoo.gif\">"));
        topic.setContent(topic.getContent().replace(":winkie:", "<img src=\"/assets/img/emoticons/icon_winkie.gif\">"));
        topic.setContent(topic.getContent().replace(":v:", "<img src=\"/assets/img/emoticons/icon_v.gif\">"));
        topic.setContent(topic.getContent().replace(":dns:", "<img src=\"/assets/img/emoticons/icon_uvdns.gif\">"));
        topic.setContent(topic.getContent().replace(":v:", "<img src=\"/assets/img/emoticons/icon_v.gif\">"));
        topic.setContent(topic.getContent().replace(":lach:", "<img src=\"/assets/img/emoticons/totlach.gif\">"));
        topic.setContent(topic.getContent().replace(":tongue:", "<img src=\"/assets/img/emoticons/icon_tongue.gif\">"));
        topic.setContent(topic.getContent().replace(":thanks:", "<img src=\"/assets/img/emoticons/icon_thanks.gif\">"));
        topic.setContent(topic.getContent().replace(":surprised:", "<img src=\"/assets/img/emoticons/icon_surprised.gif\">"));
        topic.setContent(topic.getContent().replace(":stretcher:", "<img src=\"/assets/img/emoticons/icon_stretcher.gif\">"));
        topic.setContent(topic.getContent().replace(":spam:", "<img src=\"/assets/img/emoticons/icon_spam.gif\">"));
        topic.setContent(topic.getContent().replace(":smile:", "<img src=\"/assets/img/emoticons/icon_smile.gif\">"));
        topic.setContent(topic.getContent().replace(":slide:", "<img src=\"/assets/img/emoticons/icon_slide.gif\">"));
        topic.setContent(topic.getContent().replace(":sleeping:", "<img src=\"/assets/img/emoticons/icon_sleeping.gif\">"));
        topic.setContent(topic.getContent().replace(":slap:", "<img src=\"/assets/img/emoticons/icon_slap.gif\">"));
        topic.setContent(topic.getContent().replace(":sauer:", "<img src=\"/assets/img/emoticons/icon_sauer.gif\">"));
        topic.setContent(topic.getContent().replace(":sad:", "<img src=\"/assets/img/emoticons/icon_sad.gif\">"));
        topic.setContent(topic.getContent().replace(":rofl:", "<img src=\"/assets/img/emoticons/icon_rofl.gif\">"));
        topic.setContent(topic.getContent().replace(":psst:", "<img src=\"/assets/img/emoticons/icon_psst.gif\">"));
        topic.setContent(topic.getContent().replace(":pop:", "<img src=\"/assets/img/emoticons/icon_pop.gif\">"));
        topic.setContent(topic.getContent().replace(":pff:", "<img src=\"/assets/img/emoticons/icon_pff.gif\">"));
        topic.setContent(topic.getContent().replace(":pat:", "<img src=\"/assets/img/emoticons/icon_pat.gif\">"));
        topic.setContent(topic.getContent().replace(":opos:", "<img src=\"/assets/img/emoticons/icon_oops.gif\">"));
        topic.setContent(topic.getContent().replace(":ohhno:", "<img src=\"/assets/img/emoticons/icon_ohhno.gif\">"));
        topic.setContent(topic.getContent().replace(":music:", "<img src=\"/assets/img/emoticons/icon_music.gif\">"));
        topic.setContent(topic.getContent().replace(":muahah:", "<img src=\"/assets/img/emoticons/icon_muahah.gif\">"));
        topic.setContent(topic.getContent().replace(":mml:", "<img src=\"/assets/img/emoticons/icon_mml.gif\">"));
        topic.setContent(topic.getContent().replace(":mmh:", "<img src=\"/assets/img/emoticons/icon_mmh.gif\">"));
        topic.setContent(topic.getContent().replace(":mad:", "<img src=\"/assets/img/emoticons/icon_mad.gif\">"));
        topic.setContent(topic.getContent().replace(":lol:", "<img src=\"/assets/img/emoticons/icon_lol.gif\">"));
        topic.setContent(topic.getContent().replace(":kop:", "<img src=\"/assets/img/emoticons/icon_kop.gif\">"));
        topic.setContent(topic.getContent().replace(":kissed:", "<img src=\"/assets/img/emoticons/icon_kissed.gif\">"));
        topic.setContent(topic.getContent().replace(":kiss:", "<img src=\"/assets/img/emoticons/icon_kiss.gif\">"));
        topic.setContent(topic.getContent().replace(":kaffee:", "<img src=\"/assets/img/emoticons/icon_kaffee.gif\">"));
        topic.setContent(topic.getContent().replace(":hihi:", "<img src=\"/assets/img/emoticons/icon_hihi.gif\">"));
        topic.setContent(topic.getContent().replace(":help:", "<img src=\"/assets/img/emoticons/icon_help.gif\">"));
        topic.setContent(topic.getContent().replace(":hehehe:", "<img src=\"/assets/img/emoticons/icon_hehehe.gif\">"));
        topic.setContent(topic.getContent().replace(":gaehn:", "<img src=\"/assets/img/emoticons/icon_gaehn.gif\">"));
        topic.setContent(topic.getContent().replace(":faint:", "<img src=\"/assets/img/emoticons/icon_faint.gif\">"));
        topic.setContent(topic.getContent().replace(":dudu:", "<img src=\"/assets/img/emoticons/icon_dudu.gif\">"));
        topic.setContent(topic.getContent().replace(":duck:", "<img src=\"/assets/img/emoticons/icon_duckundweg.gif\">"));
        topic.setContent(topic.getContent().replace(":drinking:", "<img src=\"/assets/img/emoticons/icon_drinking.gif\">"));
        topic.setContent(topic.getContent().replace(":down:", "<img src=\"/assets/img/emoticons/icon_down.gif\">"));
        topic.setContent(topic.getContent().replace(":dau:", "<img src=\"/assets/img/emoticons/icon_daumen.gif\">"));
        topic.setContent(topic.getContent().replace(":dance:", "<img src=\"/assets/img/emoticons/icon_dance3.gif\">"));
        topic.setContent(topic.getContent().replace(":megadance:", "<img src=\"/assets/img/emoticons/icon_dance2.gif\">"));
        topic.setContent(topic.getContent().replace(":cry:", "<img src=\"/assets/img/emoticons/icon_cry.gif\">"));
        topic.setContent(topic.getContent().replace(":confused:", "<img src=\"/assets/img/emoticons/icon_confused.gif\">"));
        topic.setContent(topic.getContent().replace(":clap:", "<img src=\"/assets/img/emoticons/icon_clapping.gif\">"));
        topic.setContent(topic.getContent().replace(":cheers:", "<img src=\"/assets/img/emoticons/icon_cheers.gif\">"));
        topic.setContent(topic.getContent().replace(":blow:", "<img src=\"/assets/img/emoticons/icon_blow.gif\">"));
        topic.setContent(topic.getContent().replace(":blink:", "<img src=\"/assets/img/emoticons/icon_blink.gif\">"));
        topic.setContent(topic.getContent().replace(":v:", "<img src=\"/assets/img/emoticons/icon_v.gif\">"));
        topic.setContent(topic.getContent().replace(":big:", "<img src=\"/assets/img/emoticons/icon_big.gif\">"));
        topic.setContent(topic.getContent().replace(":bet:", "<img src=\"/assets/img/emoticons/icon_betruebt.gif\">"));
        topic.setContent(topic.getContent().replace(":applause:", "<img src=\"/assets/img/emoticons/icon_applause.gif\">"));

        return topic;
    }

    @Override
    public List<Topic> get10(String order, String sortBy) {
        FilterTopicOptions sort = new FilterTopicOptions();
        Optional<String> orderCriteria = Optional.of(order);
        Optional<String> sortByCriteria = Optional.of(sortBy);
        sort.setSortOrder(orderCriteria);
        sort.setSortBy(sortByCriteria);
        List<Topic> resultList = repository.get(sort);
        for (Topic topic : resultList) replaceSmileys(topic);
        return repository.get10(resultList);
    }
    @Override
    public void create(Topic topic, User user) {
        topic.setCreator(user);
        topic.setCreationDate(LocalDateTime.now());
        topic.setTags(new HashSet<>());
        topic.setPosts(new HashSet<>());
        topic.setLikedBy(new HashSet<>());
        topic.setLikes(0);
        topic.setViews(0);
        topic.setStatusDeleted(0);
        repository.create(topic);

    }

    @Override
    public void delete(int id, User user) {
        Topic topicToBeDeleted = repository.get(id);
        if (topicToBeDeleted.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(id));
        }
        checkModifyPermissions(id, user);
        repository.delete(id);
    }

    @Override
    public void update(Topic topic, User user) {
        checkModifyPermissions(topic.getId(), user);
        if (topic.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(topic.getId()));
        }
        repository.update(topic);
    }
    public void updateLike(Topic topic, User user) {
        if (topic.getStatusDeleted() == 1) {
            throw new EntityDeletedException("Post", "ID", String.valueOf(topic.getId()));
        }
        if (topic.getLikedBy().contains(user)) {
            topic.setLikes(topic.getLikes() - 1);
            topic.getLikedBy().remove(user);
            repository.updateLike(topic);
        } else {
            topic.setLikes(topic.getLikes() + 1);
            topic.getLikedBy().add(user);
            repository.updateLike(topic);
        }
    }
    private void checkModifyPermissions(int topicId, User user) {
        Topic topic = repository.get(topicId);
        if (!(user.getIsAdmin() == 1 || topic.getCreator().equals(user))) {
            throw new AuthorizationException(MODIFY_TOPIC_ERROR_MESSAGE);
        }
    }
}
