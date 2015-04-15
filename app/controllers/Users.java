package controllers;

import java.util.List;
import akka.actor.dsl.Inbox.Get;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;
import views.html.*;
import play.api.mvc.*;

public class Users extends Controller {

	/** ユーザ作成フォーム */
    static Form<User> userForm = Form.form(User.class);

    static Form<Login> loginForm = Form.form(Login.class);

	/** コントローラのnewUser()にredirect */
    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok(index.render(Article.find.all()));
    }

    /** ユーザの登録画面を表示 */
    @Security.Authenticated(Secured.class)
    public static Result newUser() {
        return ok(views.html.newUser.render(userForm));
    }
    
    /** 
     * サンプルユーザ作成用
     * 使い終わったら、このメソッドを消してください。
     */
    public static Result createSampleUser() {
      User check = User.find.where().eq("name", "admin").findUnique();
        if (check == null) {
            User user = new User();
	    user.name = "admin";
	    user.password = "admin";
	    user.email = "sample@example.com";
            user.admin = true;
	    user.save();
	}
        return redirect(routes.Users.login());
    }

    /**
     *  フォームからデータを取り出し、
     *  エラーがあれば、登録画面へリターン
     *  成功したらユーザを登録し、
     *  コントローラのallUsers()にredirect
     */
    @Security.Authenticated(Secured.class)
    public static Result addUser() {
    	/** ユーザから要求フォーム作成 */
        Form<User> filledForm = userForm.bindFromRequest();
        /** もしユーザの入力を間違ったら、エラーを表示する */
        if (filledForm.hasErrors()) {
            return badRequest(views.html.newUser.render(filledForm));
        }
        /**Userのcreateのメソッドを呼ぶ出す、コントローラのallUsers()にredirect*/
        else {
            User.create(filledForm.get());
            return redirect(routes.Users.allUsers());
        }
    }

    /** 全てのユーザ情報を表示 */
    @Security.Authenticated(Secured.class)
    public static Result allUsers() {
    	/**すべて新規ユーザ一覧を表示する*/
        return ok(views.html.showUser.render(User.all()));

    }

    /** 削除機能を追加するため*/
    @Security.Authenticated(Secured.class)
    public static Result deleteUser(Long id){
    	/**モデルの削除メソッドを呼びたす*/
    	User.delete(id);
    	/**すべて新規ユーザ一覧を表示する*/
    	return redirect(routes.Users.allUsers());
    }

    /** ユーザの詳細を表示する*/
    @Security.Authenticated(Secured.class)
    public static Result selectUser(Long id){
    	 /** モデル側の選択メソッドを呼ぶ出す*/
    	User.select(id);
    	 /** 新しいページを転送する*/
    	return ok(views.html.updateUser.render(models.User.select(id),userForm));
    }

    @Security.Authenticated(Secured.class)
    public static Result updateUser(Long id){
    	 /** ユーザから更新の要求フォーム作成*/
    	Form<User> filledForm = userForm.bindFromRequest();

    	/** もしユーザの入力を間違ったら、エラーを表示する */
    	if(filledForm.hasErrors()) {
    	    return badRequest(
    	    /** 選択画面をリターン */
    	        views.html.updateUser.render(models.User.select(id),filledForm)
    	    );
    	}else{
        /** 
	 * Userの更新メソッドを呼ぶ出して、更新した内容を取り出す
    	 * コントローラのallUsers()にredirect 
	 */
    	User.update(filledForm.get());
          return redirect(routes.Users.allUsers());
    	}
    }

    public static Result login(){
    	return ok(views.html.login.render(loginForm));
    }

    public static Result logout(){
    	session().clear();
	return redirect(routes.Users.login());
    }

    public static Result authenticate(){
    	Form<Login> filledForm = loginForm.bindFromRequest();
        if(filledForm.hasErrors()){
            return badRequest(login.render(filledForm));
        }
        session().clear();
        session("name",filledForm.get().name);
        return redirect(routes.Users.index());
    }

    public static class Login{

        public String name;
        public String password;

	public String validate(){
        if (User.authenticate(name,password) == null){
            return "パスワード、またはユーザ名が有効ではありません。";
	}
	return null;
       }
    }
}
