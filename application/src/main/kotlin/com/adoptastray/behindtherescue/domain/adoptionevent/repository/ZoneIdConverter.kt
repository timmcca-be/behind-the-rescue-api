package com.adoptastray.behindtherescue.domain.adoptionevent.repository

import java.time.ZoneId

import javax.persistence.AttributeConverter
import javax.persistence.Converter


@Converter(autoApply = true)
class ZoneIdConverter : AttributeConverter<ZoneId, String> {
    override fun convertToDatabaseColumn(attribute: ZoneId): String = attribute.id
    override fun convertToEntityAttribute(dbData: String): ZoneId = ZoneId.of(dbData)
}
