package com.jaks1m.project.service.community;


import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.user.Friend;
import com.jaks1m.project.domain.entity.user.User;
import com.jaks1m.project.domain.error.ErrorCode;
import com.jaks1m.project.domain.exception.CustomException;
import com.jaks1m.project.dto.community.response.FriendResponseDto;
import com.jaks1m.project.repository.user.FriendsRepository;
import com.jaks1m.project.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FriendService {
    private final UserRepository userRepository;
    private final FriendsRepository friendsRepository;
    @Transactional
    public void addFriend(Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        User findUser=userRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Friend findFriend = friendsRepository.findByFriend(findUser);
        if(findFriend==null) {
            findFriend = Friend.builder().friend(findUser).build();
            friendsRepository.save(findFriend);
        }
        if(user.getFriends().contains(findFriend)){
            throw new CustomException(ErrorCode.ALREADY_FRIEND);
        }
        user.addFriends(findFriend);
    }

    public List<FriendResponseDto> getFriends(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Friend> friends = user.getFriends();
        List<FriendResponseDto> result=new ArrayList<>();
        friends.forEach(friend -> result.add(FriendResponseDto.builder().id(friend.getFriend().getId())
                .name(friend.getFriend().getName().getName()).image(friend.getFriend().getS3Image().getImagePath()).build()));
        return result;
    }

    @Transactional
    public void deleteFriend(Long id){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        User findUser = userRepository.findById(id)
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        Friend friend = friendsRepository.findByFriend(findUser);
        if(friend==null){
            throw new CustomException(ErrorCode.NOT_FOUND_FRIEND);
        }
        user.deleteFriends(friend);
    }
}
