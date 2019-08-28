package io.miowlimiowli.manager.data;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 后端使用，前端不应使用
 */
public class RawNews {
    public List<String> image_urls;
    public Date publishtime;
    public List<Keyword> keywords;
    public String language;
    public String video_url;
    public String title;
    public List<When> whens;
    public String content;
    public List<Person> persons;
    public String id;
    public Date crawltime;
    public List<Org> orgs;
    public String publisher;
    public List<Location> locations;
    public List<Where> wheres;
    public String catagory;
    public List<Who> whos;

    /**
     * @param size
     * @param pagenum
     * @param begin 开始时间，null表示所有新闻
     * @param end 结束时间，null表示最新新闻
     * @param keyword
     * @param category
     * @return
     */
    public static List<RawNews> fetch_news_from_server(int size, int pagenum, final Date begin, final Date end, final String keyword, final String category) {
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            String sbegin = "1970-01-01", send = format.format(new Date(System.currentTimeMillis()));
            if (begin != null) {
                sbegin = format.format(begin);
            }
            if (end != null)
                send = format.format(end);
            String url = "https://api2.newsminer.net/svc/news/queryNewsList?" + String.format("size=%d&startDate=%s&endDate=%s&words=%s&categories=%s&page=%d",
                    size,
                    sbegin,
                    send,
                    keyword,
                    category,
                    pagenum);

            URL cs = new URL(url);
            System.out.println("fetching news from url:");
            System.out.println(cs.toString());
            URLConnection urlConn = cs.openConnection();
            urlConn.setConnectTimeout(10 * 1000);
            BufferedReader in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
            String inputLine, body = "";
            while ((inputLine = in.readLine()) != null)
                body = body + inputLine;
            System.out.println("length of data:" + body.length());
            String source;
            JSONObject all = new JSONObject(body);
            JSONArray jnewses = all.getJSONArray("data");
            List<RawNews> newses = new ArrayList<>();
            for (int i = 0; i < jnewses.length(); ++i) {
                JSONObject jnews = jnewses.getJSONObject(i);
                RawNews news = new RawNews();
                String img = jnews.getString("image");
                //img = img.substring(1, img.length() - 1);
                String[] imgs = img.split(",");
                if (imgs[0].length() == 0)
                    imgs = new String[0];
                List<String> ss = new ArrayList<>();
                for (String s : imgs)
                    ss.add(s);
                news.image_urls = ss;
                String s = jnews.getString("publishTime");
                news.publishtime = simpleDateFormat.parse(s);
                JSONArray keywords = jnews.getJSONArray("keywords");
                news.keywords = new ArrayList<>();
                for (int j = 0; j < keywords.length(); ++j) {
                    Keyword word = new Keyword();
                    word.keyword = keywords.getJSONObject(j).getString("word");
                    word.score = keywords.getJSONObject(j).getDouble("score");
                    news.keywords.add(word);
                }
                news.language = jnews.getString("language");
                news.video_url = jnews.getString("video");
                news.title = jnews.getString("title");
                JSONArray whens = jnews.getJSONArray("when");
                news.whens = new ArrayList<>();
                for (int j = 0; j < whens.length(); ++j) {
                    When when = new When();
                    when.when = simpleDateFormat.parse(whens.getJSONObject(j).getString("word"));
                    when.score = whens.getJSONObject(j).getDouble("score");
                    news.whens.add(when);
                }
                news.content = jnews.getString("content");
                JSONArray persons = jnews.getJSONArray("persons");
                news.persons = new ArrayList<>();
                for (int j = 0; j < persons.length(); ++j) {
                    Person person = new Person();
                    person.count = persons.getJSONObject(j).getInt("count");
                    person.url = persons.getJSONObject(j).getString("linkedURL");
                    person.name = persons.getJSONObject(j).getString("mention");
                    news.persons.add(person);
                }
                news.id = jnews.getString("newsID");
                news.crawltime = simpleDateFormat.parse(jnews.getString("crawlTime"));
                JSONArray orgs = jnews.getJSONArray("organizations");
                news.orgs = new ArrayList<>();
                for (int j = 0; j < orgs.length(); ++j) {
                    Org org = new Org();
                    org.count = orgs.getJSONObject(j).getInt("count");
                    org.url = orgs.getJSONObject(j).getString("linkedURL");
                    org.name = orgs.getJSONObject(j).getString("mention");
                    news.orgs.add(org);
                }
                news.publisher = jnews.getString("publisher");
                JSONArray locations = jnews.getJSONArray("locations");
                news.locations = new ArrayList<>();
                for (int j = 0; j < locations.length(); ++j) {
                    Location location = new Location();
                    //System.out.println(locations.getJSONObject(j).toString());
                    location.count = locations.getJSONObject(j).getInt("count");
                    location.url = locations.getJSONObject(j).getString("linkedURL");
                    location.name = locations.getJSONObject(j).getString("mention");
                    if (locations.getJSONObject(j).has("lat"))
                        location.lat = locations.getJSONObject(j).getDouble("lat");
                    if (locations.getJSONObject(j).has("lng"))
                        location.lng = locations.getJSONObject(j).getDouble("lng");
                    news.locations.add(location);
                }
                JSONArray wheres = jnews.getJSONArray("where");
                news.wheres = new ArrayList<>();
                for (int j = 0; j < wheres.length(); ++j) {
                    Where where = new Where();
                    //System.out.println(wheres.getJSONObject(j).toString());
                    where.score = wheres.getJSONObject(j).getDouble("score");
                    where.name = wheres.getJSONObject(j).getString("word");
                    news.wheres.add(where);
                }
                news.catagory = jnews.getString("category");
                JSONArray whos = jnews.getJSONArray("who");
                news.whos = new ArrayList<>();
                for (int j = 0; j < whos.length(); ++j) {
                    Who who = new Who();
                    who.score = whos.getJSONObject(j).getDouble("score");
                    who.name = whos.getJSONObject(j).getString("word");
                    news.whos.add(who);
                }
                newses.add(news);
            }
            System.out.println(newses.size() + " news found!");
            return newses;
        } catch (Exception e) {
            System.out.println("error raised when fetching news from server!");
            System.out.println(e);
            e.printStackTrace();
            return null;
        }
    }


}
