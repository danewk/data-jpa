package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
class MemberJpaRepositoryTest {

  @Autowired
  MemberJpaRepository jpaRepository;

  @Test
  public void testMember(){
    Member member = new Member("memberA");
    Member savedMember = jpaRepository.save(member);

    Member findMember = jpaRepository.find(savedMember.getId());

    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

    assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장
  }
}