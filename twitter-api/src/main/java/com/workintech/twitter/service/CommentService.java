package com.workintech.twitter.service;

import com.workintech.twitter.entity.Comment;

public interface CommentService {

    Comment createComment(Long tweetId,Comment comment);
    Comment updateComment(Long commentId,Comment comment);
    Comment deleteComment(Long commentId);

}
