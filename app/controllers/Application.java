package controllers;

import akka.actor.dsl.Inbox.Get;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

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
        Form<User> filledForm = userForm.bindFromRequest();

        if (filledForm.hasErrors()) {
            return badRequest(views.html.index.render(filledForm));
        } else {
            User.create(filledForm.get());
            return redirect(routes.Application.allUsers());
        }
    }
    /** 全てのユーザ情報を表示 */
    public static Result allUsers() {

        return ok(views.html.showUser.render(User.all()));

    }

}
