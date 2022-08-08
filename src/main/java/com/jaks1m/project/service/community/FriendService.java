package com.jaks1m.project.service.community;


import com.jaks1m.project.config.security.SecurityUtil;
import com.jaks1m.project.domain.entity.user.Friends;
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
        User receiveUser=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        User sendUser=userRepository.findById(id).orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        addFriend(receiveUser,sendUser);
        addFriend(sendUser,receiveUser);
    }

    public List<FriendResponseDto> getFriends(){
        User user=userRepository.findByEmail(SecurityUtil.getCurrentUserEmail())
                .orElseThrow(()-> new CustomException(ErrorCode.NOT_FOUND_USER));
        List<Friends> friends = user.getFriends();
        List<FriendResponseDto> result=new ArrayList<>();
        friends.forEach(friend -> result.add(FriendResponseDto.builder().id(friend.getId()).name(friend.getName()).build()));
        return result;
    }
    private void addFriend(User receiveUser,User sendUser){
        Optional<Friends> friends = friendsRepository.findById(receiveUser.getId());
        if(friends.isEmpty()){
            Friends friend=Friends.builder().id(receiveUser.getId()).name(receiveUser.getName().getName()).build();
            friendsRepository.save(friend);
            sendUser.addFriends(friend);
        }else{
            sendUser.addFriends(friends.get());
        }
    }
}
