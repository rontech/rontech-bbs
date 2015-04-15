package controllers;

import models.Article;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import play.mvc.Security;

public class Articles extends Controller {

    static Form<Article> articleForm = Form.form(Article.class);

    /** 全ての記事を渡す */
    @Security.Authenticated(Secured.class)
    public static Result index() {
        return ok (
        	views.html.index.render(Article.find.all())
        );
    }

    @Security.Authenticated(Secured.class)
    public static Result newArticles() {
    	return ok (
    		views.html.newArticle.render(articleForm)
        );
    }

    @Security.Authenticated(Secured.class)
    public static Result addArticles() {
    	Form<Article>filledForm = articleForm.bindFromRequest();
    	if(filledForm.hasErrors()){
    	    return badRequest(
    	        views.html.newArticle.render(filledForm)
            );
    	}else {
    	    Article.create(filledForm.get());
    	    return redirect(routes.Articles.index());
    	}
    }
}
