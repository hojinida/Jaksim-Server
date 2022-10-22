package com.jaks1m.project.follow.application;


import com.jaks1m.project.auth.support.SecurityUtil;
import com.jaks1m.project.follow.domain.Follow;
import com.jaks1m.project.notification.Notification;
import com.jaks1m.project.notification.domain.NotificationType;
import com.jaks1m.project.user.domain.User;
import com.jaks1m.project.common.exception.ErrorCode;
import com.jaks1m.project.common.exception.CustomException;
import com.jaks1m.project.follow.application.dto.FollowResponse;
import com.jaks1m.project.notification.domain.repository.NotificationRepository;
import com.jaks1m.project.follow.domain.repository.FollowRepository;
import com.jaks1m.project.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FollowService {
    private final UserRepository userRepository;
    private final FollowRepository followRepository;
    private final NotificationRepository notificationRepository;
    @Transactional
    public void addFollow(Long id){
        User fromUser=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        User toUser=userRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        if(followRepository.findByFromUserAndToUser(fromUser,toUser).isPresent()){
            throw new CustomException(ErrorCode.ALREADY_FOLLOW);
        }
        Follow follow=Follow.builder().fromUser(fromUser).toUser(toUser).build();
        followRepository.save(follow);

        notificationRepository.save(Notification.builder().user(toUser).checked(false).valueId(fromUser.getId())
                .message(fromUser.getName().getName()+"님이 회원님을 팔로우 했습니다.").notificationType(NotificationType.FOLLOW).build());
    }

    public List<FollowResponse> getFollowers(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Follow> followers = user.getFollowers();
        List<FollowResponse> result=new ArrayList<>();
        followers.forEach(follower -> result.add(FollowResponse.builder().id(follower.getFromUser().getId())
                .name(follower.getFromUser().getName().getName()).image(follower.getFromUser().getS3Image().getImagePath()).build()));
        return result;
    }

    public List<FollowResponse> getFollows(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Follow> follows = user.getFollows();
        List<FollowResponse> result=new ArrayList<>();
        follows.forEach(follower -> result.add(FollowResponse.builder().id(follower.getToUser().getId())
                .name(follower.getToUser().getName().getName()).image(follower.getToUser().getS3Image().getImagePath()).build()));
        return result;
    }

    @Transactional
    public void unFollow(Long id){
        User fromUser=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        User toUser=userRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_FOLLOW));
        fromUser.getFollows().remove(follow);
        toUser.getFollowers().remove(follow);
        followRepository.delete(follow);
    }
    @Transactional
    public void deleteFollower(Long id){
        User toUser=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        User fromUser=userRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Follow follow = followRepository.findByFromUserAndToUser(fromUser, toUser)
                .orElseThrow(()->new CustomException(ErrorCode.NOT_FOUND_FOLLOW));
        followRepository.delete(follow);
    }
}
