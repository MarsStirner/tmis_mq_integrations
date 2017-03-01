package ru.bars_open.medvtr.mq.util;

/**
 * Author: Upatov Egor <br>
 * Date: 28.02.2017, 18:53 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class JdbcUrlBuilder {

    public static String build(final String rdbms, final String host, final Integer port, final String databaseName) {
        return "jdbc:" + rdbms + "://" + host + ':' + port + '/' + databaseName;
    }
}
