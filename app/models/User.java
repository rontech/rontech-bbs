package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.api.mvc.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

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
	/** 削除するためidを探さなければ */
	public static void delete(Long id){
		find.ref(id).delete();
	}
	/** 選択機能を実装する*/
	public static User select(Long id){
		return find.ref(id);
	}
	/** 新しい情報を変更するために、newUserの変数を定義する*/
	public static void update(User newUser){
		newUser.update();
	}
}
