package me.biocomp.hubitat_ci.api.device_api.zigbee.zcl

enum DataType {
    NO_DATA(0x00),

    DATA8(0x08),
    DATA16(0x09),
    DATA24(0x0A),
    DATA32(0x0B),
    DATA40(0x0C),
    DATA48(0x0D),
    DATA56(0x0E),
    DATA64(0x0F),
    
    BOOLEAN(0x0f),

    BITMAP8(0x18),
    BITMAP16(0x19),
    BITMAP24(0x1A),
    BITMAP32(0x1B),
    BITMAP40(0x1C),
    BITMAP48(0x1D),
    BITMAP56(0x1E),
    BITMAP64(0x1F),
    
    UINT8(0x20),
    UINT16(0x21),
    UINT24(0x22),
    UINT32(0x23),
    UINT40(0x24),
    UINT48(0x25),
    UINT56(0x26),
    UINT64(0x27),

    INT8(0x28),
    INT16(0x29),
    INT24(0x2A),
    INT32(0x2B),
    INT40(0x2C),
    INT48(0x2D),
    INT56(0x2E),
    INT64(0x2F),

    ENUM8(0x30),
    ENUM16(0x31),

    FLOAT2(0x28),
    FLOAT4(0x39),
    FLOAT8(0x3A),

    STRING_OCTET(0x41),
    STRING_CHAR(0x42),
    STRING_LONG_OCTET(0x43),
    STRING_LONG_CHAR(0x44),

    ARRAY(0x48),
    STRUCTURE(0x4C),

    SET(0x50),
    BAG(0x51),
    
    TIME_OF_DAY(0xE0),
    DATE(0xE1),
    UTCTIME(0xE2),
    
    CLUSTER_ID(0xE8),
    ATTRIBUTE_ID(0xE9),
    BACNET_OID(0xEA),
    
    IEEE_ADDRESS(0xF0),
    SECKEY128(0xF1),
    
    UNKNOWN(0xFF)

    DataType(int val) {
        val = val
    }

    boolean _is_hubitat_ci_private() { true }

    private int val
}