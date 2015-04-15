package models;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.data.validation.Constraints.Required;
import play.db.ebean.Model;


@Entity
public class Article extends Model {
	@Id
	public Long id;

	@Required
	public String title;
	@Required
	public String article;

	public Long user_id;

	public static Finder <Long,Article> find = new Finder(
	    Long.class,Article.class
	);

	public static List<Article> all() {
		return find.all();
	}
	public static void create(Article article) {
		article.save();
	}

}
