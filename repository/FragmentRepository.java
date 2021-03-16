package com.bot.someweire.repository;

import com.bot.someweire.model.Fragment;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface FragmentRepository extends ReactiveNeo4jRepository<Fragment, Long> {

  Flux<Fragment> findByTypeLikeIgnoreCase(String type);

  Flux<Fragment> findByQuantite(int quantite);


  @Query("match (c:Character)-[r:HAS]->(:Fragment)-[:IN_CACHE]->(:Cache)-[:IN_LIEU]->(:Lieu)-[:IN_REGION]->(:Region)-[:IN_WORLD]->(w:World) "
      + "WHERE c.userId=$userId AND id(w)=$worldId return sum(r.left)")
  Mono<Integer> findTotalFragmentsLeftByUserIdAndWorldId(String userId, Long worldId);

  @Query("MATCH (c:Character), (f:Fragment) WHERE c.userId=$userId AND id(f)=$fragmentId RETURN EXISTS((c)-[:HAS]->(f))")
  Mono<Boolean> checkFragmentOwnedByPlayer(String userId, Long fragmentId);
}
