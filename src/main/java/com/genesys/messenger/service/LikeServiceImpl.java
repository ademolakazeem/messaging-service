package com.genesys.messenger.service;

import com.genesys.messenger.model.Like;
import com.genesys.messenger.repository.LikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeServiceImpl implements LikeService{
@Autowired
private LikeRepository likeRepository;

    @Override
    public Like save(Like like) {
        likeRepository.save(like);
        return like;
    }

    @Override
    public List<Like> findAllLikes() {
        return likeRepository.findAll();
    }
}
