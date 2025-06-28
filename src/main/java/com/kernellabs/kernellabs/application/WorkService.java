package com.kernellabs.kernellabs.application;

import com.kernellabs.kernellabs.domain.Work;
import com.kernellabs.kernellabs.infrastructure.WorkRepository;
import com.kernellabs.kernellabs.presentation.dto.request.SurveyRequest;
import com.kernellabs.kernellabs.presentation.dto.response.SurveyResponse;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class WorkService {
    private final WorkRepository placeRepo;

    public SurveyResponse recommend(SurveyRequest req) {
        // 1) 사용자 벡터(u) 계산 (기존 코드 유지)
        double[] u = new double[8];
        u[0] = "A".equals(req.getQ1()) ? 1 : 0;
        u[1] = "B".equals(req.getQ1()) ? 1 : 0;
        u[2] = "C".equals(req.getQ1()) ? 1 : 0;
        u[3] = "D".equals(req.getQ1()) ? 1 : 0;
        u[4] = switch(req.getQ2()) { case "A"->0.1; case "B"->0.3; case "C"->0.5; case "D"->0.75; default->1.0; };
        u[5] = switch(req.getQ3()) { case "A"->0.0; case "B"->0.33; case "C"->0.66; default->1.0; };
        u[6] = switch(req.getQ4()) { case "A"->0.0; case "B"->0.5; case "C"->1.0; default->0.0; };
        u[7] = "A".equals(req.getQ5()) ? 1 : 0;

        // 2) 모든 장소 스코어링 → 최고 1개 pick
        Work best = placeRepo.findAll().stream()
            .map(p -> Map.entry(p, calcScore(u, p)))
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RuntimeException("추천할 장소가 없습니다"));

        return SurveyResponse.from(best);
    }

    /**
     * 차이 기반 유사도 계산
     * - 이진(one-hot) feature: dot-product
     * - 연속(norm) feature: 1 - |pref - actual|
     * - pref[u6]==0 (상관없음)이면 위치 점수 full(1점)
     * - priv bonus: 필요하고 가능하면 +1
     */
    private double calcScore(double[] u, Work p) {
        double score = 0;

        // 1) 업무 유형(dot)
        double[] vType = {
            p.getWorkDev()?1:0,
            p.getWorkDoc()?1:0,
            p.getWorkMeet()?1:0,
            p.getWorkCall()?1:0
        };
        for (int i = 0; i < 4; i++) {
            score += u[i] * vType[i];
        }

        // 2) 시간 적합도
        score += 1 - Math.abs(p.getHoursNorm()   - u[4]);

        // 3) 붐빔 적합도
        score += 1 - Math.abs(p.getCrowdNorm()   - u[5]);

        // 4) 위치 적합도 (상관없음 → full 점수)
        if (u[6] == 0) {
            score += 1;
        } else {
            score += 1 - Math.abs(p.getLocationNorm() - u[6]);
        }

        // 5) 독립 공간 필요 보너스
        if (u[7] == 1 && p.getPrivateAvailable()) {
            score += 1;
        }

        return score;
    }

}
