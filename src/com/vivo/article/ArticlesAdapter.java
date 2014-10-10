package com.vivo.article;

import java.util.LinkedList;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.vivo.providers.articles.Articles;

public class ArticlesAdapter {

	private static final String LOG_TAG="com.vivo.article.ArticlesAdapter";
	
	private ContentResolver resolver=null;
	public ArticlesAdapter(Context context){
		resolver=context.getContentResolver();
	}
	
	public long insertArticle(Article article){
		ContentValues values=new ContentValues();
		values.put(Articles.TITLE, article.getTitle());
		values.put(Articles.ABSTRACT, article.getAbs());
		values.put(Articles.URL, article.getUrl());
		
		Uri uri=resolver.insert(Articles.CONTENT_URI, values);
		String itemId=uri.getPathSegments().get(1);
		return Integer.valueOf(itemId).longValue();
	}
	
	public boolean updateArticle(Article article){
		Uri uri=ContentUris.withAppendedId(Articles.CONTENT_URI, article.getId());
		ContentValues values=new ContentValues();
		values.put(Articles.TITLE, article.getTitle());
		values.put(Articles.ABSTRACT, article.getAbs());
		values.put(Articles.URL, article.getUrl());
		
		int count=resolver.update(uri, values, null, null);
		return count>0;
	}
	
	public LinkedList<Article> getAllArticles(){
		LinkedList<Article> articles=new LinkedList<Article>();
		String[] projection=new String[]{
				Articles.ID,
				Articles.TITLE,
				Articles.ABSTRACT,
				Articles.URL
		};
		
		Cursor cursor=resolver.query(Articles.CONTENT_URI, projection, null, null, Articles.DEFAULT_SORT_ORDER);
		if(cursor.moveToFirst()){
			do{
				int id=cursor.getInt(0);
				String title=cursor.getString(1);
				String abs=cursor.getString(2);
				String url=cursor.getString(3);
				
				Article article=new Article(id, title, abs, url);
				articles.add(article);
			}while(cursor.moveToNext());
		}
		return articles;
	}
	
	
}
