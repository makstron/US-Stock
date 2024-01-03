package com.klim.stock.utils.phonenumber

import com.klim.stock.utils.phonenumber.api.PhoneNumberUtils
import com.google.i18n.phonenumbers.PhoneNumberUtil as PhoneNumber

class PhoneNumberUtilsImpl : PhoneNumberUtils {

    private var phoneNumberUtil: PhoneNumber = PhoneNumber.getInstance()

    override fun format(number: String): String {
        return phoneNumberUtil.format(
            phoneNumberUtil.parse(number, "US"),
            PhoneNumber.PhoneNumberFormat.INTERNATIONAL
        )
    }


}