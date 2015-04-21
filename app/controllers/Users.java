package controllers;

import java.util.List;
import akka.actor.dsl.Inbox.Get;
import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

import controllers.*;

import views.html.*;
import views.html.user.*;
import play.api.mvc.*;


public class Users extends Controller {

    /** ユーザ作成フォーム */
    static Form<User> userForm = Form.form(User.class);
    /** ログイン用フォーム */
    static Form<Forms.Login> loginForm = Form.form(Forms.Login.class);
    /** 更新用のフォーム */
    static Form<Forms.Renew> updateForm = Form.form(Forms.Renew.class);

    /** コントローラのnewUser()にredirect */
    @Security.Authenticated(Secured.class)
    public static Result index() {
    	 return ok(index.render(Article.find.all()));
    }

    /** ユーザの登録画面を表示 */
    @Security.Authenticated(Secured.class)
    public static Result newUser() {
        return ok(newUser.render(userForm));
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

    /** ユーザの追加 */
    @Security.Authenticated(Secured.class)
    public static Result addUser() {
        Form<User> filledForm = userForm.bindFromRequest();
        if (filledForm.hasErrors()) {
            return badRequest(newUser.render(filledForm));
        }
        else {
            User.create(filledForm.get());
            return redirect(routes.Users.allUsers());
        }
    }

    /**すべて新規ユーザ一覧を表示する*/
    @Security.Authenticated(Secured.class)
    public static Result allUsers() {
        return ok(showUser.render(User.all()));
    }

    /** ユーザ削除 */
    @Security.Authenticated(Secured.class)
    public static Result deleteUser(Long id){
    	User.delete(id);
    	return redirect(routes.Users.allUsers());
    }

    /** ユーザの詳細を表示する*/
    @Security.Authenticated(Secured.class)
    public static Result selectUser(Long id){
    	User.select(id);
    	return ok(updateUser.render(User.select(id), updateForm));
    }

    /** ユーザ情報の更新を行う */
    @Security.Authenticated(Secured.class)
    public static Result updateUser(Long id){
    	Form<Forms.Renew> filledForm = updateForm.bindFromRequest();
    	if(filledForm.hasErrors()) {
    	    return badRequest(
    	        updateUser.render(User.select(id),filledForm)
    	    );
    	}else{
    	  User.update(filledForm.get());
          return redirect(routes.Users.allUsers());
    	}
    }

    public static Result login(){
    	return ok(login.render(loginForm));
    }

    public static Result logout(){
    	session().clear();
	return redirect(routes.Users.login());
    }
    
    /** ログイン認証 */
    public static Result authenticate(){
    	Form<Forms.Login> filledForm = loginForm.bindFromRequest();
        if(filledForm.hasErrors()){
            return badRequest(login.render(filledForm));
        }
        session().clear();
        session("name",filledForm.get().name);
        return redirect(routes.Users.index());
    }
}
