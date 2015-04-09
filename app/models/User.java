package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;
import play.db.ebean.Model.Finder;

@Entity
public class User extends Model {

	@Id
	public Long id;
	@Required
	public String name;

	public String password;

	public String email;

	public Boolean admin;

	public static Finder<Long,User>find = new Finder(Long.class, User.class);

    /** 全てのユーザを取り出し、Listに格納 */
	public static List<User> all() {
	    return find.all();
	}

	/** 新規ユーザ登録 */
	public static void create(User user) {
		 user.save();
    }
}
