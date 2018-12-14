package com.belatrix.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.belatrix.entity.Log;

@Repository("logRepository")
public interface LogRepository extends JpaRepository<Log, Object>{

}
