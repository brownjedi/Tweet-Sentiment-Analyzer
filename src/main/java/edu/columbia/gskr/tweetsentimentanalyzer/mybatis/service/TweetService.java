package edu.columbia.gskr.tweetsentimentanalyzer.mybatis.service;

import edu.columbia.gskr.tweetsentimentanalyzer.domain.Sentiment;
import edu.columbia.gskr.tweetsentimentanalyzer.domain.Tweet;
import edu.columbia.gskr.tweetsentimentanalyzer.mybatis.mapper.TweetMapper;
import edu.columbia.gskr.tweetsentimentanalyzer.mybatis.util.MyBatisConnectionFactory;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.TransactionIsolationLevel;

import java.util.List;

/**
 * Created by saikarthikreddyginni on 2/27/15.
 */

@SuppressWarnings("unused")
public class TweetService {

    public void updateSentiment(Sentiment sentiment) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.updateSentiment(sentiment);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void insertSentiment(Sentiment sentiment) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.insertSentiment(sentiment);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }

    public void deleteSentiment(Sentiment sentiment) {
        SqlSession sqlSession = MyBatisConnectionFactory.getSqlSessionFactory().openSession();
        try {
            TweetMapper tweetMapper = sqlSession.getMapper(TweetMapper.class);
            tweetMapper.deleteSentiment(sentiment);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
