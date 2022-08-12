package com.jaks1m.project.service.community;


import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.follow.Follow;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.dto.community.response.FriendResponseDto;
import com.jaks1m.project.repository.user.FollowRepository;
import com.jaks1m.project.repository.user.UserRepository;
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
        toUser.getFollowers().add(follow);
        fromUser.getFollows().add(follow);
        followRepository.save(follow);
    }

    public List<FriendResponseDto> getFollowers(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Follow> followers = user.getFollowers();
        List<FriendResponseDto> result=new ArrayList<>();
        followers.forEach(follower -> result.add(FriendResponseDto.builder().id(follower.getToUser().getId())
                .name(follower.getToUser().getName().getName()).image(follower.getToUser().getS3Image().getImagePath()).build()));
        return result;
    }

    public List<FriendResponseDto> getFollows(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Follow> follows = user.getFollows();
        List<FriendResponseDto> result=new ArrayList<>();
        follows.forEach(follower -> result.add(FriendResponseDto.builder().id(follower.getToUser().getId())
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
        toUser.getFollowers().remove(follow);
        fromUser.getFollows().remove(follow);
    }
}
