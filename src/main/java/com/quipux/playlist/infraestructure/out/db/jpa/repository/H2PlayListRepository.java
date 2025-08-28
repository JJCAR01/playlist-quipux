package com.quipux.playlist.infraestructure.out.db.jpa.repository;

import com.quipux.playlist.infraestructure.out.db.entities.PlayListEntity;
import com.quipux.playlist.infraestructure.out.db.jpa.JpaPlayListRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface H2PlayListRepository extends JpaRepository<PlayListEntity, Long> {
    boolean existsByName(String name);
    Optional<PlayListEntity> findByName(String name);
}
