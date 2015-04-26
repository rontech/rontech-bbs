package controllers;

import models.*;
import play.data.Form;
import play.mvc.Controller;

import play.mvc.Result;
import play.mvc.Security;

import views.html.*;
import play.api.mvc.*;

import play.db.ebean.Model;

public class Forms extends Controller {
    /** ログイン用 */
    public static class Login {
        public String name;
        public String password;

        public String validate(){
            if (User.authenticate(name,password) == null){
                return "パスワード、またはユーザ名が有効ではありません。";
	     }
	     return null;
        }
    }

    /** 更新用 */
    public static class Renew {
	public Long id;
        public String name;
        public String password;
        public String email;
        public Boolean admin;
    }

    /** 新規ユーザ用 */
    public static class newUser {
	public Long id;
        public String name;
	public String password;
        public String email;
	public Boolean admin;

        public String validate(){
            if (User.checkName(name) != null){
                return "すでに存在しています。";
            }else if(User.checkEmail(email) != null){
                return "すでに存在しています。";
            }
            return null;
        }
    }
}
