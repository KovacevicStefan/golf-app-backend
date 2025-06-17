package golfResults.tournament;

import golfResults.par.ParDTO;

import java.util.Date;
import java.util.List;

public record TournamentRequestDTO(
        String name,
        String location,
        String description,
        Integer roundNumber,
        Integer holeNumber,
        Boolean status,
        Date startDate,
        Date endDate,
        List<ParDTO> pars
) {
}
