package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.MakeupArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IMakeupArtistRepository extends JpaRepository<MakeupArtist, Long> {
    Optional<MakeupArtist> findByMakeupArtistId(Long makeupArtistId);
}
