package org.example.pollingsystem.controller;

import org.example.pollingsystem.entity.Poll;
import org.example.pollingsystem.entity.Option;
import org.example.pollingsystem.repository.PollRepository;
import org.example.pollingsystem.repository.OptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/polls")
public class PollController {

    @Autowired
    private PollRepository pollRepository;

    @Autowired
    private OptionRepository optionRepository;

    // Create a new poll
    @PostMapping
    public ResponseEntity<Poll> createPoll(@RequestBody Poll poll) {
        poll.getOptions().forEach(option -> option.setPoll(poll));
        Poll savedPoll = pollRepository.save(poll);
        return ResponseEntity.ok(savedPoll);
    }

    // Get all polls
    @GetMapping
    public ResponseEntity<List<Poll>> getAllPolls() {
        List<Poll> polls = pollRepository.findAll();
        return ResponseEntity.ok(polls);
    }

    // Get a specific poll by ID
    @GetMapping("/{pollId}")
    public ResponseEntity<Poll> getPollById(@PathVariable Long pollId) {
        Optional<Poll> poll = pollRepository.findById(pollId);
        return poll.map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // Vote on an option in a poll
    @PostMapping("/{pollId}/vote")
    public ResponseEntity<String> vote(@PathVariable Long pollId, @RequestBody Long optionId) {
        Optional<Option> option = optionRepository.findById(optionId);
        if (option.isPresent() && option.get().getPoll().getId().equals(pollId)) {
            Option selectedOption = option.get();
            selectedOption.setVotes(selectedOption.getVotes() + 1);
            optionRepository.save(selectedOption);
            return ResponseEntity.ok("Vote cast successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid poll or option");
        }
    }

    // Delete a poll
    @DeleteMapping("/{pollId}")
    public ResponseEntity<String> deletePoll(@PathVariable Long pollId) {
        if (pollRepository.existsById(pollId)) {
            pollRepository.deleteById(pollId);
            return ResponseEntity.ok("Poll deleted successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
