package ro.phd.vsp.roptreactivecaller.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ro.phd.vsp.roptreactivecaller.models.ExecutionStep;

public interface ExecutionStepsRepository extends ReactiveCrudRepository<ExecutionStep, Integer> {

}
