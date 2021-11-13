package com.manish.ipl.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.manish.ipl.constants.MatchConstants;
import com.manish.ipl.exception.MatchException;
import com.manish.ipl.model.Match;
import com.manish.ipl.repository.MatchRepository;
/**
 * 
 * @author manish.kumar14
 *
 *This is a service class for match operations. It contains the main business logic.
 */

@Service
public class MatchService {

	@Autowired
	MatchRepository matchRepository;
	
	public List<String> getAllCities(){
		return matchRepository.findAllCity();
	}

	public List<Match> getMatchesInCity(String city) {
		List<Match> matchList;
		if(getAllCities().contains(city) == false) {
			throw new MatchException(MatchConstants.ERROR_CODE_803, MatchConstants.MSG_803+city);
		} 
			matchList = matchRepository.findMatchesInCity(city);
			return matchList;		
	}

	public Match addMatch(Match matchRecord) {
		if(matchRecord.getCity().isEmpty() || matchRecord.getCity().length() ==0
			|| matchRecord.getId() == null
			|| matchRecord.getMatchWinner().isEmpty() || matchRecord.getMatchWinner().length() ==0
			|| matchRecord.getPlayerOfMatch().isEmpty() || matchRecord.getPlayerOfMatch().length() ==0
			|| matchRecord.getMatchResult().isEmpty() || matchRecord.getMatchResult().length() ==0
			|| matchRecord.getResultMargin().isEmpty() || matchRecord.getResultMargin().length() ==0
			|| matchRecord.getTeam1().isEmpty() || matchRecord.getTeam1().length() ==0
			|| matchRecord.getTeam2().isEmpty() || matchRecord.getTeam2().length() ==0
			|| matchRecord.getTossDecision().isEmpty() || matchRecord.getTossDecision().length() ==0
			|| matchRecord.getTossWinner().isEmpty() || matchRecord.getTossWinner().length() ==0
			|| matchRecord.getUmpire1().isEmpty() || matchRecord.getUmpire1().length() ==0
			|| matchRecord.getUmpire2().isEmpty() || matchRecord.getUmpire2().length() ==0
			|| matchRecord.getVenue().isEmpty() || matchRecord.getVenue().length() ==0) {
			throw new MatchException(MatchConstants.ERROR_CODE_801, MatchConstants.MSG_801);
		}
		return matchRepository.save(matchRecord);
	}

	public Match updateMatchRecord(Match matchrecord) {
		if(matchRepository. existsById(matchrecord.getId())){
			
		}
		return null;
	}

	public List<Match> getAllMatches() {
		
		List<Match> matchList = matchRepository.findAll();
		if(matchList.isEmpty()) {
			throw new MatchException(MatchConstants.ERROR_CODE_802, MatchConstants.MSG_802);
		}
		return matchList;
	}

	public Match deleteMatch(Long id) {
		Match m= matchRepository.findById(id).get();
		matchRepository.deleteById(id);
		return m;
	}
	
	
	
	
}
