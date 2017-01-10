package ru.bars_open.medvtr.configuration_loader;

/**
 * Author: Upatov Egor <br>
 * Date: 09.01.2017, 16:39 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description: Абстарактный класс-интерфейс источника конфигурации
 */
public interface ConfigurationSource {
    /**
     * Проверка на корректность заполнения полей
     * @return true если необходимый минимум полей заполнен, false - если нет
     */
    boolean isValid();

    /**
     * Строковый идентификатор типа источника конфигурации
     * @return Строка - идентифкатор типа источника конфигурации (MUST BE UNIQUE THROUGH SUBCLASSES)
     */
    String getResourceType();
}
