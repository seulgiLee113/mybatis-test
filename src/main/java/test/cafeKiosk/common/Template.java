package test.cafeKiosk.common;

import org.apache.ibatis.datasource.pooled.PooledDataSource;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;
import test.cafeKiosk.model.dao.MenuMapper;

public class Template {

    private static String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static String URL = "jdbc:mysql://localhost/menudb";
    private static String USER = "ohgiraffers";
    private static String PASSWORD = "ohgiraffers";

    private static SqlSessionFactory sqlSessionFactory;

    public static SqlSession getSqlSession() {

        if(sqlSessionFactory == null) {

            // MyBatis 환경 설정 정보 객체 생성
            Environment env = new Environment(
                    "dev",
                    new JdbcTransactionFactory(),
                    new PooledDataSource(DRIVER, URL, USER, PASSWORD)
            );

            // myBatis 전역 설정 정보 객체 생성
            Configuration configuration = new Configuration(env);
            configuration.getTypeAliasRegistry().registerAlias("MenuDTO", test.cafeKiosk.model.dto.MenuDTO.class);
            configuration.addMapper(MenuMapper.class);

            sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
        }

        // DML auto commit 설정을 false로 지정
        SqlSession sqlSession = sqlSessionFactory.openSession(false);

        return sqlSession;
    }

}
