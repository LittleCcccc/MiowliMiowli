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
import io.miowlimiowli.manager.data.RawNews;
import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

import static java.lang.Math.sqrt;

public class Recommender {

    private static double readWordWeight = 1;
    private static double favorateWordWeight = 40;
    private static double classWeight = 200;
    private static class WordVec {
        Map<String,Double> vec;
        public WordVec(){
            vec = new HashMap<>();
        }
        public WordVec(RawNews news){
            this();
            addVec(news,1,200);
        }
        public void addVec(RawNews news,double wordWeight,double classWeight){
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
    private static double similarity(WordVec a, WordVec b){
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
    public static List<RawNews> fetch_recommand_list(List<RawNews> recentNews,List<RawNews> readNews,List<RawNews> favoriteNews){
        List<RawNews> rst = new ArrayList<>();
        WordVec userVec = new WordVec();
        for(RawNews news : readNews){
            userVec.addVec(news,readWordWeight,classWeight);
        }
        for(RawNews news:favoriteNews){
            userVec.addVec(news,favorateWordWeight,classWeight);
        }
        List<Pair<Double,RawNews>> sortedList = new ArrayList<>();
        for(RawNews news:recentNews){
            WordVec wordVec = new WordVec(news);
            double dist = similarity(userVec,wordVec);
            sortedList.add(new Pair<Double, RawNews>(dist,news));
        }
        Collections.sort(sortedList, new Comparator<Pair<Double, RawNews>>() {
            @Override
            public int compare(Pair<Double, RawNews> o1, Pair<Double, RawNews> o2) {
                return o1.first.compareTo(o2.first);
            }
        });
        for(Pair<Double,RawNews> pair : sortedList){
            rst.add(pair.second);
        }
        return rst;
    }

}
