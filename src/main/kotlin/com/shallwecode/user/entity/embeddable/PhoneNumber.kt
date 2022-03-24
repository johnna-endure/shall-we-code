package com.shallwecode.user.entity.embeddable

import com.shallwecode.common.exception.BadRequestException
import javax.persistence.Embeddable

@Embeddable
class PhoneNumber(var phoneNumber: String) {

    init {
        this.phoneNumber = validateAndReturnPhoneNumber(phoneNumber)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PhoneNumber

        if (phoneNumber != other.phoneNumber) return false

        return true
    }

    override fun hashCode(): Int {
        return phoneNumber.hashCode()
    }

    /**
     * 최대 11자리를 넘어가는지, 하이픈 이외의 문자가 들어갔는지 체크합니다.
     *
     */
    private fun validateAndReturnPhoneNumber( phoneNumber: String): String {
        val phoneNumberRemovedHyphen = removeHyphen(phoneNumber)

        if (phoneNumberRemovedHyphen.isBlank()) throw BadRequestException("핸드폰 번호 정보가 입력되지 않았습니다.")

        val hasOnlyNumber = """ 
            [0-9]+
        """.trimIndent().toRegex();
        if (!hasOnlyNumber.matches(phoneNumberRemovedHyphen)) throw BadRequestException("하이픈 이외의 문자는 포함될 수 없습니다. phoneNumber : $phoneNumber")

        if (phoneNumberRemovedHyphen.length > 11) throw BadRequestException("핸드폰 번호 숫자가 11자리 이상입니다. length : ${phoneNumberRemovedHyphen.length}")
        return phoneNumberRemovedHyphen
    }

    /**
     * 핸드폰 번호에 하이픈이 포함된 경우 제거합니다.
     */
    private fun removeHyphen(phoneNumber: String): String {
        return phoneNumber.replace("-", "")
    }



}