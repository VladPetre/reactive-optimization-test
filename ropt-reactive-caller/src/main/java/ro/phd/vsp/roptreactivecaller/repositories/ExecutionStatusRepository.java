package ro.phd.vsp.roptreactivecaller.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import ro.phd.vsp.roptreactivecaller.models.ExecutionStatus;

public interface ExecutionStatusRepository extends
    ReactiveCrudRepository<ExecutionStatus, Integer> {

}
