package com.project.daerkoob.service;

import com.project.daerkoob.domain.Friend;
import com.project.daerkoob.domain.User;
import com.project.daerkoob.model.MessageWithFriendList;
import com.project.daerkoob.repository.FriendRepository;
import com.project.daerkoob.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;
import java.util.List;

@Service
public class FriendService {

    FriendRepository friendRepository;
    UserRepository userRepository;

    public FriendService(FriendRepository friendRepository , UserRepository userRepository){
        this.friendRepository = friendRepository;
        this.userRepository = userRepository;
    }

    public List<Friend> ask(Long userId){
        return friendRepository.findByUser(userRepository.findById(userId).get());
    }

    public MessageWithFriendList add(Long userId, Long friendId){ // 친구 추가
        if (friendRepository.existsByUserAndFriendIndex(userRepository.findById(userId).get(),friendId)){
            return new MessageWithFriendList(false , "이미 친구입니다." , ask(userId));
        }
        else if(userId == friendId){
            return new MessageWithFriendList(false , "자신은 친구로 추가할 수 없습니다." , ask(userId));
        }
        Optional<User> findById = userRepository.findById(userId);
        User resultUser = findById.get();
        resultUser.setFriendCount(resultUser.getFriendCount() + 1);
        userRepository.save(resultUser); //친구 수 바꾸고 저장 (업데이트)
        friendRepository.save(createFriend(userId, friendId)); // 친구로 저장
        return new MessageWithFriendList(true , "친구 등록에 성공했습니다.", ask(userId));
    }

    public Friend createFriend(Long userId , Long friendId){ //friend 객체를 만드는 함수
        Friend newFriend = new Friend();
        newFriend.setFriendIndex(friendId);
        newFriend.setFriendNickName(userRepository.findById(friendId).get().getNickName());
        newFriend.setUser(userRepository.findById(userId).get());
        return newFriend;
    }
}