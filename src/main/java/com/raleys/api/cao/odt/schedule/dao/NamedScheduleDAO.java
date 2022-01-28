package com.raleys.api.cao.odt.schedule.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.raleys.api.cao.odt.schedule.model.NamedSchedule;

@Repository
public interface NamedScheduleDAO extends JpaRepository<NamedSchedule, Integer> {
	NamedSchedule findByName(String name);
}
