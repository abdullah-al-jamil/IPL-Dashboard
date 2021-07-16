package com.javaproject.ipldashboard.repository;


import java.time.LocalDate;
import java.util.List;

import com.javaproject.ipldashboard.Model.Match;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface MatchRepository extends CrudRepository<Match, Long>{
   
    @Query("select m from Match m where (m.team1 = :teamName or m.team2 = :teamName) and m.date between :startDate and :endDate order by date desc")
    List<Match>getMactesByTeamBetweenDates(
        @Param("teamName") String teamName, 
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
    
     List<Match>getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);
    // List<Match>getByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
    //     String teamName1, LocalDate date1, LocalDate Date2,
    //     String teamName2, LocalDate date3, LocalDate date4
    // );

    default List<Match>findLatestMatchesByTeam(String teamName, int count)
    {
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, PageRequest.of(0, count));
    }
}
