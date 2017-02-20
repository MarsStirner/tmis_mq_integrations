package ru.bars_open.medvtr.amqp.biomaterial.util;

/**
 * Author: Upatov Egor <br>
 * Date: 20.02.2017, 19:57 <br>
 * Company: Bars Group [ www.bars.open.ru ]
 * Description:
 */
public class LaboratoryNotAssignedException extends Exception {

    public LaboratoryNotAssignedException() {
    }

    @Override
    public String getMessage() {
        return "LaboratoryNotAssignedException";
    }
}
