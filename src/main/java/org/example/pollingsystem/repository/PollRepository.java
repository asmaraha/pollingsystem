package org.example.pollingsystem.repository;

import org.example.pollingsystem.entity.Poll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PollRepository extends JpaRepository<Poll, Long> {
}