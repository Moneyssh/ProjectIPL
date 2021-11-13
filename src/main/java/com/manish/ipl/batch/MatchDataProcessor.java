package com.manish.ipl.batch;

import java.text.SimpleDateFormat;
import java.time.LocalDate;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;

import com.manish.ipl.data.MatchInput;
import com.manish.ipl.model.Match;

public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {

	private static final Logger log = LogManager.getLogger(MatchDataProcessor.class);

	@Override
	public Match process(final MatchInput matchInput) throws Exception {

		final Match match = new Match();

		match.setId(Long.parseLong(matchInput.getId()));
		match.setCity(matchInput.getCity());
		match.setMatchDate(new SimpleDateFormat("dd-mm-yyyy").parse(matchInput.getDate()));
		match.setPlayerOfMatch(matchInput.getPlayer_of_match());
		match.setVenue(matchInput.getVenue());

		String firstInningTeam, secondInningTeam;

		if ("bat".equals(matchInput.getToss_decision())) {
			firstInningTeam = matchInput.getToss_winner();
			secondInningTeam = firstInningTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();
		} else {
			secondInningTeam = matchInput.getToss_winner();
			firstInningTeam = secondInningTeam.equals(matchInput.getTeam1()) ? matchInput.getTeam2()
					: matchInput.getTeam1();
		}
		match.setTeam1(firstInningTeam);
		match.setTeam2(secondInningTeam);
		match.setTossDecision(matchInput.getToss_decision());
		match.setTossWinner(matchInput.getToss_winner());
		match.setMatchWinner(matchInput.getWinner());
		match.setMatchResult(matchInput.getResult());
		match.setResultMargin(matchInput.getResult_margin());
		match.setUmpire1(matchInput.getUmpire1());
		match.setUmpire2(matchInput.getUmpire2());

		log.info("Converting (" + matchInput + ") into (" + match + ")");

		return match;
	}

}
