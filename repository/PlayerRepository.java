package com.bot.someweire.repository;

import com.bot.someweire.model.Character;
import org.neo4j.springframework.data.repository.ReactiveNeo4jRepository;
import org.neo4j.springframework.data.repository.query.Query;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface PlayerRepository  extends ReactiveNeo4jRepository<Character, Long> {


  @Query("MATCH (n:`Character`) WHERE id(n) = $id WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}],__relationship__}], "
      + "Character_HAS_Fragment: [(n)-[__relationship__:`HAS`]->(n_fragments:`Fragment`) | n_fragments{__internalNeo4jId__: id(n_fragments), .type, .quantite, __nodeLabels__: labels(n_fragments), __relationship__}], "
      + "Character_IN_LIEU_Lieu: [(n)-[:`IN_LIEU`]->(n_position:`Lieu`) | n_position{.thumb, .description, __internalNeo4jId__: id(n_position), .name, __nodeLabels__: labels(n_position), "
      + "Lieu_IN_REGION_Region: [(n_position)-[__relationship__:`IN_REGION`]->(n_position_regions:`Region`) | n_position_regions{.description, __internalNeo4jId__: id(n_position_regions), .name, __nodeLabels__: labels(n_position_regions), "
      + "Region_IN_WORLD_World: [(n_position_regions)-[__relationship__:`IN_WORLD`]->(n_position_regions_worlds:`World`) | n_position_regions_worlds{.description, __internalNeo4jId__: id(n_position_regions_worlds), .name, __nodeLabels__: labels(n_position_regions_worlds), __relationship__}], __relationship__}], "
      + "Lieu_LEADS_TO_Lieu: [(n_position)-[:`LEADS_TO`]->(n_position_roads:`Lieu`) | n_position_roads{.thumb, .description, __internalNeo4jId__: id(n_position_roads), .name, __nodeLabels__: labels(n_position_roads)}]}]}")
  Mono<Character> findById(Long id);

  Flux<Character> findByNameLikeIgnoreCase(String name);

  Flux<Character> findByClassTypeIgnoreCase(String classType);

  @Query("MATCH (n:`Character`) WHERE n.userId=$userId RETURN n.name")
  Mono<String> findPlayerName(String userId);

  @Query("MATCH (n:`Character`) WHERE n.userId=$userId WITH n, id(n) AS __internalNeo4jId__ "
      + "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
          + "Character_HAS_Indice: [(n)-[__relationship__:`HAS`]->(n_indices:`Indice`) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}], "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:`TO_ENIGME`]-(n_enigmes_indices:`Indice`) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}], __relationship__}], "
      + "Character_HAS_Fragment: [(n)-[__relationship__:`HAS`]->(n_fragments:`Fragment`) | n_fragments{__internalNeo4jId__: id(n_fragments), .type, .quantite, __nodeLabels__: labels(n_fragments), __relationship__}], "
      + "Character_IN_LIEU_Lieu: [(n)-[:`IN_LIEU`]->(n_position:`Lieu`) | n_position{.thumb, .description, __internalNeo4jId__: id(n_position), .name, __nodeLabels__: labels(n_position), "
      + "Lieu_IN_LIEU_Portail: [(n_position)<-[:`IN_LIEU`]-(n_portals:`Portail`) | n_portals{__internalNeo4jId__: id(n_portals), __nodeLabels__: labels(n_portals), "
      + "Portail_IN_LIEU_Lieu: [(n_portals)-[`IN_LIEU`]->(n_portals_lieux:`Lieu`) | n_portals_lieux{__internalNeo4jId__: id(n_portals_lieux), __nodeLabels__: labels(n_portals_lieux), .description, .thumb, .name}]}], "
          + "Lieu_IN_LIEU_Cache: [(n_position)<-[:`IN_LIEU`]-(n_caches:`Cache`) | n_caches{__internalNeo4jId__: id(n_caches), __nodeLabels__: labels(n_caches), "
      + "Cache_IN_CACHE_Enigme: [(n_caches)<-[:`IN_CACHE`]-(n_caches_enigme:`Enigme`) | n_caches_enigme{__internalNeo4jId__: id(n_caches_enigme), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_caches_enigme), "
          + "Enigme_TO_ENIGME_Indice: [(n_caches_enigme)<-[:`TO_ENIGME`]-(n_caches_enigme_indices:`Indice`) | n_caches_enigme_indices{__internalNeo4jId__: id(n_caches_enigme_indices), .numero, .titre, __nodeLabels__: labels(n_caches_enigme_indices)}]}], "
      + "Cache_IN_CACHE_Fragment: [(n_caches)<-[:`IN_CACHE`]-(n_caches_fragments:`Fragment`) | n_caches_fragments{__internalNeo4jId__: id(n_caches_fragments), .quantite, .type, __nodeLabels__: labels(n_caches_fragments)}]}],"
      + "Lieu_IN_REGION_Region: [(n_position)-[__relationship__:`IN_REGION`]->(n_position_regions:`Region`) | n_position_regions{.description, __internalNeo4jId__: id(n_position_regions), .name, __nodeLabels__: labels(n_position_regions), "
      + "Region_IN_WORLD_World: [(n_position_regions)-[__relationship__:`IN_WORLD`]->(n_position_regions_worlds:`World`) | n_position_regions_worlds{.description, __internalNeo4jId__: id(n_position_regions_worlds), .name, __nodeLabels__: labels(n_position_regions_worlds), __relationship__}], __relationship__}], "
      + "Lieu_LEADS_TO_Lieu: [(n_position)-[:`LEADS_TO`]->(n_position_roads:`Lieu`) | n_position_roads{.thumb, .description, __internalNeo4jId__: id(n_position_roads), .name, __nodeLabels__: labels(n_position_roads), "
      + "Lieu_IN_REGION_Region: [(n_position_roads)-[__relationship__:`IN_REGION`]->(n_position_roads_regions:`Region`) | n_position_roads_regions{.description, __internalNeo4jId__: id(n_position_roads_regions), .name, __nodeLabels__: labels(n_position_roads_regions), "
          + "Region_IN_WORLD_World: [(n_position_roads_regions)-[__relationship__:`IN_WORLD`]->(n_position_roads_regions_worlds:`World`) | n_position_roads_regions_worlds{.description, __internalNeo4jId__: id(n_position_roads_regions_worlds), .name, __nodeLabels__: labels(n_position_roads_regions_worlds), __relationship__}], __relationship__}]}]}]}")
  Mono<Character> findByUserId(String userId);

  @Query("MATCH (n:Character)-[:IN_LIEU]->(:Lieu)-[:IN_REGION]->(:Region)-[:IN_WORLD]->(w:World) WHERE id(w)=$worldId " +
          "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), " +
          "Character_HAS_Fragment: [(n)-[__relationship__:`HAS`]->(n_fragments:`Fragment`) | n_fragments{__internalNeo4jId__: id(n_fragments), .type, .quantite, __nodeLabels__: labels(n_fragments), __relationship__}], " +
          "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), __relationship__}]}")
  Flux<Character> findAllCharactersInWorld(Long worldId);

  @Query("MATCH (n:Character)-[r:IN_LIEU]->(l:Lieu), (y:Lieu) WHERE id(n)=$userid AND id(l)=$previouslocationid AND id(y)=$newlocationid DELETE r CREATE (n)-[:IN_LIEU]->(y) "
      + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_IN_LIEU_Lieu: [(n)-[:`IN_LIEU`]->(n_position:`Lieu`) | n_position{.thumb, .description, __internalNeo4jId__: id(n_position), .name, __nodeLabels__: labels(n_position)}]}")
  Mono<Character> updatePlayerLocation(Long userid, Long previouslocationid, Long newlocationid);

  @Query("MATCH (n:Character)-[r:HAS]->(e:Enigme) WHERE n.userId=$userId AND id(e)=$enigmeId SET r.solved=true "
      + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}], __relationship__}]}")
  Mono<Character> updateSolvedPlayerEnigme(String userId, Long enigmeId);

  @Query("MATCH (n:Character)-[r:HAS]->(e:Enigme) WHERE n.userId=$userId AND id(e)=$enigmeId SET n.powers=$powers, r.powered=true, r.tries=$tries "
      + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender,.niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}], __relationship__}]}")
  Mono<Character> updateSolvedPlayerEnigmeByPower(String userId, Long enigmeId, int tries, int powers);

  @Query("MATCH (n:Character), (e:Enigme) WHERE n.userId=$userId AND id(e)=$enigmeId CREATE (n)-[:HAS{solved:false, tries:0, forfeit:false, powered:false}]->(e) "
      + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}], __relationship__}]}")
  Mono<Character> updateOwnedPlayerEnigme(String userId, Long enigmeId);

  @Query("MATCH (n:Character), (e:Enigme) WHERE n.userId=$userId AND id(e)=$enigmeId SET n.powers=$powers CREATE (n)-[:HAS{solved:false, tries:0, forfeit:false, powered:false}]->(e) "
          + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
          + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
          + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}], __relationship__}]}")
  Mono<Character> updateOwnPlayerEnigmeByPower(String userId, Long enigmeId, int powers);

  @Query("MATCH(n:Character), (f:Fragment) WHERE n.userId=$userid and id(f)=$fragmentid SET n.powers=$powers CREATE (n)-[:HAS{left:$quantite}]->(f) "
      + "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Fragment: [(n)-[__relationship__:HAS]->(n_fragments:`Fragment`) | n_fragments{__internalNeo4jId__: id(n_fragments), .quantite, .type, __nodeLabels__: labels(n_fragments), "
      + "Fragment_IN_CACHE_Cache: [(n_fragments)-[:`IN_CACHE`]->(n_fragments_cache:`Cache`) | n_fragments_cache{__internalNeo4jId__: id(n_fragments_cache), __nodeLabels__: labels(n_fragments_cache)}], __relationship__}]}")
  Mono<Character> updatePlayerFragments(String userid, Long fragmentid, int quantite, int powers);


  @Query("match(n:Character)-[r:HAS]->(f:Fragment) WHERE n.userId=$userId AND id(f)=$fragmentId CREATE (n)-[:HAS{left:r.left-$number}]->(f) DELETE r "
      + "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Fragment: [(n)-[__relationship__:HAS]->(n_fragments:`Fragment`) | n_fragments{__internalNeo4jId__: id(n_fragments), .quantite, .type, __nodeLabels__: labels(n_fragments), "
      + "Fragment_IN_CACHE_Cache: [(n_fragments)-[:`IN_CACHE`]->(n_fragments_cache:`Cache`) | n_fragments_cache{__internalNeo4jId__: id(n_fragments_cache), __nodeLabels__: labels(n_fragments_cache)}], __relationship__}]}")
  Mono<Character> spendPlayerFragment(String userId, Long fragmentId, int number);

  @Query("MATCH (n:Character)-[r:HAS]->(f:Fragment) WHERE n.userId=$userId AND id(f)=$fragmentId DELETE r CREATE (n)-[:HAS{left:$valueLeft}]->(f) "
      + "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Fragment: [(n)-[__relationship__:HAS]->(n_fragments:`Fragment`) | n_fragments{__internalNeo4jId__: id(n_fragments), .quantite, .type, __nodeLabels__: labels(n_fragments), "
      + "Fragment_IN_CACHE_Cache: [(n_fragments)-[:`IN_CACHE`]->(n_fragments_cache:`Cache`) | n_fragments_cache{__internalNeo4jId__: id(n_fragments_cache), __nodeLabels__: labels(n_fragments_cache)}], __relationship__}]}")
  Mono<Character> setValueLeftOnFragmentForPlayer(String userId, Long fragmentId, int valueLeft);

  @Query("MATCH (n:Character) WHERE n.userId=$userId RETURN EXISTS(n.userId)")
  Mono<Boolean> playerExists(String userId);

  @Query("MATCH (w:World)<-[:IN_WORLD]-(r:Region)<-[:IN_REGION]-(l:Lieu) WHERE w.name=~'(?i).*'+$worldName+'.*' WITH collect(l)[0] as place "
      + "CREATE (n:Character{name:$name, classType:$classType, gender:$gender, userId:$userId, powers:2, niveau:1, forfeitPower:false})-[:IN_LIEU]->(place) "
      + "RETURN EXISTS(n.userId)")
  Mono<Boolean> createPlayer(String name, String classType, String gender, String userId, String worldName);

  @Query("MATCH (w:World)<-[:IN_WORLD]-(r:Region)<-[:IN_REGION]-(l:Lieu) WHERE w.name=~'(?i).*'+$worldName+'.*' WITH collect(l)[0] as place "
      + "MATCH (c:Character{userId:$userId}) DETACH DELETE c "
      + "CREATE (n:Character{name:$name, classType:$classType, gender:$gender, userId:$userId, powers:2, niveau:1, forfeitPower:false})-[:IN_LIEU]->(place) RETURN EXISTS(n.userId)")
  Mono<Boolean> resetPlayer(String name, String classType, String gender, String userId, String worldName);

  @Query("MATCH(n:Character) WHERE n.userId=$userId SET n.power=$powers "
      + "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n)}")
  Mono<Character> updatePlayerPowers(String userId, int powers);

  @Query("MATCH (n:Character), (i:Indice) WHERE n.userId=$userId AND id(i)=$indiceId CREATE (n)-[:HAS{powered:false}]->(i) "
      + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Indice: [(n)-[__relationship__:`HAS`]->(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}], "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}],__relationship__}]}")
  Mono<Character> updateOwnedPlayerIndice(String userId, Long indiceId);

  @Query("MATCH (n:Character), (i:Indice) WHERE n.userId=$userId AND id(i)=$indiceId SET n.powers= $powers CREATE (n)-[:HAS{powered:true}]->(i) "
      + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Indice: [(n)-[__relationship__:`HAS`]->(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}], "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}],__relationship__}]}")
  Mono<Character> updateOwnedPlayerPowerIndice(String userId, Long indiceId, int powers);

  @Query("MATCH (n:Character)-[r:HAS]->(i:Indice) WHERE n.userId=$userId AND id(i)=$indiceId SET n.powers=$powers, r.powered=true "
          + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
          + "Character_HAS_Indice: [(n)-[__relationship__:`HAS`]->(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}], "
          + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
          + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}],__relationship__}]}")
  Mono<Character> updatePlayerPowerOwnedIndice(String userId, Long indiceId, int powers);

  @Query("MATCH (n:Character) WHERE n.userId=$userId SET n.forfeitPower=$forfeitPower "
      + "RETURN n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n)}")
  Mono<Character> updateForfeitPower(String userId, boolean forfeitPower);

  @Query("MATCH (e:Enigme)<-[r:HAS]-(c:Character) WHERE id(e)=$enigmeId and c.userId=$userId SET r.tries=r.tries+1 return $solution =~ '(?i).*'+e.solution+'.*'")
  Mono<Boolean> trySolveEnigme(String solution, Long enigmeId, String userId);

  @Query("MATCH (e:Enigme)<-[r:HAS]-(n:Character) where n.userId=$userId and id(e)=$enigmeId SET r.forfeit=true, n.forfeitPower=true "
      + "return n{.classType, __internalNeo4jId__: id(n), .name, .powers, .userId, .gender, .niveau, .forfeitPower, __nodeLabels__: labels(n), "
      + "Character_HAS_Indice: [(n)-[__relationship__:`HAS`]->(n_indices:Indice) | n_indices{__internalNeo4jId__: id(n_indices), .numero, .titre, __nodeLabels__: labels(n_indices), __relationship__}], "
      + "Character_HAS_Enigme: [(n)-[__relationship__:`HAS`]->(n_enigmes:`Enigme`) | n_enigmes{__internalNeo4jId__: id(n_enigmes), .monde, .numero, .titre, .prix, __nodeLabels__: labels(n_enigmes), "
      + "Enigme_TO_ENIGME_Indice: [(n_enigmes)<-[:TO_ENIGME]-(n_enigmes_indices:Indice) | n_enigmes_indices{__internalNeo4jId__: id(n_enigmes_indices), .numero, .titre, __nodeLabels__: labels(n_enigmes_indices)}],__relationship__}]}")
  Mono<Character> forfeitByCharacter(String userId, Long enigmeId);
}