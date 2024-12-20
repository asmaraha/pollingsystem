package org.example.pollingsystem.service;

import org.example.pollingsystem.entity.Poll;
import org.example.pollingsystem.entity.Option;
import org.example.pollingsystem.repository.PollRepository;
import org.example.pollingsystem.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PollService {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;

    // Create a new poll
    public Poll createPoll(Poll poll) {
        poll.getOptions().forEach(option -> option.setPoll(poll)); // Link options to poll
        return pollRepository.save(poll);
    }

    // Get all polls
    public List<Poll> getAllPolls() {
        return pollRepository.findAll();
    }

    // Get a poll by ID
    public Optional<Poll> getPollById(Long pollId) {
        return pollRepository.findById(pollId);
    }

    // Delete a poll
    public void deletePoll(Long pollId) {
        if (pollRepository.existsById(pollId)) {
            pollRepository.deleteById(pollId);
        }
    }

    // Vote on an option
    public String vote(Long pollId, Long optionId) {
        Optional<Option> option = optionRepository.findById(optionId);
        if (option.isPresent() && option.get().getPoll().getId().equals(pollId)) {
            Option selectedOption = option.get();
            selectedOption.setVotes(selectedOption.getVotes() + 1);
            optionRepository.save(selectedOption);
            return "Vote cast successfully";
        } else {
            return "Invalid poll or option";
        }
    }
}
