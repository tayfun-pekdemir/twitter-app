package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;

public interface CommentService {

    Comment createComment(Long userId,Long tweetId,Comment comment);
    Comment updateComment(Long commentId,Long userId,Comment comment);
    Comment deleteComment(Long commentId,Long userId);

}
