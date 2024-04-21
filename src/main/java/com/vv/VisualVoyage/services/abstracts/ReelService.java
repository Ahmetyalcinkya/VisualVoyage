package com.vv.VisualVoyage.services.abstracts;

import com.vv.VisualVoyage.dtos.requests.ReelSaveDto;
import com.vv.VisualVoyage.dtos.responses.ReelResponse;

import java.util.List;

public interface ReelService {

    ReelResponse createReel(ReelSaveDto reelSaveDto, long userId);
    List<ReelResponse> findAllReels();
    List<ReelResponse> findReelsByUser(long userId);

}
