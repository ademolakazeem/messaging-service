package com.genesys.messenger.service;

import com.genesys.messenger.model.Like;

import java.util.List;

public interface LikeService {
Like save(Like like);
List<Like> findAllLikes();
}
