package ru.bars_open.medvtr.amqp.biomaterial.util;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 18:09 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class MessageHolder {
    public static final String NO_LABORATORY_ASSIGNED = "Не найдено лабораторий в которые можно отправить этот биозабор";
    public static final String WAIT = "Биозабор отправлен в следующие лаборатории: {0}";
    public static final String CONTENT_INCORRECT = "Сообщение некорректно или имеет неизвестный формат. Ошибки: {0}";
    public static final String UNKNOWN_ROUTING_KEY = "Сообщение имеет неизвестный ключ '{0}'. Возможные ключи: {1}";
    public static final String UNKNOWN_EXCEPTION = "Неизвестная необработанная ошибка: {0}";
    public static final String SENDED = "Успешно обработано";
}
