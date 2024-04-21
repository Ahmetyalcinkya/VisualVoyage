package com.vv.VisualVoyage.services.concretes;

import com.vv.VisualVoyage.dtos.requests.ReelSaveDto;
import com.vv.VisualVoyage.dtos.responses.ReelResponse;
import com.vv.VisualVoyage.entities.Reel;
import com.vv.VisualVoyage.entities.User;
import com.vv.VisualVoyage.repositories.ReelRepository;
import com.vv.VisualVoyage.repositories.UserRepository;
import com.vv.VisualVoyage.services.abstracts.ReelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReelManager implements ReelService {

    private ReelRepository reelRepository;
    private UserRepository userRepository;

    @Autowired
    public ReelManager(ReelRepository reelRepository, UserRepository userRepository) {
        this.reelRepository = reelRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ReelResponse createReel(ReelSaveDto reelSaveDto, long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!
        Reel reel = Reel.builder()
                .title(reelSaveDto.getTitle())
                .video(reelSaveDto.getVideo())
                .createdAt(LocalDateTime.now())
                .user(user)
                .build();
        Reel saved = reelRepository.save(reel);
        return ReelResponse.builder()
                .id(saved.getId())
                .title(saved.getTitle())
                .video(saved.getVideo())
                .createdAt(saved.getCreatedAt())
                .userId(saved.getUser().getId())
                .build();
    }

    @Override
    public List<ReelResponse> findAllReels() {
        List<Reel> reels = reelRepository.findAll();

        return reels.stream().map(reel -> ReelResponse.builder()
                .id(reel.getId())
                .title(reel.getTitle())
                .video(reel.getVideo())
                .createdAt(reel.getCreatedAt())
                .userId(reel.getUser().getId())
                .build()).toList();
    }

    @Override
    public List<ReelResponse> findReelsByUser(long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found!")); //TODO GetAuthenticatedUser method must be added!
        List<Reel> reels = reelRepository.findReelsByUserId(user.getId());

        return reels.stream().map(reel -> ReelResponse.builder()
                .id(reel.getId())
                .title(reel.getTitle())
                .video(reel.getVideo())
                .createdAt(reel.getCreatedAt())
                .userId(reel.getUser().getId())
                .build()).toList();
    }
}
