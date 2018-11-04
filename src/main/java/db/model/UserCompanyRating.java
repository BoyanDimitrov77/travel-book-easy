package db.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Data
@Table(name = "user_company_rating")
public class UserCompanyRating {

	
	@EmbeddedId
	private UserCompanyRatingPk id;
	
	@Column(name = "rating")
	private BigDecimal rating;
}
