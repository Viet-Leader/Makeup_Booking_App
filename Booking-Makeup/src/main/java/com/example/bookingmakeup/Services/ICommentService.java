package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Comment;


import java.util.List;
import java.util.Optional;

public interface ICommentService {
    List<Comment> getCommentsByMakeupArtistId(Long makeupArtistId);
    Comment saveComment(Comment comment);
    Optional<Comment> getCommentById(Long id);
    void deleteCommentById(Long id);
}
