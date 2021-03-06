package com.iamycx.club.async.handler;

import com.iamycx.club.async.EventHandler;
import com.iamycx.club.async.EventModel;
import com.iamycx.club.async.EventType;
import com.iamycx.club.model.EntityType;
import com.iamycx.club.model.Message;
import com.iamycx.club.model.User;
import com.iamycx.club.service.MessageService;
import com.iamycx.club.service.UserService;
import com.iamycx.club.util.ClubUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by iamycx on 19:21 2017/12/29
 */
@Component
public class FollowHandler implements EventHandler {
    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Override
    public void doHandle(EventModel model) {
        Message message = new Message();
        message.setFromId(ClubUtil.SYSTEM_USERID);
        message.setToId(model.getEntityOwnerId());
        message.setCreatedDate(new Date());
        User user = userService.getUser(model.getActorId());
        if (model.getEntityType()== EntityType.ENTITY_QUESTION){
            message.setContent("用户"+user.getName()+"关注了你的问题，点击查看 http://127.0.0.1:8080/question/"+model.getEntityId());
        } else if(model.getEntityType() == EntityType.ENTITY_USER){
            message.setContent("用户"+user.getName()+"关注了你，点击查看 http://127.0.0.1:8080/user/"+model.getActorId());
        }
        messageService.addMessage(message);
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW);
    }
}
