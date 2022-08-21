package study.datajpa.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;

@SpringBootTest
@Transactional
@Rollback(false)
public class MemberRepositoryTest {

  @Autowired
  MemberRepository memberRepository;

  @Test
  public void testMember() {

    Member member = new Member("memberA");
    Member savedMember = memberRepository.saveAndFlush(member);

    Member findMember = memberRepository.findById(savedMember.getId())
        .get();

    assertThat(findMember.getId()).isEqualTo(member.getId());
    assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
    assertThat(findMember).isEqualTo(member); //JPA 엔티티 동일성 보장

  }

  @Test
  public void basicCRUD() {
    Member member1 = new Member("member1");
    Member member2 = new Member("member2");

    memberRepository.save(member1);
    memberRepository.save(member2);

    //단건 조회 검증
    Member findMember1 = memberRepository.findById(member1.getId())
        .get();
    Member findMember2 = memberRepository.findById(member2.getId())
        .get();
    assertThat(findMember1).isEqualTo(member1);
    assertThat(findMember2).isEqualTo(member2);

    //리스트 조회 검증
    List<Member> all = memberRepository.findAll();
    assertThat(all.size()).isEqualTo(2);

    //카운트 검증
    long count = memberRepository.count();
    assertThat(count).isEqualTo(2);

    //삭제 검증
    memberRepository.delete(member1);
    memberRepository.delete(member2);
    long deletedCount = memberRepository.count();
    assertThat(deletedCount).isEqualTo(0);
  }

  @Test
  public void findByUsernameAndAgeGreaterThan(){
    Member member1 = new Member("member",10);
    Member member2 = new Member("member",20);

    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> result = memberRepository.findByUsernameAndAgeGreaterThan("member", 15);

    assertThat(result.get(0).getUsername()).isEqualTo("member");
    assertThat(result.get(0).getAge()).isEqualTo(20);
    assertThat(result.size()).isEqualTo(1);


  }

  @Test
  public void testQuery(){
    Member member1 = new Member("member",10);
    Member member2 = new Member("member",20);

    memberRepository.save(member1);
    memberRepository.save(member2);

    List<Member> findMember = memberRepository.findUser("member", 10);
    assertThat(findMember.get(0).getUsername()).isEqualTo("member");
    assertThat(findMember.get(0).getAge()).isEqualTo(10);
    assertThat(findMember.size()).isEqualTo(1);


  }

}