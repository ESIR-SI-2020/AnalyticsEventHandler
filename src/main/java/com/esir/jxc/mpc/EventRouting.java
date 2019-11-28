package com.esir.jxc.mpc;

import com.esir.jxc.mpc.model.Event;
import com.esir.jxc.mpc.model.UserAdded;
import com.esir.jxc.mpc.model.ArticleAdded;
import com.esir.jxc.mpc.model.event.ArticleCreated;
import com.esir.jxc.mpc.repository.ArticleAddedRepository;
import com.esir.jxc.mpc.repository.UserAddedRepository;
import com.esir.jxc.mpc.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@Component
public class EventRouting {

    @Autowired
    UserAddedRepository userAddedRepository;
    @Autowired
    ArticleAddedRepository articleAddedRepository;

    public void processEvent(Event event) {
        if (event.getEventName().equals("USER_ADDED")) {
            UserAdded userAdded = new UserAdded(UUID.randomUUID().toString(), DateUtils.getDate());
            userAddedRepository.save(userAdded);
        }

        else if (event.getEventName().equals("ARTICLE_ADDED")) {
            ArticleCreated eventArticleCreated = ArticleCreated.of(event);
            ArticleAdded articleAdded = new ArticleAdded();
            articleAdded.setCreationDate(getDate());
            articleAdded.setUrl(eventArticleCreated.getUrl());
            articleAddedRepository.save(articleAdded);
        }
    }
}
