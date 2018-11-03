package db.reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import db.model.User;
import db.model.VerificationToken;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{

	 public VerificationToken findByUser(User user);

	  public VerificationToken findByToken(String token);
}
