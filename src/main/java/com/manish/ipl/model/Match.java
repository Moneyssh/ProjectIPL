package com.manish.ipl.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "match_table")
public class Match {
	
	@Id
	@NotNull
	private Long id;
	@NotNull
	private String city;
	@NotNull
	private Date matchDate;
	@NotNull
	@Size(min = 5, max = 30, message = "Make sure that 30< Field length >5")
	private String playerOfMatch;
	@NotNull
	private String venue;
	@NotNull
	@Size(min = 5, max = 30, message = "Make sure that 30< Field length >5")
	private String team1;
	@NotNull
	@Size(min = 5, max = 30, message = "Make sure that 30< Field length >5")
	private String team2;
	@NotNull
	@Size(min = 5, max = 30, message = "Make sure that 30< Field length >5")
	private String tossWinner;
	@NotNull
	private String tossDecision;
	@NotNull
	@Size(min = 5, max = 30, message = "Make sure that 30< Field length >5")
	private String matchWinner;
	@NotNull
	private String matchResult;
	@NotNull
	private String resultMargin;
	@NotNull
	@Size(min = 5, max = 30, message = "Make sure that 30< Field length >5")
	private String umpire1;
	@NotNull
	@Size(min = 5, max = 30, message = "Make sure that 30< Field length >5")
	private String umpire2;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getMatchDate() {
		return matchDate;
	}
	public void setMatchDate(Date date) {
		this.matchDate = date;
	}
	public String getPlayerOfMatch() {
		return playerOfMatch;
	}
	public void setPlayerOfMatch(String playerOfMatch) {
		this.playerOfMatch = playerOfMatch;
	}
	public String getVenue() {
		return venue;
	}
	public void setVenue(String venue) {
		this.venue = venue;
	}
	public String getTeam1() {
		return team1;
	}
	public void setTeam1(String team1) {
		this.team1 = team1;
	}
	public String getTeam2() {
		return team2;
	}
	public void setTeam2(String team2) {
		this.team2 = team2;
	}
	public String getTossWinner() {
		return tossWinner;
	}
	public void setTossWinner(String tossWinner) {
		this.tossWinner = tossWinner;
	}
	public String getTossDecision() {
		return tossDecision;
	}
	public void setTossDecision(String tossDecision) {
		this.tossDecision = tossDecision;
	}
	public String getMatchWinner() {
		return matchWinner;
	}
	public void setMatchWinner(String matchWinner) {
		this.matchWinner = matchWinner;
	}
	public String getMatchResult() {
		return matchResult;
	}
	public void setMatchResult(String result) {
		this.matchResult = result;
	}
	public String getResultMargin() {
		return resultMargin;
	}
	public void setResultMargin(String resultMargin) {
		this.resultMargin = resultMargin;
	}
	public String getUmpire1() {
		return umpire1;
	}
	public void setUmpire1(String umpire1) {
		this.umpire1 = umpire1;
	}
	public String getUmpire2() {
		return umpire2;
	}
	public void setUmpire2(String umpire2) {
		this.umpire2 = umpire2;
	}

}
