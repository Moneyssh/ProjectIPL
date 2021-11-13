package com.manish.ipl.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.manish.ipl.model.Match;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long>{

	@Query(value = "select distinct (city) from match_table mt order by city ", nativeQuery = true)
	List<String> findAllCity();

	@Query(value = "select * from match_table mt where mt.city =? ", nativeQuery = true)
	List<Match> findMatchesInCity(String city);

}
