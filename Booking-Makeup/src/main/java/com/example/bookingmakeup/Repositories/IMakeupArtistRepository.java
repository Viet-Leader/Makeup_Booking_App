package com.example.bookingmakeup.Repositories;

import com.example.bookingmakeup.Models.MakeupArtist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IMakeupArtistRepository extends JpaRepository<MakeupArtist, Integer> {
}
