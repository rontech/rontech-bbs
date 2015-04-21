package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import play.api.mvc.*;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

import controllers.*;

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
    public static void update(Forms.Renew newUser){
	User user = new User();
	user.id = newUser.id;
	user.name = newUser.name;
	user.password = newUser.password;
	user.email = newUser.email;
	user.admin = newUser.admin;
        user.update();
    }

    public static User authenticate(String name,String password){
        return find.where().eq("name", name).eq("password", password).findUnique();
    }

    public static User authenticate1(String name){
        return find.where().eq("name", name).findUnique();
    }

    public static User authenticate2(String email){
        return find.where().eq("email", email).findUnique();
    }

    public static Boolean checkAdmin(String name){
        User user = find.where().eq("name",name).findUnique();
        return user.admin;
    }

    public String validate(){
        if (User.authenticate1(name) != null){
            return "すでに存在しています。";
	} else if (User.authenticate2(email) != null){
            return "すでに存在しています。";
        }
	return null;
    }
}
