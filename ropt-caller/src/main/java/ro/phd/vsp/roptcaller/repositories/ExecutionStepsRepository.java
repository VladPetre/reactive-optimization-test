package ro.phd.vsp.roptcallerreactive.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ro.phd.vsp.roptcallerreactive.models.ExecutionStep;

public interface ExecutionStepsRepository extends JpaRepository<ExecutionStep, Integer> {


}
