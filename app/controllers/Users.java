package controllers;

import akka.actor.dsl.Inbox.Get;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.api.mvc.*;

public class Users extends Controller {

	/** ユーザ作成フォーム */
	static Form<User> userForm = Form.form(User.class);

	/** コントローラのnewUser()にredirect */
    public static Result index() {
        return redirect(routes.Users.newUser());
    }

    /** ユーザの登録画面を表示 */
    public static Result newUser() {
        return ok(views.html.newUser.render(userForm));
    }
    /**
     *  フォームからデータを取り出し、
     *  エラーがあれば、登録画面へリターン
     *  成功したらユーザを登録し、
     *  コントローラのallUsers()にredirect
     */
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
    public static Result allUsers() {
    	/**すべて新規ユーザ一覧を表示する*/
        return ok(views.html.showUser.render(User.all()));

    }
    /** 削除機能を追加するため*/
    public static Result deleteUser(Long id){
    	/**モデルの削除メソッドを呼びたす*/
    	User.delete(id);
    	/**すべて新規ユーザ一覧を表示する*/
    	return redirect(routes.Users.allUsers());
    }
    /** ユーザの詳細を表示する*/
    public static Result selectUser(Long id){
    	 /** モデル側の選択メソッドを呼ぶ出す*/
    	User.select(id);
    	 /** 新しいページを転送する*/
    	return ok(views.html.updateUser.render(models.User.select(id),userForm));
    }

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
    		/** Userの更新メソッドを呼ぶ出して、更新した内容を取り出す */
    		/** コントローラのallUsers()にredirect */
    		User.update(filledForm.get());
          return redirect(routes.Users.allUsers());
    	}
    }

}


