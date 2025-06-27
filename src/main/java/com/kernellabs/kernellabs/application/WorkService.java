package com.kernellabs.kernellabs.application;

import com.kernellabs.kernellabs.domain.Work;
import com.kernellabs.kernellabs.infrastructure.WorkRepository;
import com.kernellabs.kernellabs.presentation.dto.request.SurveyRequest;
import com.kernellabs.kernellabs.presentation.dto.response.SurveyResponse;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkService {
    private final WorkRepository placeRepo;


    public SurveyResponse recommend(SurveyRequest req) {
        // 1) 사용자 벡터 계산
        double[] u = new double[8];
        // 업무 유형 one-hot
        u[0] = "A".equals(req.getQ1()) ? 1 : 0; // Dev
        u[1] = "B".equals(req.getQ1()) ? 1 : 0; // Doc
        u[2] = "C".equals(req.getQ1()) ? 1 : 0; // Meet
        u[3] = "D".equals(req.getQ1()) ? 1 : 0; // Call
        // 시간
        u[4] = switch(req.getQ2()) {
            case "A" -> 0.1;
            case "B" -> 0.3;
            case "C" -> 0.5;
            case "D" -> 0.75;
            default  -> 1.0;
        };
        // 밀집도
        u[5] = switch(req.getQ3()) {
            case "A" -> 0.0;
            case "B" -> 0.33;
            case "C" -> 0.66;
            default  -> 1.0;
        };
        // 조용⇆관광
        u[6] = switch(req.getQ4()) {
            case "A" -> 0.0;
            case "B" -> 0.5;
            default  -> 1.0;  // C→1.0, D→1.0(혹은 0으로 처리)
        };
        // 독립 공간
        u[7] = "A".equals(req.getQ5()) ? 1 : 0;

        // 2) DB에서 모든 장소 가져와서 내적 계산
        List<Work> places = placeRepo.findAll();
        Work best = null;
        double bestScore = Double.NEGATIVE_INFINITY;

        for (Work p : places) {
            double[] v = {
                p.getWorkDev() ? 1:0,
                p.getWorkDoc() ? 1:0,
                p.getWorkMeet()? 1:0,
                p.getWorkCall()?1:0,
                p.getHoursNorm(),
                p.getCrowdNorm(),
                p.getLocationNorm(),
                p.getPrivateAvailable()?1:0
            };
            double score = 0;
            for (int i = 0; i < u.length; i++) {
                score += u[i] * v[i];
            }
            if (score > bestScore) {
                bestScore = score;
                best = p;
            }
        }
        return SurveyResponse.from(best);
    }

}
