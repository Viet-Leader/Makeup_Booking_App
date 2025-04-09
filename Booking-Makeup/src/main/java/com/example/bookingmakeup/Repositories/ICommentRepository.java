package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.Comment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"customer", "customer.user"})
    List<Comment> findByMakeupArtist_MakeupArtistId(Long makeupArtistId);
}

