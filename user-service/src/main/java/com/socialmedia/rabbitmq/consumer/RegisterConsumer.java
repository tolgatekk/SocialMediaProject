package com.socialmedia.rabbitmq.consumer;

import com.socialmedia.entity.UserProfile;
import com.socialmedia.mapper.IUserMapper;
import com.socialmedia.rabbitmq.model.RegisterModel;
import com.socialmedia.rabbitmq.producer.RegisterElasticProducer;
import com.socialmedia.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterConsumer {

    private final UserProfileService userProfileService;
    private final RegisterElasticProducer registerElasticProducer;

    @RabbitListener(queues = "${rabbitmq.register-queue}")
    public void createUserFromQueue(RegisterModel model){
        System.out.println(model);
        UserProfile userProfile = userProfileService.save(UserProfile.builder()
                .authId(model.getAuthId())
                .username(model.getUsername())
                .email(model.getEmail())
                .build());

        registerElasticProducer.sendNewUser(IUserMapper.INSTANCE.toRegisterElasticModel(userProfile));
    }


//    @RabbitListener(queues = "registerQueue")
//    public void newUserCreate(RegisterModel registerModel){
//        userProfileService.createNewUserWithRabbit(registerModel);
//    }
}
