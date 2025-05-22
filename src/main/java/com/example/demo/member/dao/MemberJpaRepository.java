package com.example.demo.member.dao;

import com.example.demo.member.dao.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
    @Query("select m from MemberEntity m where m.idToken = :idToken")
    Optional<MemberEntity> findByIdToken(@Param("idToken") String idToken);

    @Query("select m from MemberEntity m where m.nickname = :nickname")
    Optional<MemberEntity> findByNickname(@Param("nickname") String nickname);
}
