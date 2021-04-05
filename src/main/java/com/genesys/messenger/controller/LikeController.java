package com.genesys.messenger.controller;

import com.genesys.messenger.exception.UserBadRequestException;
import com.genesys.messenger.model.Like;
import com.genesys.messenger.model.Message;
import com.genesys.messenger.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
public class LikeController {
    @Autowired
    private LikeService likeService;

    @PostMapping("/likes")
    public ResponseEntity<Like> LikeMessage(@RequestBody Like like) {
        try {
            Like liked = likeService.save(like);
            return ResponseEntity.created(new URI("/likes/" + like.getId())).build();
        } catch (Exception e) {
            throw new UserBadRequestException(e.getMessage());
        }
    }

    @GetMapping("/likes")
    public ResponseEntity<?> findAllLikes () {
        List<Like> likes = likeService.findAllLikes();
        if(likes == null || likes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(likes);
    }
}
