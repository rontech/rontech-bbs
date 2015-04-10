package controllers;

import akka.actor.dsl.Inbox.Get;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;
import play.api.mvc.*;

public class Application extends Controller {

	/** ユーザ作成フォーム */
	static Form<User> userForm = Form.form(User.class);

	/** コントローラのnewUser()にredirect */
    public static Result index() {
        return redirect(routes.Application.newUser());
    }

    /** ユーザの登録画面を表示 */
    public static Result newUser() {
        return ok(views.html.index.render(userForm));
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
            return badRequest(views.html.index.render(filledForm));
        }
        /**Userのcreateのメソッドを呼ぶ出す、コントローラのallUsers()にredirect*/
        else {
            User.create(filledForm.get());
            return redirect(routes.Application.allUsers());
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
    	return redirect(routes.Application.allUsers());
    }

}
