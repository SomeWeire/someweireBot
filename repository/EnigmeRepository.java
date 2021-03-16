package com.bot.someweire.repository;

import com.bot.someweire.model.Enigme;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EnigmeRepository extends ReactiveNeo4jRepository<Enigme, Long> {

  @Query("MATCH(n:Enigme)-[:IN_CACHE]->(:Cache)-[:IN_LIEU]->(:Lieu)-[:IN_REGION]->(:Region)-[:IN_WORLD]->(w:World) WHERE id(w)=$worldId AND n.numero=$numero "
      + "return n{__internalNeo4jId__: id(n), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n), "
      + "Enigme_TO_ENIGME_Indice: [(n)<-[__relationship__:TO_ENIGME]-(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}]}")
  Mono<Enigme> findByNumero(int numero, Long worldId);

  @Query("MATCH (n:Enigme)<-[r:HAS]-(c:Character) where c.userId=$userId and r.solved=true "
      + "return n{__internalNeo4jId__: id(n), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n), "
      + "Enigme_TO_ENIGME_Indice: [(n)<-[__relationship__:TO_ENIGME]-(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}]}")
  Flux<Enigme> findAllSolvedEnigmesByCharacter(String userId);

  @Query("MATCH (n:Enigme)<-[r:HAS]-(c:Character) where c.userId=$userId and r.solved=false "
      + "return n{__internalNeo4jId__: id(n), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n), "
      + "Enigme_TO_ENIGME_Indice: [(n)<-[__relationship__:TO_ENIGME]-(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}]}")
  Flux<Enigme> findAllUnsolvedEnigmesByCharacter(String userId);

  @Query("MATCH (w:World)<-[:IN_WORLD]-(:Region)<-[:IN_REGION]-(:Lieu)<-[:IN_LIEU]-(:Cache)<-[:IN_CACHE]-(n:Enigme)<-[r:HAS]-(c:Character) "
      + "where c.userId=$userId AND id(w)=$worldId return n{__internalNeo4jId__: id(n), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n),"
      + "Enigme_TO_ENIGME_Indice: [(n)<-[__relationship__:TO_ENIGME]-(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}]}")
  Flux<Enigme> findAllEnigmesByCharacterAndWorld(String userId, Long worldId);

  @Query("MATCH (n:Enigme)<-[r:HAS]-(c:Character) where c.userId=$userId and id(n)=$enigmeId return r.solved")
  Mono<Boolean> HasSolvedByCharacter(String userId, Long enigmeId);

  @Query("MATCH (c:Character), (d:Enigme) WHERE c.userId=$userid AND id(d)=$enigmeid RETURN EXISTS((c)-[:HAS]->(d))")
  Mono<Boolean> HasEnigmeByCharacter(String userid, Long enigmeid);

  @Query("MATCH(n:Enigme)-[:IN_CACHE]->(:Cache)-[:IN_LIEU]->(:Lieu)-[:IN_REGION]->(:Region)-[:IN_WORLD]->(w:World) "
      + "WHERE id(w)=$worldid return n{__internalNeo4jId__: id(n), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n),"
      + "Enigme_TO_ENIGME_Indice: [(n)<-[__relationship__:TO_ENIGME]-(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}]}")
  Flux<Enigme> findAllEnigmesByWorld(Long worldid);

  @Query("MATCH (e:Enigme)<-[r:HAS]-(c:Character) WHERE id(e)=$enigmeId and c.userId=$userId SET r.tries=r.tries+1 return $solution =~ '(?i).*'+e.solution+'.*'")
  Mono<Boolean> trySolveEnigme(String solution, Long enigmeId, String userId);

  @Query("MATCH (n:Enigme)<-[r:HAS]-(c:Character) where c.userId=$userId and id(n)=$enigmeId return r.tries")
  Mono<Integer> findTriesByCharacter(String userId, Long enigmeId);

  @Query("MATCH (n:Enigme)<-[r:HAS]-(c:Character) where c.userId=$userId and id(n)=$enigmeId SET r.tries=$tries RETURN r.tries")
  Mono<Integer> updateTriesByCharacter(String userId, Long enigmeId, int tries);

  @Query("MATCH (n:Enigme)<-[r:HAS]-(c:Character) where c.userId=$userId and id(n)=$enigmeId SET r.forfeit=true, c.forfeitPower=true RETURN r.forfeit")
  Mono<Boolean> forfeitByCharacter(String userId, Long enigmeId);

  @Query("MATCH (n:Enigme) WHERE id(n)=$enigmeId RETURN n.solution")
  Mono<String> findEnigmeSolution(Long enigmeId);
}
