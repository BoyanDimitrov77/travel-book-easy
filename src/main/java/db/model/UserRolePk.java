package db.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Builder
@Getter
@Setter
public class UserRolePk implements Serializable {

    private static final long serialVersionUID = 1L;

    public UserRolePk() {}

    public UserRolePk(User user, UserRoleEnum role) {
        this.user = user;
        this.role = role;
    }

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false, insertable = false)
    User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    UserRoleEnum role;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((role == null) ? 0 : role.hashCode());
        result = prime * result + ((user == null) ? 0 : user.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserRolePk other = (UserRolePk) obj;
        if (role != other.role)
            return false;
        if (user == null) {
            if (other.user != null)
                return false;
        } else if (!user.equals(other.user))
            return false;
        return true;
    }
}
