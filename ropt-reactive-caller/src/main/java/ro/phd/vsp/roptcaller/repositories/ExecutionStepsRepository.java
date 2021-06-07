package ro.phd.vsp.roptcaller.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.phd.vsp.roptcaller.models.ExecutionStep;

public interface ExecutionStepsRepository extends JpaRepository<ExecutionStep, Integer> {


}
