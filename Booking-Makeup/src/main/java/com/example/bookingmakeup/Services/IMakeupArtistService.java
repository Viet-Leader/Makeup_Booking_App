package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.MakeupArtist;
import java.util.List;
import java.util.Optional;

public interface IMakeupArtistService {
    List<MakeupArtist> getAllMakeupArtists();
    Optional<MakeupArtist> getMakeupArtistById(Integer id);
    void saveMakeupArtist(MakeupArtist makeupArtist);
    void deleteMakeupArtist(Integer id);
}
