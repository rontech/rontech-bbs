package models;

import java.util.List;

import javax.persistence.*;
import play.data.validation.Constraints.*;
import play.db.ebean.Model;

@Entity
public class Article extends Model {
    @Id
    public Long id;

    @Required
    @MinLength(5)
    @MaxLength(100)
    public String title;

    @Required
    @Column(name="article", columnDefinition="text")
    @MinLength(5)
    @MaxLength(1000)
    public String article;

    public Long user_id;

    public static Finder <Long,Article> find = new Finder(
        Long.class,Article.class
    );

    public static List<Article> all() {
        return find.all();
    }

    /** 自分の書いた記事のみを検索する */
    public static List<Article> myAll(Long userId) {
        return find.where().eq("user_id", userId).findList();
    }

    public static void create(Article article) {
        article.save();
    }

    public static void change(Article newArticle) {
        newArticle.update();
    }

    public static void delete(Long id) {
        find.ref(id).delete();
    }

    public static  Article select(Long id) {
        return find.ref(id);
    }
}
