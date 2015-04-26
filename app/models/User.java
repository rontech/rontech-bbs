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
    public static void create(Forms.newUser newUser) {
        User user = new User();
	user.id = newUser.id;
	user.name = newUser.name;
	user.password = newUser.password;
        user.email = newUser.email;
        user.admin = newUser.admin;
        user.save();
    }

    /** マイページ更新用 */
    public static void updateMypage(Forms.Renew update){
        User user = new User();
        user.id = update.id;
        user.name = update.name;
        user.password = update.password;
        user.email = update.email;
        user.admin = update.admin;
        user.update();
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
    public static void update(Forms.Renew updateUser){
        User user = new User();
        user.id = updateUser.id;
        user.name = updateUser.name;
        user.password = updateUser.password;
        user.email = updateUser.email;
        user.admin = updateUser.admin;
        user.update();
    }

    public static User authenticate(String name,String password){
        return find.where().eq("name", name).eq("password", password).findUnique();
    }

    /** ユーザ名の完全一致で、レコードを返す */
    public static User checkName(String name){
        return find.where().eq("name", name).findUnique();
    }

    /** Emailの完全一致で、レコードを返す */
    public static User checkEmail(String email){
        return find.where().eq("email", email).findUnique();
    }

    /** ユーザ名からadmin判断 */
    public static Boolean checkAdmin(String name){
        User user = find.where().eq("name",name).findUnique();
        return user.admin;
    }

    /** ユーザ名からIDを取り出す */
    public static Long checkId(String name){
        User user = find.where().eq("name",name).findUnique();
        return user.id;
    }
}
