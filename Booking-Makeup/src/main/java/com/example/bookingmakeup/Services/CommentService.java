package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.Comment;
import com.example.bookingmakeup.Repositories.ICommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CommentService implements ICommentService {
    @Autowired
    private ICommentRepository commentRepository;

    public List<Comment> getCommentsByMakeupArtistId(Long makeupArtistId) {
        return commentRepository.findByMakeupArtist_MakeupArtistId(makeupArtistId);
    }
    @Override
    public Comment saveComment(Comment comment) {
        System.out.println("Bình luận trước khi lưu: " + comment.getCmt());
        return commentRepository.save(comment);
    }

    @Override
    public Optional<Comment> getCommentById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public void deleteCommentById(Long id) {
        if (commentRepository.existsById(id)) {
            commentRepository.deleteById(id);
        } else {
            throw new RuntimeException("Không tìm thấy bình luận với ID: " + id);
        }
    }
}
