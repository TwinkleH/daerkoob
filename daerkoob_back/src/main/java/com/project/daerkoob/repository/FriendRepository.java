package com.project.daerkoob.repository;

import com.project.daerkoob.domain.Friend;
import com.project.daerkoob.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface FriendRepository extends JpaRepository<Friend , Long> {
    Boolean existsByUserAndFriendIndex(User user, Long friendIndex);
    List<Friend> findByUser(User user);
    @Transactional
    void deleteByFriendIndex(Long friendIndex);
}
