package com.example.bookingmakeup.Services;

import com.example.bookingmakeup.Models.MakeupArtist;
import com.example.bookingmakeup.Repositories.IMakeupArtistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MakeupArtistService implements IMakeupArtistService {

    @Autowired
    private IMakeupArtistRepository IMakeupArtistRepository;

    @Override
    public List<MakeupArtist> getAllMakeupArtists() {
        List<MakeupArtist> artists = IMakeupArtistRepository.findAll();
        System.out.println("Danh sách từ DB: " + artists); // Debug để xem dữ liệu có được lấy ra không
        return artists;
    }

    @Override
    public Optional<MakeupArtist> getMakeupArtistByMakeupArtistId(Long makeupArtistId) {
        return IMakeupArtistRepository.findById(makeupArtistId);
    }


    @Override
    public void saveMakeupArtist(MakeupArtist makeupArtist) {
        IMakeupArtistRepository.save(makeupArtist);
    }

    @Override
    public void deleteMakeupArtist(Long id) {
        IMakeupArtistRepository.deleteById(id);
    }

}
