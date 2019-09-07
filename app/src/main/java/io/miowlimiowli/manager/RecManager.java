package io.miowlimiowli.manager;

import android.util.Pair;
import android.view.Display;

import java.security.Key;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.miowlimiowli.activity.CollectionActivity;
import io.miowlimiowli.manager.data.Keyword;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static java.lang.Math.sqrt;

public class RecManager {

    private double readWordWeight = 1;
    private double favorateWordWeight = 40;
    private double classWeight = 200;
    private class WordVec {
        Map<String,Double> vec;
        public WordVec(){
            vec = new HashMap<>();
        }
        public WordVec(DisplayableNews news){
            this();
            addVec(news,1,200);
        }
        public void addVec(DisplayableNews news,double wordWeight,double classWeight){
            List<Keyword> ls = news.keywords;
            for(Keyword word:ls){
                if(!vec.containsKey(word.keyword))
                    vec.put(word.keyword,0.0);
                vec.put(word.keyword,vec.get(word.keyword)+word.score*wordWeight);
            }
            if(!vec.containsKey(news.catagory)){
                vec.put(news.catagory,0.0);
            }
            vec.put(news.catagory,vec.get(news.catagory)+classWeight*wordWeight);
        }

        double normal2(){
            double rst = 0.0;
            for(double v:vec.values())
                rst += v*v;
            rst = sqrt(rst);
            return rst;
        }
    }

    /**
     * 文本相似度
     * @param a 文本向量
     * @param b 文本向量
     * @return 余弦相似度
     */
    private double similarity(WordVec a, WordVec b){
        double rst = 0.0;
        for(String key:b.vec.keySet()){
            if(a.vec.containsKey(key))
                rst += a.vec.get(key)*b.vec.get(key);
        }
        rst = rst/(a.normal2()*b.normal2());
        return rst;
    }

    /**
     * 获取推荐新闻
     * @param recentNews 近期新闻列表
     * @param readNews 阅读新闻列表
     * @param favoriteNews 收藏新闻列表
     * @return 推荐新闻列表
     */
    public List<DisplayableNews> fetch_recommand_list(List<DisplayableNews> recentNews,List<DisplayableNews> readNews,List<DisplayableNews> favoriteNews){
        List<DisplayableNews> rst = new ArrayList<>();
        WordVec userVec = new WordVec();
        for(DisplayableNews news : readNews){
            userVec.addVec(news,readWordWeight,classWeight);
        }
        for(DisplayableNews news:favoriteNews){
            userVec.addVec(news,favorateWordWeight,classWeight);
        }
        List<Pair<Double,DisplayableNews>> sortedList = new ArrayList<>();
        for(DisplayableNews news:recentNews){
            WordVec wordVec = new WordVec(news);
            double dist = similarity(userVec,wordVec);
            sortedList.add(new Pair<Double, DisplayableNews>(dist,news));
        }
        Collections.sort(sortedList, new Comparator<Pair<Double, DisplayableNews>>() {
            @Override
            public int compare(Pair<Double, DisplayableNews> o1, Pair<Double, DisplayableNews> o2) {
                return o1.first.compareTo(o2.first);
            }
        });
        for(Pair<Double,DisplayableNews> pair : sortedList){
            rst.add(pair.second);
        }
        return rst;
    }

}
